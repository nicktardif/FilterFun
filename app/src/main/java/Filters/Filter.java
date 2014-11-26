package Filters;

import android.graphics.Bitmap;

public abstract class Filter {
    public abstract Bitmap filter(Bitmap bitmap);

    public static int[] getRGBFromPixel(int pixel) {
        int[] result = new int[3];
        result[0] = (pixel & 0xff0000) >> 16;
        result[1] = (pixel & 0xff00) >> 8;
        result[2] = pixel & 0xff;

        return result;
    }
}

