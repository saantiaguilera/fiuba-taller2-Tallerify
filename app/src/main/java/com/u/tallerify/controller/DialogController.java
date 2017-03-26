package com.u.tallerify.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A controller that displays a dialog window, floating on top of its activity's window.
 * This is a wrapper over {@link Dialog} object like {@link android.app.DialogFragment}.
 *
 * <p>Implementations should override this class and implement {@link #onCreateDialog(Context context)} to withProvider a custom dialog, such as an {@link android.app.AlertDialog}
 */
public abstract class DialogController extends BaseController {

    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";

    private Dialog dialog;
    private boolean dismissed;

    @NonNull
    @Override
    final protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        dialog = onCreateDialog(getActivity());
        dialog.setOwnerActivity(getActivity());
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dismissDialog();
            }
        });
        return new View(getActivity());//stub view
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bundle dialogState = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG);
        if (dialogState != null) {
            dialog.onRestoreInstanceState(dialogState);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (dialog != null) {
            Bundle dialogState = dialog.onSaveInstanceState();
            outState.putBundle(SAVED_DIALOG_STATE_TAG, dialogState);
        }
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        dialog.show();
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        dialog.hide();
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        dialog.setOnDismissListener(null);
        dialog.dismiss();
        dialog = null;
    }

    /**
     * Dismiss the dialog and pop this controller
     */
    public void dismissDialog() {
        if (dismissed) {
            return;
        }
        getRouter().popController(this);
        dismissed = true;
    }

    @Nullable
    protected Dialog getDialog() {
        return dialog;
    }

    /**
     * Build your own custom Dialog container such as an {@link android.app.AlertDialog}
     *
     * @param context The context
     * @return Return a new Dialog instance to be displayed by the Controller
     */
    @NonNull
    protected abstract Dialog onCreateDialog(@NonNull Context context);
}