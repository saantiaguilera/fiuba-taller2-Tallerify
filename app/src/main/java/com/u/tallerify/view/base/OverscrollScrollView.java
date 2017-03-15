package com.u.tallerify.view.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ScrollView;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/14/17.
 */
public class OverscrollScrollView extends ScrollView {

    private @Nullable PublishSubject<OverScrolledBundle> onOverscrolledSubject;

    public OverscrollScrollView(final Context context) {
        super(context);
    }

    public OverscrollScrollView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public OverscrollScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(final int scrollX, final int scrollY, final boolean clampedX,
        final boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (onOverscrolledSubject != null) {
            onOverscrolledSubject.onNext(new OverScrolledBundle(scrollX, scrollY, clampedX, clampedY));
        }
    }

    public Observable<OverScrolledBundle> observeOnOverscrollChanges() {
        return onOverscrolledSubject == null ?
            onOverscrolledSubject = PublishSubject.create() :
            onOverscrolledSubject;
    }

    public static class OverScrolledBundle {
        public int scrollX, scrollY;
        public boolean clampedX, clampedY;

        public OverScrolledBundle(int sx, int sy, boolean cx, boolean cy) {
            scrollX = sx;
            scrollY = sy;
            clampedX = cx;
            clampedY = cy;
        }
    }

}
