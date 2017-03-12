package com.u.tallerify.contract.home.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;

/**
 * Created by saguilera on 3/12/17.
 */
public interface HeaderCardContract {

    interface View extends ContractView {

        void setTitle(@NonNull CharSequence charSequence);

    }

    interface Presenter extends ContractPresenter {

    }

}
