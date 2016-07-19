package it.to.peppesca.flickrtest.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.to.peppesca.flickrtest.R;
import it.to.peppesca.flickrtest.models.Photo;
import it.to.peppesca.flickrtest.network.NetworkManagerFlickr;
import it.to.peppesca.flickrtest.utils.Events;
import it.to.peppesca.flickrtest.views.RelativeLayoutCustom;

/**
 * This class uses secret contained in Photo element as example of possible description
 */
public class DetailActivity extends GeneralActivity {

    @Bind(R.id.tv_detail_title)
    TextView tvTitle;
    @Bind(R.id.tv_detail_description)
    TextView tvDescription;
    @Bind(R.id.iv_detail_image)
    ImageView imageView;
    @Bind(R.id.rl_custom_detail)
    RelativeLayoutCustom relativeLayoutCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        relativeLayoutCustom.initializeDefault();
        relativeLayoutCustom.showProgressBar();
    }

    /**
     * Registration to event of downloading photo
     *
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Events.EventPhotoSend event) {
        relativeLayoutCustom.showNormalLayout();
        Photo myPhoto = event.getPhoto();
        if (myPhoto != null) {
            tvTitle.setText(myPhoto.getTitle());
            tvDescription.setText(myPhoto.getSecret());

            Picasso.with(this)
                    .load(NetworkManagerFlickr.getUrlFromPhotoPojo(myPhoto, false))
                    .into(imageView);
        }

    }
}