# Readme

This is the Readme file for Giuseppe Scabellone's task for Philips.

- How to use application
> Choose the variant (blue or red) to change the colors of application 
>(this has done just to show the knowledge of usage of flavors and variants)
> Click on searchview and perform a search by tag, a list of possible results will be showed.

Follows an explanation of all requirements how have been solved :

1. The app must use the Flickr API (https://www.flickr.com/services/api/) to allow user searching for photos with specific words
> By Using Volley and Picasso libraries it has been possible to download photos that user searchs using the SearchView.
> There is a manager (NetworkManagerBetsson) that takes care of all possible call to REST API and keeps also the URLS to call.
The Manager uses a custom Volley Request(GsonRequest) that makes the parsing of Json easier (it uses Gson library inside it). After obtaining the list of Photos send a message to the Handler that asked this information and put result in a Singleton.
MainActivity has an Handler that waits for result and in case of success retreives data from Singleton.

2. The app must show the results of the search in an infinite scroll list where each cell contains at least a photo
> Are downloaded just 25 photos so the user doesnt need to wait too much, when he scrolls other 25 are downloaded and so on such as an infinite list.
It has been possible by implmenting EndlessRecyclerOnScrollListener and using the callback loadMore()

3. When tapping on a cell the user of the app must see the full screen photo and its details
  Non-functional requirements
>It has created another Activity (DetailActivity) that shows detail of photo and show it bigger. For passing the photo to show from MainActivity to DetailActivity it has been used a Singleton. (Instead of description it has used the secret value of photo but it could be used another service of Flickr)

- Other nice features
>Simple caching system: it has used an HashMap <Key, Value> where the Key is the tag requested by user and the Value is an ArrayList <Photo>. When user performs a query the value is searched first in the hashmap, if exists first 25 photos are showed otherwise a Network call is performed. It exits a SimplyCacheManager for this instead of using directly an HashMap so if in future developers want to use another cache solution they need just to change this Manager, but not the rest of the app.
>I used two flavors to show this behaviour, if application is builded with red flavor color, otherwise blue.
>Simple POJO (like Photo and Photos) are made in Kotlin that makes very easy the read of simple files.
>UI follows some basics of Material Design like cardviews, recyclerviews and collapsing toolbar.
 
### Libraries

Libraries used:

* [Volley] - For download data by Flickr
* [Butterknife] - A solution by Jake Wharton to simplify the code
* [Picasso] - For downloading photos
* [Gson] - Library by Google to help the mapping from Json to POJO
* [EventBus] - Android optimized event bus that simplifies communication between Activities, Fragments, etc...
 
 [Volley]: <https://github.com/mcxiaoke/android-volley>
 [Butterknife]: <https://github.com/JakeWharton/butterknife>
 [Picasso]: <http://square.github.io/picasso/>
 [Gson]: <https://github.com/google/gson>
 [EventBus]: <https://github.com/greenrobot/EventBus>

   


