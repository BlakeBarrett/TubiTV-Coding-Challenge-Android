package com.blakebarrett.tubitest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.blakebarrett.tubitest.controllers.MovieAdapter;

public class MainActivity extends Activity {

    private ListView listView;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = findViewById(R.id.movies_list_view);
        fetchMovies();
    }

    private void fetchMovies() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                adapter = new MovieAdapter(MainActivity.this);
                return null;
            }

            protected void onPostExecute (Void result) {
                listView.setAdapter(adapter);
            }
        }.execute();
    }
}
