package com.u.tallerify.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.rxlifecycle.ControllerEvent;
import com.bluelinelabs.conductor.rxlifecycle.RxControllerLifecycle;
import com.squareup.coordinators.Coordinator;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.OutsideLifecycleException;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.controller.abstracts.BaseDialogController;
import com.u.tallerify.utils.RouterInteractor;
import java.lang.ref.WeakReference;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/12/17.
 */
public abstract class Presenter<VIEW extends ContractView> extends Coordinator {

    @Nullable WeakReference<VIEW> viewWeakReference;

    private final @NonNull BehaviorSubject<PresenterEvent> lifecycleSubject;

    public Presenter() {
        lifecycleSubject = BehaviorSubject.create();
    }

    protected @Nullable Context getContext() {
        return RouterInteractor.instance().mainRouter().getActivity();
    }

    protected @NonNull Router getMainRouter() {
        return RouterInteractor.instance().mainRouter();
    }

    protected @NonNull Router getAuxiliaryRouter() {
        return RouterInteractor.instance().auxRouter();
    }

    @SuppressWarnings("ConstantConditions")
    protected void showDialog(@NonNull BaseDialogController controller) {
        RouterInteractor.instance().auxRouter()
            .setPopsLastView(true)
            .setRoot(RouterTransaction.with(controller)
                .popChangeHandler(new FadeChangeHandler())
                .pushChangeHandler(new FadeChangeHandler()));
    }

    /**
     * Bind to a view, the observable will be unsuscribed when the view is detached from its root
     */
    @NonNull
    @CheckResult
    protected final <T> LifecycleTransformer<T> bindToView(@NonNull View view) {
        return RxLifecycleAndroid.bindView(view);
    }

    /**
     * Useful when using views that are reusable and dont detach (eg in a recyclerview)
     * Or for binding stuff that are agnostic to the view (eg an interactor)
     */
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bind(lifecycleSubject, CONTROLLER_LIFECYCLE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void attach(@NonNull final View view) {
        super.attach(view);
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
        viewWeakReference = new WeakReference<>((VIEW) view);
        onAttach((VIEW) view);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void detach(@NonNull final View view) {
        super.detach(view);
        lifecycleSubject.onNext(PresenterEvent.DETACH);
        viewWeakReference = null;
        onDetach((VIEW) view);
    }

    protected final void requestView() {
        if (viewWeakReference != null && viewWeakReference.get() != null) {
            new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new Runnable() {
                @Override
                public void run() {
                    onViewRequested(viewWeakReference.get());
                }
            });
        }
    }

    protected abstract void onAttach(final @NonNull VIEW view);
    protected void onViewRequested(final @NonNull VIEW view) {}
    protected void onDetach(final @NonNull VIEW view) {}

    private static final Func1<PresenterEvent, PresenterEvent> CONTROLLER_LIFECYCLE =
        new Func1<PresenterEvent, PresenterEvent>() {
            @Override
            public PresenterEvent call(PresenterEvent lastEvent) {
                switch (lastEvent) {
                    case ATTACH:
                        return PresenterEvent.DETACH;
                    default:
                        throw new OutsideLifecycleException("Cannot bind to lifecycle when outside of it.");
                }
            }
        };

    private enum PresenterEvent {

        ATTACH,
        DETACH,

    }

}
