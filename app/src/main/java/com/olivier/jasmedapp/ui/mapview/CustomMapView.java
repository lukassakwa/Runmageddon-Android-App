package com.olivier.jasmedapp.ui.mapview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import org.jetbrains.annotations.NotNull;

public class CustomMapView extends MapView {
    public CustomMapView(@NonNull @NotNull Context context) {
        super(context);
    }

    public CustomMapView(@NonNull @NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMapView(@NonNull @NotNull Context context, @NonNull @NotNull AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomMapView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable GoogleMapOptions options) {
        super(context, options);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;

            case MotionEvent.ACTION_DOWN:
                this.getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
