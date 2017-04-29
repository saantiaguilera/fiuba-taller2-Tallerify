package com.u.tallerify.presenter.abstracts;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import com.u.tallerify.contract.abstracts.BaseInputContract;
import com.u.tallerify.presenter.Presenter;
import java.util.concurrent.TimeUnit;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/27/17.
 */
public abstract class BaseInputPresenter extends Presenter<BaseInputContract.View>
    implements BaseInputContract.Presenter {

    @Override
    protected void onAttach(@NonNull final BaseInputContract.View view) {
        view.observeInputs()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<String>bindToLifecycle())
            .debounce(500, TimeUnit.MILLISECONDS) // To avoid backpressure on the api
            .subscribe(new Action1<String>() {
                @Override
                public void call(final String inputText) {
                    onInput(inputText);
                }
            });

        ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        view.requestSearchFocus();
    }

    @Override
    protected void onRender(@NonNull final BaseInputContract.View view) {}

    @Override
    protected void onDetach(@NonNull final BaseInputContract.View view) {
        super.onDetach(view);
        ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    protected abstract void onInput(@NonNull String input);

}