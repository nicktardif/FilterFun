package com.ticknardif.filterfun;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;

public class SaveImageAsync extends AsyncTask<String, Void, String> {

    private String imagePath;
    private Bitmap image;

    public SaveImageAsync(Bitmap image, String imagePath) {
        this.image = image;
        this.imagePath = imagePath;
    }

    @Override
    protected String doInBackground(String... strings) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imagePath);
            image.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
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
        return "Successfully called doInBackground";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Debug", "Overwrote image :)");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
