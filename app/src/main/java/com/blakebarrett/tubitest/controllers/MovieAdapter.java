package com.blakebarrett.tubitest.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blakebarrett.tubitest.R;
import com.blakebarrett.tubitest.models.IMovie;

import java.util.ArrayList;

/**
 * Created by Blake on 2/6/2018.
 */

public class MovieAdapter extends BaseAdapter{

    private ArrayList<IMovie> mMovies;

    private final Context mContext;
    private LayoutInflater mInflater;

    public ArrayList<IMovie> getMovies() {
        return mMovies;
    }

    public void setMovies(final ArrayList<IMovie> movies) {
        this.mMovies = movies;
    }

    public MovieAdapter(final Context context, final ArrayList<IMovie> movies) {
        super();
        this.mContext = context;
        this.mMovies = movies;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mMovies.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(final int position) {
        return mMovies.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(final int position) {
        return position;
    }

    /**
     * Generally, I prefer mot to mutate arguments, but since the view is a "physical" object
     * ultimately, we have to modify it in the end.
     */
    private void updateImageViewWithContentsOfURL(final ImageView imageView, final IMovie movie) {
        imageView.post(new Runnable() {
            @Override
            public void run() {
                final Bitmap bmp = ImageCache.getImage(movie);
                imageView.setImageBitmap(bmp);
            }
        });
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.movie_list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.moviePosterImage);
            holder.titleView = (TextView) convertView.findViewById(R.id.movieTitle);
            holder.idView = (TextView) convertView.findViewById(R.id.movieId);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final IMovie movie = mMovies.get(position);
        updateImageViewWithContentsOfURL(holder.imageView, movie);
        holder.titleView.setText(movie.getTitle());
        holder.idView.setText(movie.getId());

        return convertView;
    }

    // To find more information about the "ViewHolder Pattern", see the docs here:
    // https://developer.android.com/training/improving-layouts/smooth-scrolling.html
    private static class ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView idView;
    }
}
