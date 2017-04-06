package com.u.tallerify.contract.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import java.util.List;
import rx.Observable;

/**
 * Created by saguilera on 3/30/17.
 */
public interface ProfileUserContactsContract {

    interface View extends ContractView {

        void setContacts(final @NonNull List<String> names, final @NonNull List<String> urls);
        @NonNull Observable<Integer> observeContactClicks();

    }

    interface Presenter extends ContractPresenter {
    }

}
