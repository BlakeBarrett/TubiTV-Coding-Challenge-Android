package com.blakebarrett.tubitest.models;

import android.os.Bundle;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Blake on 2/5/2018.
 */

public class MovieModel implements IMovie {

    public static final String TITLE_KEY = "title";

    public static final String IMAGE_URL_KEY = "image";

    public static final String ID_KEY = "id";

    private JSONObject data;

    public MovieModel(final Bundle value) {
        final String colon = ": ";
        final String comma = ", ";
        // This is a total cheat, but it's a cool idea (IMO) and it works and is fast.
        final StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(TITLE_KEY).append(colon).append(value.getString(TITLE_KEY));
        builder.append(comma);
        builder.append(ID_KEY).append(colon).append(value.getString(ID_KEY));
        builder.append(comma);
        builder.append(IMAGE_URL_KEY).append(colon).append(value.getString(IMAGE_URL_KEY));
        builder.append("}");
        final String jsonString = builder.toString();
        try {
            this.data = new JSONObject(jsonString);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

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
