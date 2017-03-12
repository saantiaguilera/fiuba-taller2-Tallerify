package com.u.tallerify.presenter.home;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.home.HomeCardContainerContract;
import com.u.tallerify.list.adapter.GenericAdapter;
import com.u.tallerify.list.home.card.supplier.HeaderCardSupplier;
import com.u.tallerify.list.home.card.supplier.NoAccountCardSupplier;
import com.u.tallerify.presenter.Presenter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
public class HomeCardContainerPresenter extends Presenter<HomeCardContainerContract.View>
        implements HomeCardContainerContract.Presenter {

    @Override
    protected void onAttach(@NonNull final HomeCardContainerContract.View view) {
        List<GenericAdapter.ItemSupplier> mocks = new ArrayList<>();
        mocks.add(new HeaderCardSupplier(getContext(), "No Account View Mock"));

        mocks.add(new NoAccountCardSupplier(getContext()));

        mocks.add(new HeaderCardSupplier(getContext(), "Songs Mock"));

        view.setData(mocks);
    }

}
