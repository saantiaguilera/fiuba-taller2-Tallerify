package com.u.tallerify.contract.home;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.list.adapter.GenericAdapter;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */

public interface HomeCardContainerContract {

    interface View extends ContractView {

        void setData(final @NonNull List<GenericAdapter.ItemSupplier> cards);

    }

    interface Presenter extends ContractPresenter {

    }

}
