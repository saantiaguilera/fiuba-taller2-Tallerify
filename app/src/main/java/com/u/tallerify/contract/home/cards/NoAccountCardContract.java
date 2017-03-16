package com.u.tallerify.contract.home.cards;

import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */

public interface NoAccountCardContract {

    interface View extends ContractView {

        Observable<Void> observeCreateAccountsClicks();

    }

    interface Presenter extends ContractPresenter {

    }

}
