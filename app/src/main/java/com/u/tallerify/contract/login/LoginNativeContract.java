package com.u.tallerify.contract.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import rx.Observable;

/**
 * Created by saguilera on 4/13/17.
 */

public interface LoginNativeContract {

    @NonNull String KEY_USERNAME = LoginNativeContract.class.getName() + "username";
    @NonNull String KEY_PASSWORD = LoginNativeContract.class.getName() + "password";
    @NonNull String KEY_FIRSTNAME = LoginNativeContract.class.getName() + "firstname";
    @NonNull String KEY_LASTNAME = LoginNativeContract.class.getName() + "surname";
    @NonNull String KEY_EMAIL = LoginNativeContract.class.getName() + "email";
    @NonNull String KEY_BIRTHDAY = LoginNativeContract.class.getName() + "birthday";
    @NonNull String KEY_COUNTRY = LoginNativeContract.class.getName() + "country";

    interface View extends ContractView {

        @NonNull Observable<Bundle> observeLoginClicks();
        @NonNull Observable<Bundle> observeSignUpClicks();
        @NonNull Observable<Boolean> observeSignUpVisibilityChanges();
        void suggestCountry(@NonNull String country);

    }

    interface Presenter extends ContractPresenter {

        void setImage(@NonNull String imageUri);
        @NonNull Observable<Boolean> observeSignupVisibilityChanges();

    }

}
