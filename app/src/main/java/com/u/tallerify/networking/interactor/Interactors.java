package com.u.tallerify.networking.interactor;

import android.os.Handler;
import android.os.Looper;
import com.google.firebase.crash.FirebaseCrash;
import rx.functions.Action1;

/**
 * The purpose of this actions is because the user is able to customize the observables
 * as he prefers (adding composables / threading / etc). Since he is the one subscribing,
 * we need to do error handling (so he has to subscribe with error action params !)
 * And thats the reason.
 *
 * Created by saguilera on 3/17/17.
 */
public final class Interactors {

    private Interactors() throws IllegalAccessException {
        throw new IllegalAccessException("Why are you instantiating this?");
    }

   public static final Action1<Object> ACTION_NEXT = new Action1<Object>() {
       @Override
       public void call(final Object o) {}
   };

   public static final Action1<Throwable> ACTION_ERROR = new Action1<Throwable>() {
       @Override
       public void call(final Throwable throwable) {
           FirebaseCrash.report(throwable);
       }
   };

}
