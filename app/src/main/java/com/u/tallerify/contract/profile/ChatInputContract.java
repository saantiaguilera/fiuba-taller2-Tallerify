package com.u.tallerify.contract.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import rx.Observable;

public interface ChatInputContract {

    interface View extends ContractView {

        @NonNull Observable<String> observeUserMessages();
        void focus();

    }

    interface Presenter extends ContractPresenter {

    }

}
