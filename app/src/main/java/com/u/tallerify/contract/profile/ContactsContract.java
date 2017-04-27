package com.u.tallerify.contract.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.utils.adapter.GenericAdapter;
import java.util.List;

/**
 * Created by saguilera on 4/27/17.
 */
public interface ContactsContract {

    interface View extends ContractView {

        void setData(@NonNull List<GenericAdapter.ItemSupplier> data);

    }

    interface Presenter extends ContractPresenter {

    }

}
