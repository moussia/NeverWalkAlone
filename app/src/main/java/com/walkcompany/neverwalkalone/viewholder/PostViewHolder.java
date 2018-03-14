package com.walkcompany.neverwalkalone.viewholder;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.walkcompany.neverwalkalone.R;
import com.walkcompany.neverwalkalone.models.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;
    public TextView LieuView;
    public ImageView ImageCat;
    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.post_body);
        LieuView = (TextView) itemView.findViewById(R.id.post_lieu);
    ImageCat = (ImageView)itemView.findViewById(R.id.imgCat);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        if(post.title.equals("sport")){
          // titleView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sport,0,0,0);
            ImageCat.setImageResource(R.drawable.sport);
        }else if (post.title.equals("soirée")){
           // titleView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.soiree,0,0,0);
          ImageCat.setImageResource(R.drawable.soiree);
        }else if(post.title.equals("repas")){
          // titleView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.food,0,0,0);
     ImageCat.setImageResource(R.drawable.food);
        }else if(post.title.equals("trajet")){
           // titleView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trajet,0,0,0);
           ImageCat.setImageResource(R.drawable.trajet);
        }
        titleView.setText(post.sousCategorie);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);
        LieuView.setText(post.lieu);
        starView.setOnClickListener(starClickListener);
    }
}
