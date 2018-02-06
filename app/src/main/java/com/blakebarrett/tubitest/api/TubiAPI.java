package com.blakebarrett.tubitest.api;

import com.blakebarrett.tubitest.models.IMovie;
import com.blakebarrett.tubitest.models.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Blake on 2/5/2018.
 */

public class TubiAPI {

    private static final String ENDPOINT = "http://eng-assets.s3-website-us-west-2.amazonaws.com/fixture/movies.json";

    public static final ArrayList<IMovie> fetchMovies() {
        final ArrayList<IMovie> allMovies = new ArrayList<>();
        final JSONArray moviesJSONArray = fetchMoviesJSON();
        for (int i = 0; i < moviesJSONArray.length(); i++) {
            final JSONObject object = moviesJSONArray.optJSONObject(i);
            final IMovie current = new MovieModel(object);
            allMovies.add(current);
        }
        return allMovies;
    }

    private static final JSONArray fetchMoviesJSON() {
        try {
            final String response = doRequest();
            final JSONArray result = new JSONArray(response);
            return result;
        } catch (final JSONException e) {

        } catch (final NullPointerException e) {
            // I guess fetching the data failed.
        }
        return null;
    }

    // For expedience sake, code pulled from here: https://github.com/square/okhttp/wiki/Recipes
    private static final OkHttpClient client = new OkHttpClient();

    private static String doRequest() {
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (final Exception e) {
            System.console().printf(e.getMessage());
        }
        return null;
    }
}
