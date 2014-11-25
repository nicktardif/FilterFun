package com.ticknardif.filterfun;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InspectActivity extends Activity {
    public Bitmap bitmap;
    public int width = 0;
    public int height = 0;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);

        List<Runnable> functions =new ArrayList<Runnable>();
        Bundle bundle = getIntent().getExtras();
        File file = new File(bundle.getString("filePath"));
        Log.d("Debug", "ImagePath is " + file.getAbsolutePath());

        imageView = (ImageView) findViewById(R.id.inspect_image);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        width = size.x;
        height = size.y;

        bitmap = ImageAdapter.decodeSampledBitmapFromUri(file.getAbsolutePath(), width, height);
        imageView.setImageBitmap(bitmap);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView imageView = (ImageView) view;

                // Run an async task to process the full image in the background
                // The result of this will overwrite the "quicker" image processing that is done below
                ProcessImageAsync processImageAsync = new ProcessImageAsync(imageView, bitmap, InspectActivity.this);
                processImageAsync.execute(bitmap);

                long start = System.currentTimeMillis();

                // Create mutable bitmap
                // Scale down the size for quicker computations
                Bitmap mutable = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 4, bitmap.getHeight() / 4, false);

                long end = System.currentTimeMillis();

                long elapsed = end - start;
                Log.d("Debug", "Bitmap copying took " + Double.toString(elapsed / 1000.0) + " seconds.");

                start = System.currentTimeMillis();
                Filter.RainbowFilter(mutable);
                end = System.currentTimeMillis();

                elapsed = end - start;
                Log.d("Debug", "Image processing took " + Double.toString(elapsed / 1000.0) + " seconds.");

                imageView.setImageBitmap(mutable);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

