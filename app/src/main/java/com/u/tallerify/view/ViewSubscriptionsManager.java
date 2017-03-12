package com.u.tallerify.view;

import android.support.annotation.NonNull;
import android.view.View;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/12/17.
 */
public class ViewSubscriptionsManager {

    private @NonNull List<Subscription> subscriptions;

    public ViewSubscriptionsManager() {
        subscriptions = new ArrayList<>();
    }

    public void add(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void remove(Subscription subscription) {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        subscriptions.remove(subscription);
    }

    public void clear() {
        for (Subscription subscription : new ArrayList<>(subscriptions)) {
            remove(subscription);
        }
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

    private enum Event {
        ATTACH,
        DETACH
    }

}
