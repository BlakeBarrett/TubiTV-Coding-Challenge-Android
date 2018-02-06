package com.blakebarrett.tubitest.models;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Blake on 2/5/2018.
 */

public class MovieModel implements IMovie {

    private static final String TITLE_KEY = "title";

    private static final String IMAGE_URL_KEY = "image";

    private static final String ID_KEY = "id";

    private final JSONObject data;

    public MovieModel(final JSONObject value) {
        this.data = value;
    }

    @Override
    public String getTitle() {
        return data.optString(TITLE_KEY);
    }

    @Override
    public URL getImageURL() {
        final String value = data.optString(IMAGE_URL_KEY);
        try {
            return new URL(value);
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getId() {
        return data.optString(ID_KEY);
    }
}
