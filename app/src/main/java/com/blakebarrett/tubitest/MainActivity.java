package com.blakebarrett.tubitest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.blakebarrett.tubitest.api.MovieManager;
import com.blakebarrett.tubitest.controllers.MovieAdapter;

public class MainActivity extends Activity {

    private ListView listView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = findViewById(R.id.movies_list_view);
        fetchMovies();
    }

    private void fetchMovies() {
        MovieManager.LoadMovies(new MovieManager.MoviesLoadedCallback() {

            @Override
            public void run() {
                adapter = new MovieAdapter(MainActivity.this, this.movies);
                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        adapter.setMovies(MovieManager.deserialize(savedInstanceState));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(final Bundle outState) {
        final Bundle state = MovieManager.serialize(adapter);
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(state);
    }
}
