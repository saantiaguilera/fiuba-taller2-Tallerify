package com.u.tallerify.contract.profile;

import android.support.annotation.NonNull;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;

/**
 * Created by saguilera on 4/4/17.
 */
public interface ChatListContract {

    interface View extends ContractView {

        void setAdapter(@NonNull FirebaseRecyclerAdapter adapter);

    }

    interface Presenter extends ContractPresenter {

    }

}
