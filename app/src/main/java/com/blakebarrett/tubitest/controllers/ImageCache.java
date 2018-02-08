package com.blakebarrett.tubitest.controllers;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.blakebarrett.tubitest.models.IMovie;

import java.net.URL;

/**
 * Created by Blake on 2/7/2018.
 */

public class ImageCache {

    private static LruCache<URL, Bitmap> cache;

    public ImageCache(final int size) {
        cache = new LruCache<>(size);
    }

    public static void cacheImage(final IMovie movie, final Bitmap bitmap) {
        cache.put(movie.getImageURL(), bitmap);
    }

    public static Bitmap getImage(final IMovie movie) {
        return cache.get(movie.getImageURL());
    }

}
