package com.notify.app.mobile.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.notify.app.mobile.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.Arrays;
/*
* The PhotoRatingAdapter is an extension of ParseQueryAdapter
* that has a custom layout for favorite meals, including a
* bigger preview image, the meal's rating, and a "favorite"
* star.
*/
public class PhotoRatingAdapter extends ParseQueryAdapter<Photo> {
    public PhotoRatingAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Photo>() {
            public ParseQuery<Photo> create() {
// Here we can configure a ParseQuery to display
// only top-rated meals.
                ParseQuery query = new ParseQuery("Photo");
                query.whereContainedIn("rating", Arrays.asList("5", "4"));
                query.orderByDescending("rating");
                return query;
            }
        });
    }
    @Override
    public View getItemView(Photo photo, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.item_list_favorites, null);
        }
        super.getItemView(photo, v, parent);
        ParseImageView image = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile photoFile = photo.getParseFile("photo");
        if (photoFile != null) {
            image.setParseFile(photoFile);
            image.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
// nothing to do
                }
            });
        }
        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(photo.getTitle());
        TextView ratingTextView = (TextView) v
                .findViewById(R.id.favorite_rating);
        ratingTextView.setText(photo.getRating());
        return v;
    }
}