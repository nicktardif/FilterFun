package Filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BlackWhiteFilter extends Filter {
    public Bitmap filter(Bitmap bitmap) {
        int stride = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[bitmap.getWidth() * height];
        bitmap.getPixels(pixels, 0, stride, 0, 0, stride, height);

        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                int weightedR = (int) (0.3 *    (float) ((pixels[y * stride + x] >> 16 ) & 0xFF));
                int weightedG = (int) (0.59 *   (float) ((pixels[y * stride + x] >> 8  ) & 0xFF));
                int weightedB = (int) (0.11 *   (float) ((pixels[y * stride + x]       ) & 0xFF));

                bitmap.setPixel(x, y, Color.argb(weightedR + weightedG + weightedB, 255, 255, 255));
            }
        }

        return bitmap;
    }
}

