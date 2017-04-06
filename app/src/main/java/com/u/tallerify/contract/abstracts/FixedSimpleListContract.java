package com.u.tallerify.contract.abstracts;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import java.util.List;
import rx.Observable;

/**
 * Created by saguilera on 4/1/17.
 */

public interface FixedSimpleListContract {

    interface View extends ContractView {

        void setData(@NonNull List<String> names, @NonNull List<String> urls);
        Observable<Integer> observePositionClicks();

    }

    interface Presenter extends ContractPresenter {

    }

}
