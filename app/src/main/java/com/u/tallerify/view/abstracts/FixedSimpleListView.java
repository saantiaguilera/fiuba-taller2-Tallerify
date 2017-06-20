package com.u.tallerify.view.abstracts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.u.tallerify.contract.abstracts.FixedSimpleListContract;
import com.u.tallerify.view.abstracts.internals.FixedListChildView;
import java.util.List;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Fixed list for presenting simple data with a title and a name
 * This does not uses recycler view, but it does use the same recycling concept to avoid trashing memory
 *
 * Created by saguilera on 3/30/17.
 */
public class FixedSimpleListView extends LinearLayout
        implements FixedSimpleListContract.View {

    @Nullable PublishSubject<Integer> clickSubject;

    public FixedSimpleListView(final Context context) {
        this(context, null);
    }

    public FixedSimpleListView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedSimpleListView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);
    }

    public @NonNull Observable<Integer> observePositionClicks() {
        if (clickSubject == null) {
            clickSubject = PublishSubject.create();
        }

        return clickSubject;
    }

    private void attachLast(@NonNull String name, @Nullable String url) {
        final FixedListChildView view = new FixedListChildView(getContext());
        view.setTitle(name);

        if (url != null) {
            view.setImageUrl(url);
        }

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

                if (urls.get(i) != null) {
                    currentView.setImageUrl(urls.get(i));
                }

                currentView.setTitle(names.get(i));
            }
        }
    }

}
