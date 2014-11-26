package Filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class RainbowFilter extends Filter {
    public Bitmap filter(Bitmap bitmap) {

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
}
