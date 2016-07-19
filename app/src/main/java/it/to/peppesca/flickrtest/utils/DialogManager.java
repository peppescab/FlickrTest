package it.to.peppesca.flickrtest.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import it.to.peppesca.flickrtest.R;

/**
 * Created by Giuseppe Scabellone.
 */
public class DialogManager {
    /**
     * Create an AlertDialog and show it
     * @param act
     * @param stringToShow
     */
    public static void showDialogWarning(final Activity act, String stringToShow) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                new ContextThemeWrapper(act, R.style.myDialog));

        builder.setMessage(stringToShow)
                .setTitle("OPS!!!");

        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (act != null)
                    act.finish();
            }
        });

        builder.create().show();
    }


}
