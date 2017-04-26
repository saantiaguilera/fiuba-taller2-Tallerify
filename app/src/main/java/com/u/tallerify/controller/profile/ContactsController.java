package com.u.tallerify.controller.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.u.tallerify.R;
import com.u.tallerify.controller.FlowController;

/**
 * Created by saguilera on 4/26/17.
 */

public class ContactsController extends FlowController {

    @NonNull
    @Override
    protected View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup container) {
        return null;
    }

    @Nullable
    @Override
    protected String title() {
        return getResources().getString(R.string.toolbar_contacts);
    }

}
