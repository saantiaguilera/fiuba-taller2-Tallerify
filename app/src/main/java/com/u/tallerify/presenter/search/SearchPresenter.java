package com.u.tallerify.presenter.search;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.contract.search.SearchContract;
import com.u.tallerify.presenter.Presenter;
import java.util.concurrent.TimeUnit;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/24/17.
 */

public class SearchPresenter extends Presenter<SearchContract.View>
        implements SearchContract.Presenter {

    @Override
    protected void onAttach(@NonNull final SearchContract.View view) {
        view.observeInputs()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<String>bindToLifecycle())
            .debounce(500, TimeUnit.MILLISECONDS) // To avoid backpressure on the api
            .subscribe(new Action1<String>() {
                @Override
                public void call(final String inputText) {
                    // TODO do somehthing
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), inputText, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
    }

}
