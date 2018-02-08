package com.blakebarrett.tubitest.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.blakebarrett.tubitest.controllers.ImageCache;
import com.blakebarrett.tubitest.controllers.MovieAdapter;
import com.blakebarrett.tubitest.models.IMovie;
import com.blakebarrett.tubitest.models.MovieModel;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Blake on 2/7/2018.
 */

public abstract class MovieManager {

    public static void LoadMovies(final MoviesLoadedCallback callback) {
        new AsyncTask<Void, Void, ArrayList<IMovie>>() {

            @Override
            protected ArrayList<IMovie> doInBackground(final Void... voids) {
                return TubiAPI.fetchMovies();
            }

            protected void onPostExecute (final ArrayList<IMovie>result) {
                LoadImages(result, callback);
            }
        }.execute();
    }

    private static void LoadImages(final ArrayList<IMovie> movies, final MoviesLoadedCallback callback) {
        new ImageCache(movies.size());
        new AsyncTask<ArrayList<IMovie>, Void, ArrayList<IMovie>>() {

            @Override
            protected ArrayList<IMovie> doInBackground(final ArrayList<IMovie>... moviesList) {
                final ArrayList<IMovie> list = moviesList[0];

                for(int i = 0; i < list.size(); i++) {
                    final IMovie currentMovie = list.get(i);
                    final URL url = currentMovie.getImageURL();
                    try {
                        final InputStream inputStream = url.openConnection().getInputStream();
                        final Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                        ImageCache.cacheImage(currentMovie, bmp);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }

                return list;
            }

            protected void onPostExecute(final ArrayList<IMovie> movies) {
                callback.run(movies);
            }
        }.execute(movies);
    }

    private static final String MOVIE_COUNT_KEY = "MOVIE_COUNT_KEY";

    private static String generateKeyFor(final String value, final int atIndex) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(atIndex)).append(" ").append(value);
        return builder.toString();
    }

    public static Bundle serialize(final MovieAdapter adapter) {
        final ArrayList<IMovie> movies = adapter.getMovies();
        final Bundle bundle = new Bundle();

        bundle.putInt(MOVIE_COUNT_KEY, movies.size());

        for (int i = 0; i < movies.size(); i++) {
            final String titleKey = generateKeyFor(MovieModel.TITLE_KEY, i);
            final String idKey = generateKeyFor(MovieModel.ID_KEY, i);
            final String imageUrlKey = generateKeyFor(MovieModel.IMAGE_URL_KEY, i);

            final IMovie movie = movies.get(i);
            bundle.putString(titleKey, movie.getTitle());
            bundle.putString(idKey, movie.getId());
            bundle.putString(imageUrlKey, movie.getImageURL().toString());
        }
        return bundle;
    }

    public static ArrayList<IMovie> deserialize(final Bundle bundle) {
        final int count = bundle.getInt(MOVIE_COUNT_KEY);

        final ArrayList<IMovie> movies = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            final String titleKey = generateKeyFor(MovieModel.TITLE_KEY, i);
            final String idKey = generateKeyFor(MovieModel.ID_KEY, i);
            final String imageUrlKey = generateKeyFor(MovieModel.IMAGE_URL_KEY, i);

            final Bundle sub = new Bundle();
            sub.putString(titleKey, bundle.getString(titleKey));
            sub.putString(idKey, bundle.getString(idKey));
            sub.putString(imageUrlKey, bundle.getString(imageUrlKey));

            movies.add(new MovieModel(sub));
        }

        return movies;
    }

    public static abstract class MoviesLoadedCallback implements Runnable {

        public ArrayList<IMovie> movies;

        public MoviesLoadedCallback() {
            super();
        }

        public void run(final ArrayList<IMovie> movies) {
            this.movies = movies;
            this.run();
        }
    }
}
