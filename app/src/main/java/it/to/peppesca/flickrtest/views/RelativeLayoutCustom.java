package it.to.peppesca.flickrtest.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import it.to.peppesca.flickrtest.R;


/**
 * Utility that shows a layout, a progressbar or a text with no item contained
 *
 * Created by Giuseppe Scabellone.
 */
public class RelativeLayoutCustom extends RelativeLayout {

    private ArrayList<View> myChild = new ArrayList<>();
    private TextView mTextView = new TextView(getContext());
    private ProgressBar mProgressBar = new ProgressBar(getContext());
    private String textToShow;

    public static final int PROGRESS_BAR = 0, TEXT_NO_ITEM = 1, NORMAL_LAYOUT = 2;

    LayoutInflater mInflater;

    public RelativeLayoutCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RelativeLayoutNoProducts,
                0, 0);
        initValue(a);
    }

    public RelativeLayoutCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RelativeLayoutNoProducts,
                0, 0);
        initValue(a);
    }

    private void initValue(TypedArray a) {
        try {
            textToShow = a.getString(R.styleable.RelativeLayoutNoProducts_textToShow);
        } finally {
            a.recycle();
        }
    }

    public void initializeDefault() {
        addDefaultProgressBar();
        addTextMessage(R.style.myNormalWarningText, textToShow);
        showProgressBar();
    }

    /**
     * @param textviewStyle insert style in format R.style.mystyle if you want to use default style dont fill it
     * @param textToShow    text to insert in textview
     * @return
     */
    public TextView addTextMessage(int textviewStyle, String textToShow) {
        mTextView.setText(textToShow);
        //center
        mTextView.setGravity(Gravity.CENTER);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mTextView.setTextAppearance(getContext(), textviewStyle);
        } else {
            mTextView.setTextAppearance(textviewStyle);
        }

        addView(mTextView, params);
        return mTextView;
    }

    public void addDefaultProgressBar() {
        LayoutParams params = new LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mProgressBar, params);
    }

    /**
     * @param
     */
    public void show(int choice) {
        switch (choice) {
            case PROGRESS_BAR:
                showOldLayout(false);
                mTextView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case TEXT_NO_ITEM:
                showOldLayout(false);
                mTextView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                break;
            case NORMAL_LAYOUT:
                showOldLayout(true);
                mTextView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                break;
        }
    }

    public void showProgressBar() {
        showOldLayout(false);
        mTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void showTextView() {
        showOldLayout(false);
        mTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    public void showNormalLayout() {
        showOldLayout(true);
        mTextView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * this method show Normal layout if condition is true (it can be used for empty lists)
     *
     * @param isNormalToShow
     */
    public boolean showNormalWithCondition(boolean isNormalToShow) {
        if (isNormalToShow) {
            showOldLayout(false);
            mTextView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        } else {
            showOldLayout(true);
            mTextView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        }
        return isNormalToShow;
    }

    private void showOldLayout(boolean isToShow) {
        for (int i = 0; i < getChildCount(); i++) {
            if (isToShow) {
                getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

}
