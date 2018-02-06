package com.blakebarrett.tubitest.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Blake on 2/5/2018.
 */

public class MovieModelTest {

    private static final String JSON_ROW = "{\"title\":\"House Of 1,000 Corpses\",\"image\":\"http://images.adrise.tv/IDtu1BaqrjIFn5DjRrMKF5cXzzU=/214x306/smart/img.adrise.tv/6be3dfbb-9b4b-49ab-879a-ca27d835182f.jpg\",\"id\":\"348937\"}";

    @Test
    public void parsingFromJSON() {
        try {
            final JSONObject json = new JSONObject(JSON_ROW);
            final IMovie actual = new MovieModel(json);
            assertEquals("348937", actual.getId());
            assertEquals("House Of 1,000 Corpses", actual.getTitle());
            final String imageUrl = actual.getImageURL().toString();
            assertEquals("http://images.adrise.tv/IDtu1BaqrjIFn5DjRrMKF5cXzzU=/214x306/smart/img.adrise.tv/6be3dfbb-9b4b-49ab-879a-ca27d835182f.jpg", imageUrl);
        } catch (final JSONException e) {
            assertEquals(true, false);
        }
    }
}
