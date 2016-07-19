package it.to.peppesca.flickrtest.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.to.peppesca.flickrtest.R;
import it.to.peppesca.flickrtest.adapters.PhotoAdapter;
import it.to.peppesca.flickrtest.models.Photo;
import it.to.peppesca.flickrtest.network.NetworkManagerFlickr;
import it.to.peppesca.flickrtest.utils.Constants;
import it.to.peppesca.flickrtest.utils.DialogManager;
import it.to.peppesca.flickrtest.utils.EndlessRecyclerOnScrollListener;
import it.to.peppesca.flickrtest.utils.Events;
import it.to.peppesca.flickrtest.utils.SimplyCacheManager;
import it.to.peppesca.flickrtest.utils.SingletonFlickr;
import it.to.peppesca.flickrtest.views.RelativeLayoutCustom;


public class MainActivity extends GeneralActivity implements SearchView.OnQueryTextListener {

    @Bind(R.id.rl_custom_main)
    RelativeLayoutCustom relativeLayoutCustom;
    @Bind(R.id.rvLocations)
    RecyclerView recyclerView;
    @Bind(R.id.svPhotoByTag)
    android.support.v7.widget.SearchView searchView;
    @Bind(R.id.rl_starting)
    RelativeLayout relativeLayoutStart;

    private ArrayList<Photo> listPhotos = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private String queryValue;
    private EndlessRecyclerOnScrollListener myEndlessRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //check if internet is awailable
        if (NetworkManagerFlickr.isConnectionDown(this)) {
            DialogManager.showDialogWarning(this, getString(R.string.alert_no_wifi));
        }

        relativeLayoutCustom.initializeDefault();
        relativeLayoutCustom.showNormalLayout();
        photoAdapter = new PhotoAdapter(this, listPhotos);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(photoAdapter);

        myEndlessRec = new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                NetworkManagerFlickr.getPhotosByTag(queryValue, current_page, true);
            }
        };

        recyclerView.addOnScrollListener(myEndlessRec);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

    }

    /**
     * When Search button is pressed after entering query
     * this callback is fired
     *
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {

        relativeLayoutStart.setVisibility(View.GONE);
        relativeLayoutCustom.setVisibility(View.VISIBLE);

        queryValue = query;
        myEndlessRec.resetCurrentPage();
        listPhotos.clear();
        if (SimplyCacheManager.arePhotosInCache(query)) {
            SingletonFlickr.getInstance().setListPhotos(SimplyCacheManager.getPhotosByCache(query));
            listPhotos.addAll(SimplyCacheManager.getPhotosByCache(query));
            photoAdapter.notifyDataSetChanged();
        } else {
            NetworkManagerFlickr.getPhotosByTag(query, 1, false);
            relativeLayoutCustom.showProgressBar();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            listPhotos.clear();
            myEndlessRec.resetCurrentPage();
            photoAdapter.notifyDataSetChanged();
        }
        return false;
    }

    /**
     * Registration to event of downloading photo
     *
     * @param event
     */
    @Subscribe
    public void onEvent(Events.EventPhotosListDownload event) {
        if (event.getResponse() == Constants.RESPONSE_OK) {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            if (event.getListPhotos().size() == 0) {
                relativeLayoutCustom.showTextView();
            } else {
                listPhotos.clear();
                listPhotos.addAll(event.getListPhotos());
                relativeLayoutCustom.show(RelativeLayoutCustom.NORMAL_LAYOUT);
                photoAdapter.notifyDataSetChanged();
                //update cache
                SimplyCacheManager.addPhotosToCache(queryValue, (ArrayList<Photo>) listPhotos.clone());
            }
        } else {
            relativeLayoutCustom.showTextView();
        }
    }
}
