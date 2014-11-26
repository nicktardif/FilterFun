package Filters;

import android.graphics.Bitmap;
import android.graphics.Color;

public class RedFilter extends Filter {
    public Bitmap filter(Bitmap bitmap) {
        for(int x = 0; x < bitmap.getWidth(); x++) {
            for(int y = 0; y < bitmap.getHeight(); y++) {
                int[] rgb = getRGBFromPixel(bitmap.getPixel(x, y));
                rgb[0] = rgb[0] + 50 > 255 ? 255 : rgb[0] + 50;
                bitmap.setPixel(x, y, Color.argb(255, rgb[0], rgb[1], rgb[2]));
            }
        }

        return bitmap;
    }
}
