package com.blakebarrett.tubitest.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blakebarrett.tubitest.R;
import com.blakebarrett.tubitest.api.TubiAPI;
import com.blakebarrett.tubitest.models.IMovie;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Blake on 2/6/2018.
 */

public class MovieAdapter extends BaseAdapter{

    private final ArrayList<IMovie> movies;

    private final Context mContext;
    private LayoutInflater mInflater;

    public MovieAdapter(Context context) {
        super();
        this.mContext = context;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.movies = TubiAPI.fetchMovies();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return movies.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Generally, I prefer mot to mutate arguments, but since the view is a "physical" object
     * ultimately, we have to modify it in the end.
     * @param imageView
     * @param url
     */
    private void upateImageViewWithContentsOfURL(final ImageView imageView, final URL url) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bmp);
                        }
                    });
                } catch (final Exception e) {

                }
                return null;
            }
        }.execute();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowView = mInflater.inflate(R.layout.movie_list_item, parent, false);
        final ImageView imageView = rowView.findViewById(R.id.moviePosterImage);
        final TextView titleTextView = rowView.findViewById(R.id.movieTitle);
        final TextView idTextView = rowView.findViewById(R.id.movieId);

        final IMovie movie = movies.get(position);

        upateImageViewWithContentsOfURL(imageView, movie.getImageURL());
        titleTextView.setText(movie.getTitle());
        idTextView.setText(movie.getId());

        return rowView;
    }
}
