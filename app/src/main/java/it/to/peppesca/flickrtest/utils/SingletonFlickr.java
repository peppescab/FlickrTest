package it.to.peppesca.flickrtest.utils;

import java.util.ArrayList;

import it.to.peppesca.flickrtest.models.Photo;

/**
 * Created by Giuseppe Scabellone.
 */
public class SingletonFlickr {

    private static SingletonFlickr myInstance;
    private ArrayList<Photo> mListPhotos = new ArrayList<>();
    private Photo actualPhoto;

    public Photo getActualPhoto() {
        return actualPhoto;
    }

    public void setActualPhoto(Photo actualPhoto) {
        this.actualPhoto = actualPhoto;
    }

    private SingletonFlickr() {

    }

    public ArrayList<Photo> getListPhotos() {
        return mListPhotos;
    }

    public void setListPhotos(ArrayList<Photo> mListPhotos) {
        this.mListPhotos = mListPhotos;
    }

    public static SingletonFlickr getInstance() {
        if (myInstance == null)
            myInstance = new SingletonFlickr();
        return myInstance;
    }

    public void addToPhotoListAll(ArrayList<Photo> listPhoto) {
        mListPhotos.addAll(listPhoto);
    }
}
