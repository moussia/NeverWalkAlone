package com.walkcompany.neverwalkalone.Search;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.walkcompany.neverwalkalone.BaseActivity;
import com.walkcompany.neverwalkalone.MainActivity;
import com.walkcompany.neverwalkalone.R;

/**
 * Example application with ExpandableListView and CheckedTextView as list item.
 * Texts of selected list items are displayed in parent view.
 * 
 * @author Lauri Nevala
 * 
 *
 */
public class SearchActivity extends Activity {
	
	private SettingsListAdapter adapter;
	private ExpandableListView categoriesList;
	private ArrayList<Category> categories;

	protected Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		mContext = this;
		categoriesList = (ExpandableListView)findViewById(R.id.categories);
		categories = Category.getCategories();
		adapter = new SettingsListAdapter(this, 
				categories, categoriesList);
        categoriesList.setAdapter(adapter);
        
        categoriesList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				
				final TextView checkbox = (TextView)v.findViewById(R.id.list_item_text_child);

							BaseActivity.setRechercheVariable(checkbox.getText().toString());

				finish();
				//startActivity(new Intent(SearchActivity.this, SearchActivity.this.getCallingActivity().getClass().));

							//	Toast.makeText(getApplicationContext(),BaseActivity.getRechercheVariable(), Toast.LENGTH_SHORT).show();

				return true;
			}
		});

		Button btnAnnuler = (Button) findViewById(R.id.btnAnnuler);

		btnAnnuler.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(SearchActivity.this,MainActivity.class));
			}
		});
	}
	
	public class CustomComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }
    
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
