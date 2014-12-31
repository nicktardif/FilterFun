package com.ticknardif.filterfun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<File> images;

    public ImageAdapter(Context c) {
        context = c;
        images = new ArrayList<File>();
    }

    public void add(File file) {
        images.add(file);
    }

    public void clear() {
        images.clear();
    }

    void remove(int index) {
        images.remove(index);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public File getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        ImageView imageView;
        final int IMAGE_WIDTH = 220;
        final int IMAGE_HEIGHT = 220;

        if(convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT));
            imageView.setPadding(0, 0, 0 ,0);
        }
        else {
            imageView = (ImageView) convertView;
        }

        Bitmap bitmap = decodeSampledBitmapFromUri(images.get(position).getAbsolutePath(), IMAGE_WIDTH, IMAGE_HEIGHT);

        // Crop the image to a square in the center
        Bitmap cropped = cropImage(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT);

        imageView.setImageBitmap(cropped);

        return imageView;
    }
    public static Bitmap decodeSampledBitmapFromUri(String path, int reqWidth,
                                                    int reqHeight) {

        Bitmap bm = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);
        Log.d("Debug", "Bitmap Width: " + bm.getWidth() + ", Bitmap Height: " + bm.getHeight() + ", Sample Size: " + options.inSampleSize);

        return bm;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        // We want to make sure that the smallest dimension is larger than the size it will be scaled down to
        // This sampleSize will ensure that
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = (int) ((float) height / (float) reqHeight);
            } else {
                inSampleSize = (int) ((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
    }

    // This method creates a new bitmap with the size of the given width and height
    // The image is created from the center of the source image
    public static Bitmap cropImage(Bitmap bitmap, int width, int height) {
        int startX = (bitmap.getWidth() - width) / 2;
        int startY = (bitmap.getHeight() - height) / 2;
        return Bitmap.createBitmap(bitmap, startX, startY, width, height);
    }
}
