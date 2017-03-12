package com.u.tallerify.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;

/**
 * Class for interacting with the main routers of the application
 *
 * Created by saguilera on 3/12/17.
 */
public final class RouterInteractor {

    private static final @NonNull RouterInteractor instance = new RouterInteractor();

    /**
     * Main router for flows.
     */
    private static Router mainRouter;

    /**
     * Auxiliary router, this router can be used for anything
     * above the usual flow (dialogs / blurs / notifications /
     * etc)
     */
    private static Router auxRouter;

    public static RouterInteractor instance() {
        return instance;
    }

    public void attachMainRouter(@NonNull Activity activity,
        @NonNull ViewGroup router,
        @Nullable Bundle savedInstanceState) {
        mainRouter = Conductor.attachRouter(activity, router, savedInstanceState);
    }

    public void attachAuxiliaryRouter(@NonNull Activity activity,
        @NonNull ViewGroup router,
        @Nullable Bundle savedInstanceState) {
        auxRouter = Conductor.attachRouter(activity, router, savedInstanceState);
    }

    public Router mainRouter() {
        return mainRouter;
    }

    public Router auxRouter() {
        return auxRouter;
    }

}
