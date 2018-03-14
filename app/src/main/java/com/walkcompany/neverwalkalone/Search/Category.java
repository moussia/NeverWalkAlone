package com.walkcompany.neverwalkalone.Search;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Category {

	public ArrayList<Category> children;
	public ArrayList<String> selection;
	private DatabaseReference mDatabase;
	
	
	public String name;
	
	public Category() {
		children = new ArrayList<Category>();
		selection = new ArrayList<String>();
		mDatabase = FirebaseDatabase.getInstance().getReference();
	}
	
	public Category(String name) {
		this();
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	

	public static ArrayList<Category> getCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();

        //Sport
			Category Sport =new Category("Sport");
			//cat.generateChildren();
        Sport.children.add(new Category("Football"));
        Sport.children.add(new Category("Handball"));
        Sport.children.add(new Category("Volleyball"));
        Sport.children.add(new Category("Basketball"));
        Sport.children.add(new Category("Rugby"));
        Sport.children.add(new Category("Musculation"));
        Sport.children.add(new Category("Cyclisme"));
        Sport.children.add(new Category("Natation"));
        Sport.children.add(new Category("Athlétisme"));
        Sport.children.add(new Category("Tennis"));
        Sport.children.add(new Category("Gymnastique"));

        categories.add(Sport);

        Category Soirees =new Category("Soirées");
        Soirees.children.add(new Category("Soirées"));
        categories.add(Soirees);

        Category Repas =new Category("Repas");
        Repas.children.add(new Category("Repas"));
        categories.add(Repas);

        Category trajet =new Category("Trajet");
        trajet.children.add(new Category("Trajet"));
        categories.add(trajet);

		Category recent =new Category("recent");
		recent.children.add(new Category("recent"));
		categories.add(recent);


        return categories;
	}
	
	public static Category get(String name)
	{
		ArrayList<Category> collection = Category.getCategories();
		for (Iterator<Category> iterator = collection.iterator(); iterator.hasNext();) {
			Category cat = (Category) iterator.next();
			if(cat.name.equals(name)) {
				return cat;
			}
			
		}
		return null;
	}
}
