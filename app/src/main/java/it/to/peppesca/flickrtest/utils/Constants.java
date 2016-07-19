package it.to.peppesca.flickrtest.utils;

/**
 * Created by Giuseppe Scabellone.
 */
public class Constants {

    public static final int PHOTO_PER_PAGE = 25;

    //enum better not using in Android
    public static final int
            RESPONSE_OK = 0, RESPONSE_ERROR = 1;

    public static final String URL_FLICKR = "https://api.flickr.com/services/rest/?method=flickr.photos.search" +
            "&api_key=487d23224baa45b305e37a5557f93a89" +
            "&format=json&nojsoncallback=1" +
            "&per_page=" + Constants.PHOTO_PER_PAGE + "&page=%d&tags=";
    public static final String URL_PHOTO = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
}
