package com.u.tallerify.controller.abstracts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;
import com.u.tallerify.controller.BaseController;
import com.u.tallerify.presenter.abstracts.BaseDialogPresenter;
import com.u.tallerify.view.abstracts.BaseDialogView;

/**
 * Created by saguilera on 3/12/17.
 */
public abstract class BaseDialogController extends BaseController {

    BaseDialogPresenter.Severity severity;
    String title;
    View content;
    boolean cancellable = true;

    public BaseDialogController(BaseDialogPresenter.Severity severity,
            String title,
            View content,
            boolean cancellable) {
        this.severity = severity;
        this.title = title;
        this.content = content;
        this.cancellable = cancellable;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        View view = new BaseDialogView(getActivity());

        Coordinators.bind(view, new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(final View view) {
                return new BaseDialogPresenter.Builder()
                    .severity(severity)
                    .title(title)
                    .content(content)
                    .cancellable(cancellable)
                    .build();
            }
        });

        return view;
    }

    @Override
    public boolean handleBack() {
        return !cancellable || super.handleBack();
    }


    public boolean isCancellable() {
        return cancellable;
    }

    public View content() {
        return content;
    }

    public String title() {
        return title;
    }

    public BaseDialogPresenter.Severity severity() {
        return severity;
    }

}
