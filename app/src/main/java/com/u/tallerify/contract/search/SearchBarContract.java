package com.u.tallerify.contract.search;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import rx.Observable;

/**
 * Created by saguilera on 3/24/17.
 */

public interface SearchBarContract {

    interface View extends ContractView {

        @NonNull Observable<String> observeInputs();

    }

    interface Presenter extends ContractPresenter {
    }

}
