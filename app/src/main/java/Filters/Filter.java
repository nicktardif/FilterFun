package Filters;

import android.graphics.Bitmap;

public abstract class Filter {

    public String name;

    public abstract Bitmap filter(Bitmap bitmap);
}

