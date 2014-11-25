package com.ticknardif.filterfun;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

class ProcessImageAsync extends AsyncTask<Bitmap, Void, Bitmap> {

    private ImageView imageView;
    private Bitmap location;
    private InspectActivity activity;

    public ProcessImageAsync(ImageView imageView, Bitmap location, InspectActivity activity) {
        this.imageView = imageView;
        this.location = location;
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(Bitmap... bitmap) {

        // Create a mutable version of the bitmap
        Bitmap mutable = bitmap[0].copy(Bitmap.Config.ARGB_8888, true);
        Filter.RainbowFilter(mutable);

        return mutable;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        // Apply our bitmap to the ImageView
        imageView.setImageBitmap(bitmap);

        // Overwrite the original image with the processed bitmap
        activity.setBitmap(bitmap);

        // Set up an async task to save the image in the background
        //SaveImageAsync saveImageTask = new SaveImageAsync(bitmap, file.getAbsolutePath());
        //saveImageTask.execute();
    }
}
