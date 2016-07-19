package it.to.peppesca.flickrtest.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import it.to.peppesca.flickrtest.models.FlickrResponse;
import it.to.peppesca.flickrtest.models.Photo;
import it.to.peppesca.flickrtest.utils.AppController;
import it.to.peppesca.flickrtest.utils.Constants;
import it.to.peppesca.flickrtest.utils.Events;
import it.to.peppesca.flickrtest.utils.SingletonFlickr;


/**
 * Created by Giuseppe Scabellone.
 */
public class NetworkManagerFlickr {

    private static final String TAG = NetworkManagerFlickr.class.getSimpleName();


    public static void getPhotosByTag(String tagToSearch, int page, final boolean isToAppendList) {

        String urlFlickrPaged = String.format(Locale.US,Constants.URL_FLICKR, page);

        GsonRequest gsonRequest = new GsonRequest(urlFlickrPaged + tagToSearch,
                FlickrResponse.class, null,
                new Response.Listener<FlickrResponse>() {

                    @Override
                    public void onResponse(FlickrResponse photosResponse) {

                        ArrayList<Photo> listPhoto =
                                new ArrayList<>(Arrays.asList(photosResponse.getPhotos().getPhoto()));

                        if (isToAppendList) {
                            SingletonFlickr.getInstance().addToPhotoListAll(listPhoto);
                        } else {
                            SingletonFlickr.getInstance().setListPhotos(listPhoto);
                        }
                        EventBus.getDefault().post(new Events.EventPhotosListDownload(Constants.RESPONSE_OK, SingletonFlickr.getInstance().getListPhotos()));
                        VolleyLog.d(TAG, "Response: " + photosResponse.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                EventBus.getDefault().post(new Events.EventPhotosListDownload(Constants.RESPONSE_ERROR, null));

                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(gsonRequest);
    }

    /**
     * According to Flickr API photourl needs to be formed like
     * https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
     *
     * @param photoPojo
     * @return
     */
    public static String getUrlFromPhotoPojo(Photo photoPojo, boolean isThumbnail) {

        String sizeImage = isThumbnail ? "z" : "b";

        return String.format(Constants.URL_PHOTO,
                photoPojo.getFarm(),
                photoPojo.getServer(),
                photoPojo.getId(),
                photoPojo.getSecret(),
                sizeImage);
    }

    //TODO check if connection is on?
    public static boolean isConnectionDown(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() == null
                || !cm.getActiveNetworkInfo().isAvailable()
                || !cm.getActiveNetworkInfo().isConnected();
    }


}
