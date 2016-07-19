package it.to.peppesca.flickrtest.utils;


import android.support.annotation.Nullable;

import java.util.ArrayList;

import it.to.peppesca.flickrtest.models.Photo;

/**
 * Created by Giuseppe Scabellone.
 */

public class Events {
    /**
     * This event is fired when List of element is needed to download
     */
    public static class EventPhotosListDownload {

        public final int response;
        private ArrayList<Photo> listPhotos=new ArrayList<>();
        /**
         *
         * @param response possible values RESPONSE_OK or RESPONSE_ERROR
         * @param listPhotos this could contain list of Photos donwloaded or null
         */
        public EventPhotosListDownload(int response, ArrayList<Photo> listPhotos) {
            this.response = response;
            this.listPhotos = listPhotos;
        }
        public int getResponse() {
            return response;
        }
        @Nullable
        public ArrayList<Photo> getListPhotos() {
            return listPhotos;
        }
    }

    public static class EventPhotoSend {

        private final Photo singlePhoto;
        /**
         *
         * @param singlePhoto this could contain Photo donwloaded or null
         */
        public EventPhotoSend(Photo singlePhoto) {
            this.singlePhoto = singlePhoto;
        }
        @Nullable
        public Photo getPhoto() {
            return singlePhoto;
        }
    }
}
