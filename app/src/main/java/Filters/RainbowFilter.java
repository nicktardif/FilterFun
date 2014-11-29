package Filters;

import android.graphics.Bitmap;

public class RainbowFilter extends Filter {
    private int value;
    private int spacing;
    private int range;

    public Bitmap filter(Bitmap bitmap) {
        int stride = bitmap.getWidth();
        int height = bitmap.getHeight();

        value = 50;
        spacing = stride / 3;
        range = spacing / 3;

        int[] pixels = new int[bitmap.getWidth() * height];
        bitmap.getPixels(pixels, 0, stride, 0, 0, stride, height);

        int rBase = 1 * range;
        int gBase = 2 * range;
        int bBase = 3 * range;

        for(int x = 0; x < bitmap.getWidth(); x++) {

            int r = rainbowCalc(x, rBase);
            int g = rainbowCalc(x, gBase);
            int b = rainbowCalc(x, bBase);

            for(int y = 0; y < bitmap.getHeight(); y++) {
                int pixel = pixels[y * stride + x];

                pixels[y * stride + x] =
                    0xFF000000 |
                    (((r + (( pixel >> 16)    & 0xFF)) / 2) << 16) |
                    (((g + (( pixel >>  8)    & 0xFF)) / 2) << 8) |
                    (((b + (( pixel >>  0)    & 0xFF)) / 2) << 0);
            }
        }
        bitmap.setPixels(pixels, 0, stride, 0, 0, stride, height);
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
