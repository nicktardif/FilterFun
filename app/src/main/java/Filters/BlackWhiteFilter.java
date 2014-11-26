package Filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BlackWhiteFilter extends Filter {
    public Bitmap filter(Bitmap bitmap) {
        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                int[] rgb = getRGBFromPixel(bitmap.getPixel(x, y));

                int weightedR = (int) (0.3 * (float) rgb[0]);
                int weightedG = (int) (0.59 * (float) rgb[1]);
                int weightedB = (int) (0.11 * (float) rgb[2]);

                bitmap.setPixel(x, y, Color.argb(weightedR + weightedG + weightedB, 255, 255, 255));
            }
        }

        return bitmap;
    }
}

