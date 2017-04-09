package com.u.tallerify.controller;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.rxlifecycle.RxController;
import com.u.tallerify.controller.abstracts.AlertDialogController;
import com.u.tallerify.utils.RouterInteractor;

/**
 * Created by saguilera on 3/12/17.
 */
public abstract class BaseController extends RxController {

    /**
     * Display the dialog, withProvider a transaction and pushing the controller.
     * @param tag The tag for this controller
     */
    public void showDialog(@NonNull final AlertDialogController controller, @Nullable final String tag) {
        new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                RouterInteractor.instance().auxRouter().pushController(RouterTransaction.with(controller)
                    .pushChangeHandler(new FadeChangeHandler(false))
                    .popChangeHandler(new FadeChangeHandler())
                    .tag(tag));
            }
        });
    }

}
