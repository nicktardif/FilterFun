package com.ticknardif.filterfun;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import Filters.Filter;

class ProcessImageAsync extends AsyncTask<Bitmap, Void, Bitmap> {

    private ImageView imageView;
    private Bitmap location;
    private InspectActivity activity;
    private Filter filter;

    public ProcessImageAsync(ImageView imageView, Bitmap location, InspectActivity activity, Filter filter) {
        this.imageView = imageView;
        this.location = location;
        this.activity = activity;
        this.filter = filter;
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmap) {

        // Create a mutable version of the bitmap
        Bitmap mutable = bitmap[0].copy(Bitmap.Config.ARGB_8888, true);

        filter.filter(mutable);

        return mutable;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        // Apply our bitmap to the ImageView
        imageView.setImageBitmap(bitmap);

        // Overwrite the original image with the processed bitmap
        activity.setBitmap(bitmap);

        Log.d("Debug", "Done with async thread");

        // Set up an async task to save the image in the background
        //SaveImageAsync saveImageTask = new SaveImageAsync(bitmap, file.getAbsolutePath());
        //saveImageTask.execute();
    }
}
