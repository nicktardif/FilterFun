package com.ticknardif.filterfun;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Filter {
    public static Bitmap redFilter(Bitmap bitmap) {
        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {
                int[] rgb = getRGBFromPixel(bitmap.getPixel(x, y));
                rgb[0] = rgb[0] + 50 > 255 ? 255 : rgb[0] + 50;
                bitmap.setPixel(x, y, Color.argb(255, rgb[0], rgb[1], rgb[2]));
            }
        }

        return bitmap;
    }
    public static Bitmap greenFilter(Bitmap bitmap) {
        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {
                int[] rgb = getRGBFromPixel(bitmap.getPixel(x, y));
                rgb[1] = rgb[1] + 50 > 255 ? 255 : rgb[1] + 50;
                bitmap.setPixel(x, y, Color.argb(255, rgb[0], rgb[1], rgb[2]));
            }
        }

        return bitmap;
    }
    public static Bitmap blueFilter(Bitmap bitmap) {
        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {
                int[] rgb = getRGBFromPixel(bitmap.getPixel(x, y));
                rgb[2] = rgb[2] + 50 > 255 ? 255 : rgb[2] + 50;
                bitmap.setPixel(x, y, Color.argb(255, rgb[0], rgb[1], rgb[2]));
            }
        }

        return bitmap;
    }
    public static Bitmap BlackWhiteFilter(Bitmap bitmap) {
        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {
                int[] rgb = getRGBFromPixel(bitmap.getPixel(x, y));

                int weightedR = (int) (0.3 * (float) rgb[0]);
                int weightedG = (int) (0.59 * (float) rgb[1]);
                int weightedB = (int) (0.11 * (float) rgb[2]);

                bitmap.setPixel(x, y, Color.argb(weightedR + weightedG + weightedB, 255, 255, 255));
            }
        }

        return bitmap;
    }

    public static Bitmap RainbowFilter(Bitmap bitmap) {

        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {
                int[] rgb = getRGBFromPixel(bitmap.getPixel(x, y));

                int rBase = 100;
                int gBase = 200;
                int bBase = 300;

                int r = rainbowCalc(x, rBase);
                int g = rainbowCalc(x, gBase);
                int b = rainbowCalc(x, bBase);

                bitmap.setPixel(x, y, Color.argb(255, (r + rgb[0]) / 2, (g + rgb[1]) / 2, (b + rgb[2]) / 2));
            }
        }

        return bitmap;
    }

    private static int rainbowCalc(int x, int base) {
        int value = 50;
        int spacing = 300;
        int range = 100;
        int interval = x / spacing;

        int max = interval * spacing + base;
        int max1 = (interval - 1) * spacing + base;
        int max2 = (interval + 1) * spacing + base;
        int offset = Math.min(Math.min(Math.abs(x - max), Math.abs(x - max1)), Math.abs(x - max2));

        if (offset >= 0 && offset <= range) {
            value = value + (int) (120 * ((float) (range - offset) / (float)range));
        }

        return value;
    }

    public static int[] getRGBFromPixel(int pixel) {
        int[] result = new int[3];
        result[0] = (pixel & 0xff0000) >> 16;
        result[1] = (pixel & 0xff00) >> 8;
        result[2] = pixel & 0xff;

        return result;
    }
}
