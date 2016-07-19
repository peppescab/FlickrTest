package it.to.peppesca.flickrtest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.to.peppesca.flickrtest.R;
import it.to.peppesca.flickrtest.activities.DetailActivity;
import it.to.peppesca.flickrtest.models.Photo;
import it.to.peppesca.flickrtest.network.NetworkManagerFlickr;
import it.to.peppesca.flickrtest.utils.Events;
import it.to.peppesca.flickrtest.utils.SingletonFlickr;


/**
 * Created by PeppeSca.
 * Adapter for RecyclerView
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private static ArrayList<Photo> photosList;
    private Activity act;

    public PhotoAdapter(Activity act, ArrayList<Photo> photosList) {
        this.photosList = photosList;
        this.act = act;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int position) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_photo, parent, false);

        ViewHolder vh = new ViewHolder(v, new ViewHolder.IMyViewHolderClicks() {
            @Override
            public void onClickPhoto() {
                Intent intent = new Intent(act, DetailActivity.class);
                act.startActivity(intent);
                EventBus.getDefault().postSticky(new Events.EventPhotoSend(photosList.get(position)));
            }
        });

        return vh; // Returning the created object
    }

    @Override
    public void onBindViewHolder(final PhotoAdapter.ViewHolder holder, int position) {

        Photo res = photosList.get(position);
        holder.tvTitle.setText(res.getTitle());

        Picasso.with(act)
                .load(NetworkManagerFlickr.getUrlFromPhotoPojo(res, true))
                .into(holder.ivImage);

    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return photosList.size();
    }

    // With the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout llItem;
        ImageView ivImage;
        TextView tvTitle;
        IMyViewHolderClicks mListener;

        public ViewHolder(View itemLayoutView, IMyViewHolderClicks listener) {
            super(itemLayoutView);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item_photo);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_item_photo);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_photo_title);

            mListener = listener;
            llItem.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mListener.onClickPhoto();
        }

        public interface IMyViewHolderClicks {
            void onClickPhoto();
        }
    }
}
