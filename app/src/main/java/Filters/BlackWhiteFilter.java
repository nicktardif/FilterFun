package Filters;

import android.graphics.Bitmap;

public class BlackWhiteFilter extends Filter {

    public BlackWhiteFilter() {
        this.name = "Black and White";
    }

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

                pixels[y * stride + x] =
                        0x00FFFFFF | ((weightedR + weightedG + weightedB) << 24);
            }
        }

        bitmap.setPixels(pixels, 0, stride, 0, 0, stride, height);
        return bitmap;
    }
}

