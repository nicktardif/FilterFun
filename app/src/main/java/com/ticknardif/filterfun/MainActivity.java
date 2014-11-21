package com.ticknardif.filterfun;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public final String LOG_TAG = "Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.grid_main);
        ImageAdapter adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);

        List<String> filenames = getAlbumStorageDir("Camera");

        for (int i = 0; i < 10; i++) {
            String filename = filenames.get(i);
            Log.d(LOG_TAG, "Filename to be added: " + filename);
            adapter.add(filename);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String imagePath = (String) adapterView.getItemAtPosition(i);
                Log.d("Debug", "Touched " + imagePath);

                Intent intent = new Intent(getBaseContext(), InspectActivity.class);
                intent.putExtra("imagePath", imagePath);
                startActivity(intent);
            }
        });
    }

    public List<String> getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File path = new File(Environment.getExternalStoragePublicDirectory("DCIM"), albumName);
        List<String> filenames = new ArrayList<String>();

        File imagePath = new File("");

        if(path.isDirectory() ) {
            File[] children = path.listFiles();
            for(File file: children) {
                filenames.add(file.getAbsolutePath());
            }
        }

        return filenames;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
