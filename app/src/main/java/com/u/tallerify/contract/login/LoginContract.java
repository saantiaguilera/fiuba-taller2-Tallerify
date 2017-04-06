package com.u.tallerify.contract.login;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */

public interface LoginContract {

    interface View extends ContractView {

        @NonNull Observable<Void> observeTermsAndConditionsClicks();
        @NonNull Observable<Void> observeFacebookLoginClicks();
        void showError();

    }

    interface Presenter extends ContractPresenter {
    }

}
