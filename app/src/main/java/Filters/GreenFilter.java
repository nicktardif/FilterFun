package Filters;

import android.graphics.Bitmap;

public class GreenFilter extends Filter {

    public GreenFilter() {
        this.name = "Green";
    }

    public Bitmap filter(Bitmap bitmap) {
        int stride = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[bitmap.getWidth() * height];
        bitmap.getPixels(pixels, 0, stride, 0, 0, stride, height);

        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {

                int green = ((pixels[y * stride + x] >> 8 ) & 0xFF);
                green = green + 50 > 255 ? 255 : green + 50;

                pixels[y * stride + x] = (green << 8) | pixels[y * stride + x];
            }
        }

        bitmap.setPixels(pixels, 0, stride, 0, 0, stride, height);
        return bitmap;
    }
}

