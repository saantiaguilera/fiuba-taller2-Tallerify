package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.profile.ProfileUserContactsContract;
import com.u.tallerify.presenter.Presenter;

/**
 * Created by saguilera on 3/30/17.
 */

public class ProfileUserContacts extends Presenter<ProfileUserContactsContract.View>
    implements ProfileUserContactsContract.Presenter {

    @Override
    protected void onAttach(@NonNull final ProfileUserContactsContract.View view) {
        // view.setContacts(...);
    }

}
