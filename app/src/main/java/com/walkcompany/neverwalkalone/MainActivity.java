

package com.walkcompany.neverwalkalone;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.walkcompany.neverwalkalone.Search.SearchActivity;
import com.walkcompany.neverwalkalone.fragment.MyPostsFragment;
import com.walkcompany.neverwalkalone.fragment.SearchPostsBySousCategorieFragment;
import com.walkcompany.neverwalkalone.loginAndRegister.LoginActivity;
import com.walkcompany.neverwalkalone.profil.GestionProfil;

import java.util.ArrayList;

import static com.walkcompany.neverwalkalone.R.id.swipeContainer;

public class  MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
  private ViewPager mViewPager;
private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("acceuil");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("salut");

//create the drawer and remember the `Drawer` result object
     /*   Drawer result = new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawrItem().withName("parametre")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        return true;
                    }
                })
                .build();*/
      /*  Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);*/
        // use this to start and trigger a service
        FirebaseNotificationServices s = new FirebaseNotificationServices();
        Intent i= new Intent(this,s.getClass());
       startService(i) ;
     //   create();
        // Create the adapter that will return a fragment for each section

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

// Button launches SearchActivity
        findViewById(R.id.fab_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //MainActivity.this.reseach();
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        // Button launches NewPostActivity
        findViewById(R.id.fab_new_Post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, NewPostActivity.class));
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
             reseach();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        reseach();
    }



    @Override
    protected void onResume() {
        super.onResume();
     reseach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return true;
        }else if(i== R.id.accueil) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        else if(i== R.id.profil){
            startActivity(new Intent(this, GestionProfil.class));
            return true;
        }else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    public void reseach() {

               mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                private final Fragment[] mFragments = new Fragment[] {
                        new SearchPostsBySousCategorieFragment(),
                       new MyPostsFragment()

                };
                private final String[] mFragmentNames = new String[] {
                        getString(R.string.heading_recent),
                        getString(R.string.heading_my_posts)
                };
                @Override
                public Fragment getItem(int position) {
                    return mFragments[position];
                }
                @Override
                public int getCount() {
                    return mFragments.length;
                }
                @Override
                public CharSequence getPageTitle(int position) {
                    return mFragmentNames[position];
                }
            };
            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        swipeContainer.setRefreshing(false);

        }
    public  void create(){
        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.tabs), "Hey faisons un petit tour des horizon."),
                        TapTarget.forView(findViewById(R.id.fab_new_Post),"Ici vous pouvez recherchez et publié de nouveau post ")
                                .dimColor(R.color.colorPrimary)
                                .outerCircleColor(R.color.white)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .textColor(android.R.color.white),
                        TapTarget.forView(findViewById(R.id.fab_search),"Ici vous pouvez recherchez et publié de nouveau post ")
                                .dimColor(R.color.colorPrimary)
                                .outerCircleColor(R.color.white)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .textColor(android.R.color.white),
                        TapTarget.forView(findViewById(R.id.container)," elle est belle cette image hein ")
                                .dimColor(R.color.colorPrimary)
                                .outerCircleColor(R.color.white)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .textColor(android.R.color.white),
                        TapTarget.forView(findViewById(R.id.container)," vous vous connaissez ? ")
                                .dimColor(R.color.colorPrimary)
                                .outerCircleColor(R.color.white)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .textColor(android.R.color.white)
                       )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        // Yay
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget) {
                        // Perfom action for the current target
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Boo
                    }
                }).start();
    }
    }


