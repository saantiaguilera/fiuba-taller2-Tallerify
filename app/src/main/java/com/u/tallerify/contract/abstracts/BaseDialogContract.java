package com.u.tallerify.contract.abstracts;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */

public interface BaseDialogContract {

    interface View extends ContractView {

        @NonNull Observable<Void> observeOnCancelEvent();
        void setCancellable(boolean cancellable);
        boolean isCancellable();
        void setContentView(@NonNull android.view.View view);
        void setSeverityImage(@NonNull Drawable drawable);
        void setSeverityTitle(@NonNull String title);

    }

    interface Presenter extends ContractPresenter {

    }

}
