package it.to.peppesca.flickrtest.models

/**
 * Created by Giuseppe Scabellone.
 */


class FlickrResponse {
    var photos: Photos? = null

    var stat: String? = null

    override fun toString(): String {
        return "ClassPojo [photos = $photos, stat = $stat]"
    }
}