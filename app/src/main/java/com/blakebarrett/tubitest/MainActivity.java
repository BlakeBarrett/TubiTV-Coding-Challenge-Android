package com.blakebarrett.tubitest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.blakebarrett.tubitest.api.MovieManager;
import com.blakebarrett.tubitest.controllers.MovieAdapter;
import com.blakebarrett.tubitest.models.IMovie;

import java.util.Comparator;

public class MainActivity extends Activity {

    private ListView listView;
    private Button refreshButton;
    private Button filterButton;

    private MovieAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = findViewById(R.id.movies_list_view);
        this.refreshButton = findViewById(R.id.refreshButton);
        this.filterButton = findViewById(R.id.filterButton);

        fetchMovies();

        this.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                listView.setVisibility(View.GONE);
                fetchMovies();
            }
        });

        this.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                adapter.getMovies().sort(new Comparator<IMovie>() {
                    @Override
                    public int compare(final IMovie first, final IMovie second) {
                        final int id1 = Integer.parseInt(first.getId());
                        final int id2 = Integer.parseInt(second.getId());
                        return id1 - id2;
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void fetchMovies() {
        MovieManager.LoadMovies(new MovieManager.MoviesLoadedCallback() {

            @Override
            public void run() {
                adapter = new MovieAdapter(MainActivity.this, this.movies);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
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
