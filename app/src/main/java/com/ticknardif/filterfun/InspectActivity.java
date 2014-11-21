package com.ticknardif.filterfun;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InspectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);

        List<Runnable> functions =new ArrayList<Runnable>();
        Bundle bundle = getIntent().getExtras();
        String imagePath = bundle.getString("imagePath");
        Log.d("Debug", "ImagePath is " + imagePath);

        ImageView imageView = (ImageView) findViewById(R.id.inspect_image);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        Bitmap immutableBM = ImageAdapter.decodeSampledBitmapFromUri(imagePath, width, height);

        final Bitmap bitmap = immutableBM.copy(Bitmap.Config.ARGB_8888, true);

        functions.add(new Runnable() {
            public void run() {
                Filter.redFilter(bitmap);
            }
        });
        functions.add(new Runnable() {
            public void run() {
                Filter.blueFilter(bitmap);
            }
        });
        functions.add(new Runnable() {
            public void run() {
                Filter.greenFilter(bitmap);
            }
        });
        functions.add(new Runnable() {
            public void run() {
                Filter.BlackWhiteFilter(bitmap);
            }
        });
        functions.add(new Runnable() {
            public void run() {
                Filter.RainbowFilter(bitmap);
            }
        });

        Collections.shuffle(functions);
        functions.get(0).run();

        imageView.setImageBitmap(bitmap);

        saveImage(imagePath, bitmap);
    }
    public void saveImage(String imagePath, Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
}
