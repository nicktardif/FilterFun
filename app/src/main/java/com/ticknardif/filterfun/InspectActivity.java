package com.ticknardif.filterfun;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Filters.BlackWhiteFilter;
import Filters.BlueFilter;
import Filters.Filter;
import Filters.GreenFilter;
import Filters.RainbowFilter;
import Filters.RedFilter;

public class InspectActivity extends Activity {
    public Bitmap bitmap;
    public int width = 0;
    public int height = 0;
    public ImageView imageView;
    public List<Filter> filters;

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

        filters = new ArrayList<Filter>();
        filters.add(new RedFilter());
        filters.add(new GreenFilter());
        filters.add(new BlueFilter());
        filters.add(new BlackWhiteFilter());
        filters.add(new RainbowFilter());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView imageView = (ImageView) view;

                // Choose a random filter
                int numFilters = filters.size();
                int chosenNumber = (int) (Math.random() * numFilters);
                Log.d("Debug", Integer.toString(chosenNumber));
                Filter filter = filters.get(chosenNumber);

                // Run an async task to process the full image in the background
                // The result of this will overwrite the "quicker" image processing that is done below
                ProcessImageAsync processImageAsync = new ProcessImageAsync(imageView, bitmap, InspectActivity.this, filter);
                processImageAsync.execute(bitmap);
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

