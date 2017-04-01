package com.u.tallerify.view.abstracts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.u.tallerify.contract.abstracts.FixedListContract;
import com.u.tallerify.view.abstracts.internals.FixedListChildView;
import java.util.List;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/30/17.
 */
public class FixedListView extends LinearLayout
        implements FixedListContract.View {

    @Nullable PublishSubject<Integer> clickSubject;

    public FixedListView(final Context context) {
        this(context, null);
    }

    public FixedListView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedListView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
    }

    public @NonNull Observable<Integer> observePositionClicks() {
        if (clickSubject == null) {
            clickSubject = PublishSubject.create();
        }

        return clickSubject;
    }

    private void attachLast(@NonNull String name, @NonNull String url) {
        final FixedListChildView view = new FixedListChildView(getContext());
        view.setTitle(name);
        view.setImageUrl(url);
        view.setTag(getChildCount());
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (clickSubject != null) {
                    clickSubject.onNext((Integer) view.getTag());
                }
            }
        });

        addView(view);
    }

    public void setData(@NonNull List<String> names, @NonNull List<String> urls) {
        if (names.size() != urls.size()) throw new IllegalStateException("Names size != urls size");

        if (getChildCount() > names.size()) {
            for (int i = getChildCount() ; i > names.size() ; --i) {
                int absPosition = i - 1;
                removeViewAt(absPosition);
            }
        }

        for (int i = 0 ; i < names.size() ; ++i) {
            if (getChildCount() < i + 1) {
                attachLast(names.get(i), urls.get(i));
            } else {
                FixedListChildView currentView = (FixedListChildView) getChildAt(i);

                currentView.setImageUrl(urls.get(i));
                currentView.setTitle(names.get(i));
            }
        }
    }

}
