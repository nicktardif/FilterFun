package Filters;

import android.graphics.Bitmap;

public class BlueFilter extends Filter {
    public Bitmap filter(Bitmap bitmap) {
        int stride = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[bitmap.getWidth() * height];
        bitmap.getPixels(pixels, 0, stride, 0, 0, stride, height);

        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {

                int blue = (pixels[y * stride + x] & 0xFF);
                blue = blue + 50 > 255 ? 255 : blue + 50;

                pixels[y * stride + x] = (blue << 0) | pixels[y * stride + x];
            }
        }

        bitmap.setPixels(pixels, 0, stride, 0, 0, stride, height);
        return bitmap;
    }
}

