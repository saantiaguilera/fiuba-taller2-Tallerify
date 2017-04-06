package com.u.tallerify.contract.abstracts;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;

/**
 * Created by saguilera on 3/12/17.
 */

public interface BaseDialogContract {

    interface View extends ContractView {

        void setContentView(@NonNull android.view.View view);
        void setSeverityImage(@NonNull Drawable drawable);
        void setSeverityTitle(@NonNull String title);

    }

    interface Presenter extends ContractPresenter {

    }

}
