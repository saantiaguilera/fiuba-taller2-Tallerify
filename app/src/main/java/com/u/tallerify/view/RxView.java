package com.u.tallerify.view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/12/17.
 */
public final class RxView {

    private RxView() throws IllegalAccessException {
        throw new IllegalAccessException("Why are you instantiating this?");
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull View view) {
        final BehaviorSubject<Event> subject = BehaviorSubject.create(Event.ATTACH);

        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

            @Override
            public void onViewAttachedToWindow(final View view) {
                subject.onNext(Event.ATTACH);
            }

            @Override
            public void onViewDetachedFromWindow(final View view) {
                subject.onNext(Event.DETACH);
            }
        });

        return RxLifecycle.bindUntilEvent(subject, Event.DETACH);
    }

    public static void dispatchClicks(final @NonNull View view, final @NonNull PublishSubject<Void> subject) {
        com.jakewharton.rxbinding.view.RxView.clicks(view)
            .observeOn(Schedulers.io())
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    if (subject != null) {
                        subject.onNext(null);
                    }
                }
            });
    }

    public static void dispatchSeeks(final @NonNull SeekBar view, final @NonNull PublishSubject<Integer> subject) {
        view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                if (fromUser) {
                    subject.onNext(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {}
        });
    }

    public static void dispatchSeeks(final @NonNull RatingBar view, final @NonNull PublishSubject<Integer> subject) {
        view.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, final float rating, final boolean fromUser) {
                if (fromUser) {
                    subject.onNext((int) rating);
                }
            }
        });
    }

    private enum Event {
        ATTACH,
        DETACH
    }

}
