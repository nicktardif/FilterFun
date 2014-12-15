package Filters;

import android.graphics.Bitmap;

public class RedFilter extends Filter {

    public RedFilter() {
        this.name = "Red";
    }

    public Bitmap filter(Bitmap bitmap) {
        int stride = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[bitmap.getWidth() * height];
        bitmap.getPixels(pixels, 0, stride, 0, 0, stride, height);

        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {

                int red = ((pixels[y * stride + x] >> 16 ) & 0xFF);
                red = red + 50 > 255 ? 255 : red + 50;

                pixels[y * stride + x] = (red << 16) | pixels[y * stride + x];
            }
        }

        bitmap.setPixels(pixels, 0, stride, 0, 0, stride, height);
        return bitmap;
    }
}
