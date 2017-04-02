package com.u.tallerify.contract.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.utils.adapter.GenericAdapter;
import java.util.List;

/**
 * Created by saguilera on 3/30/17.
 */

public interface ProfileUserActivityContract {

    interface View extends ContractView {

        void setActivities(final @NonNull List<GenericAdapter.ItemSupplier> cards);

    }

    interface Presenter extends ContractPresenter {

    }

}
