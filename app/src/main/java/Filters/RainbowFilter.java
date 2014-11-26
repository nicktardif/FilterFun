package Filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class RainbowFilter extends Filter {
    private int value = 50;
    private int spacing = 300;
    private int range = 100;

    public Bitmap filter(Bitmap bitmap) {
        int stride = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[bitmap.getWidth() * height];
        bitmap.getPixels(pixels, 0, stride, 0, 0, stride, height);

        for(int x = 0; x < bitmap.getWidth(); x++) {

            int rBase = 100;
            int gBase = 200;
            int bBase = 300;

            int r = rainbowCalc(x, rBase);
            int g = rainbowCalc(x, gBase);
            int b = rainbowCalc(x, bBase);

            for(int y = 0; y < bitmap.getHeight(); y++) {
                bitmap.setPixel(x, y, Color.argb(
                    255,
                    (   r + (( pixels[y * stride + x] >> 16)    & 0xFF)) / 2,
                    (   g + (( pixels[y * stride + x] >> 8)     & 0xFF)) / 2,
                    (   b + (  pixels[y * stride + x]           & 0xFF)) / 2
                ));
            }
        }
        return bitmap;
    }

    private int rainbowCalc(int x, int base) {
        int interval = x / spacing;

        int max = interval * spacing + base;
        int max1 = (interval - 1) * spacing + base;
        int max2 = (interval + 1) * spacing + base;
        int offset = Math.min(Math.min(Math.abs(x - max), Math.abs(x - max1)), Math.abs(x - max2));

        if (offset >= 0 && offset <= range) {
            return value + (int) (120 * ((float) (range - offset) / (float)range));
        }

        return value;
    }
}
