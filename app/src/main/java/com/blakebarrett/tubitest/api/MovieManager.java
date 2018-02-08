package com.blakebarrett.tubitest.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.blakebarrett.tubitest.controllers.ImageCache;
import com.blakebarrett.tubitest.models.IMovie;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Blake on 2/7/2018.
 */

public abstract class MovieManager {

    public static void LoadMovies(final MoviesLoadedCallback callback) {
        final MoviesLoadedCallback onComplete = callback;
        new AsyncTask<Void, Void, ArrayList<IMovie>>() {

            @Override
            protected ArrayList<IMovie> doInBackground(final Void... voids) {
                return TubiAPI.fetchMovies();
            }

            protected void onPostExecute (final ArrayList<IMovie>result) {
                LoadImages(result, onComplete);
            }
        }.execute();
    }

    private static void LoadImages(final ArrayList<IMovie> movies, final MoviesLoadedCallback callback) {
        new ImageCache(movies.size());
        final MoviesLoadedCallback onComplete = callback;
        new AsyncTask<ArrayList<IMovie>, Void, ArrayList<IMovie>>() {

            @Override
            protected ArrayList<IMovie> doInBackground(final ArrayList<IMovie>... moviesList) {
                final ArrayList<IMovie> list = moviesList[0];

                for(int i = 0; i < list.size(); i++) {
                    final IMovie currentMovie = list.get(i);
                    final URL url = currentMovie.getImageURL();
                    try {
                        final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        ImageCache.cacheImage(currentMovie, bmp);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }

                return list;
            }

            protected void onPostExecute(final ArrayList<IMovie> movies) {
                onComplete.run(movies);
            }
        }.execute(movies);
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
