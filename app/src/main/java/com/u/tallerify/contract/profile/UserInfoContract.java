package com.u.tallerify.contract.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;

/**
 * Created by saguilera on 3/30/17.
 */
public interface UserInfoContract {

    interface View extends ContractView {

        void setUserName(@NonNull String name);
        void setUserLastLocation(@NonNull String location);
        void setUserImage(@NonNull String url);

    }

    interface Presenter extends ContractPresenter {
    }

}
