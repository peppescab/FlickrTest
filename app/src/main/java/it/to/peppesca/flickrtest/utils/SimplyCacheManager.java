package it.to.peppesca.flickrtest.utils;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import it.to.peppesca.flickrtest.models.Photo;


/**
 * Created by Giuseppe Scabellone.
 * This Manager adds a layer to Cache
 */
public class SimplyCacheManager {
    private static HashMap<String, ArrayList<Photo>> cachePhotosMemory = new HashMap<>();

    /**
     * Method returns true if cache contains query
     *
     * @param query
     * @return true if photos are in cache
     */
    public static boolean arePhotosInCache(String query) {
        return cachePhotosMemory.containsKey(query);
    }

    @Nullable
    public static ArrayList<Photo> getPhotosByCache(String query) {
        return cachePhotosMemory.get(query);
    }

    public static void addPhotosToCache(String query, ArrayList<Photo> photosForCache) {
        if (!cachePhotosMemory.containsKey(query)) {
            ArrayList<Photo> temp = new ArrayList<>();
            temp.addAll(photosForCache);
            cachePhotosMemory.put(query, temp);
        }
    }

}
