package com.notify.app.mobile.bootstrapOrigin.ui;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.RatingBar;
import android.widget.Toast;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.notify.app.mobile.BootstrapApplication;
import com.notify.app.mobile.R;
import com.notify.app.mobile.bootstrapOrigin.core.User;
import com.notify.app.mobile.ui.MainActivity;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter to display a list of traffic items
 */
public class UserListAdapter extends SingleTypeAdapter<User> {

    private ParseUser parseProvider;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    /**
     * @param inflater
     * @param items
     */
    public UserListAdapter(final LayoutInflater inflater, final List<User> items) {
        super(inflater, R.layout.user_list_item);

        setItems(items);
    }

    /**
     * @param inflater
     */
    public UserListAdapter(final LayoutInflater inflater) {
        this(inflater, null);


    }

    @Override
    public long getItemId(final int position) {
        final String id = getItem(position).getObjectId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.iv_avatar, R.id.tv_name};
    }

    @Override
    protected void update(final int position, final User user) {

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereEqualTo("objectId", user.getObjectId());

        try {
            List<ParseUser> results = userQuery.find();
            parseProvider = results.get(0);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        ParseFile photo = (ParseFile) parseProvider.get("ImageFile");

        try{
            Picasso.with(BootstrapApplication.getInstance())
                    .load(photo.getUrl())
                    .placeholder(R.drawable.gravatar_icon)
                    .into(imageView(0));

        }catch(NullPointerException ex){
            Picasso.with(BootstrapApplication.getInstance())
                    .load(user.getAvatarUrl())
                    .placeholder(R.drawable.gravatar_icon)
                    .into(imageView(0));
        }


        setText(1, String.format("%1$s %2$s", user.getFirstName(), user.getLastName()));



    }

}
