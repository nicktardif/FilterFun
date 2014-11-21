package com.ticknardif.filterfun;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.SurfaceTexture;
        import android.view.TextureView;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AbsListView;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.GridView;
        import android.widget.ImageView;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> images;

    public ImageAdapter(Context c) {
        context = c;
        images = new ArrayList<String>();
    }

    public void add(String path) {
        images.add(path);
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
    public String getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        final int IMAGE_WIDTH = 220;
        final int IMAGE_HEIGHT = 220;


        if(convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT));
            imageView.setPadding(8, 8, 8 ,8);
        }
        else {
            imageView = (ImageView) convertView;
        }

        Bitmap bitmap = decodeSampledBitmapFromUri(images.get(position), IMAGE_WIDTH, IMAGE_HEIGHT);
        imageView.setImageBitmap(bitmap);

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

        return bm;
    }

    public static int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height
                        / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }

        return inSampleSize;
    }
}
