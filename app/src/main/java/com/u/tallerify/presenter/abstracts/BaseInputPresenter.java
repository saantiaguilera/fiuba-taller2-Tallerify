package com.u.tallerify.presenter.abstracts;

import android.support.annotation.NonNull;
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
    }

    @Override
    protected void onRender(@NonNull final BaseInputContract.View view) {}

    protected abstract void onInput(@NonNull String input);

}