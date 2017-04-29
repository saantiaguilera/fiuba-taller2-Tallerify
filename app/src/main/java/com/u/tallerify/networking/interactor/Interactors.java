package com.u.tallerify.networking.interactor;

import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import com.google.firebase.crash.FirebaseCrash;
import com.u.tallerify.R;
import com.u.tallerify.utils.RouterInteractor;
import es.dmoral.toasty.BuildConfig;
import es.dmoral.toasty.Toasty;
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
           showError(throwable);
           FirebaseCrash.report(throwable);
       }
   };

   public static void showError(@NonNull Throwable throwable) {
       Toasty.error(
           RouterInteractor.instance().mainRouter().getActivity(),
           "Oops, parece que hubo un error interno!",
           2500
       ).show();

       if (BuildConfig.DEBUG) {
           showStackTrace(throwable);
       }
   }

   static void showStackTrace(@NonNull Throwable throwable) {
       NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(RouterInteractor.instance().mainRouter().getActivity())
           .setSmallIcon(R.drawable.ic_launcher)
           .setContentTitle("Error!")
           .setContentText(throwable.getMessage());
       NotificationManager mNotificationManager = (NotificationManager)
           RouterInteractor.instance().mainRouter().getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
       mNotificationManager.notify(0, mBuilder.build());
   }

}
