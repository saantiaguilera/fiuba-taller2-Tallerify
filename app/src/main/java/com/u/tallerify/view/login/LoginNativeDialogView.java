package com.u.tallerify.view.login;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.u.tallerify.R;
import com.u.tallerify.contract.login.LoginNativeContract;
import com.u.tallerify.utils.MetricsUtils;
import com.u.tallerify.view.base.widget.InputTextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 4/13/17.
 */

public class LoginNativeDialogView extends LinearLayout
        implements LoginNativeContract.View {

    @NonNull InputTextView userNameField;
    @NonNull InputTextView passwordField;

    @NonNull ViewGroup signupExtrasContainer;

    @NonNull View actionButton;
    @NonNull TextView swapActionVisibilityField;

    @NonNull InputTextView nameField;
    @NonNull InputTextView surnameField;
    @NonNull InputTextView emailField;
    @NonNull InputTextView birthdayField;

    @NonNull PublishSubject<Bundle> signupSubject = PublishSubject.create();
    @NonNull PublishSubject<Bundle> loginSubject = PublishSubject.create();
    @NonNull PublishSubject<Boolean> swapActionVisibilitySubject = PublishSubject.create();

    @NonNull Calendar calendar = Calendar.getInstance();

    public LoginNativeDialogView(final Context context) {
        super(context);

        inflate(context, R.layout.view_login_native_dialog, this);

        int width = MetricsUtils.getScreenPixelBounds(context).x;
        width -= width/10;

        setOrientation(VERTICAL);
        setLayoutTransition(new LayoutTransition());
        setLayoutParams(new ViewGroup.LayoutParams(width,
            ViewGroup.LayoutParams.MATCH_PARENT));

        userNameField = (InputTextView) findViewById(R.id.view_login_native_dialog_username);
        passwordField = (InputTextView) findViewById(R.id.view_login_native_dialog_password);

        nameField = (InputTextView) findViewById(R.id.view_login_native_dialog_name);
        surnameField = (InputTextView) findViewById(R.id.view_login_native_dialog_surname);
        emailField = (InputTextView) findViewById(R.id.view_login_native_dialog_mail);
        birthdayField = (InputTextView) findViewById(R.id.view_login_native_dialog_birthday);

        signupExtrasContainer = (ViewGroup) findViewById(R.id.view_login_native_dialog_signup_extras_container);

        actionButton = findViewById(R.id.view_login_native_dialog_action_button);
        swapActionVisibilityField = (TextView) findViewById(R.id.view_login_native_dialog_signup_button);

        hideSignUp();

        actionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (!isActionPerformable()) {
                    markErrors();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(LoginNativeContract.KEY_USERNAME, userNameField.getEditText().getText().toString());
                bundle.putString(LoginNativeContract.KEY_PASSWORD, passwordField.getEditText().getText().toString());

                if (isSignUp()) {
                    bundle.putString(LoginNativeContract.KEY_FIRSTNAME, nameField.getText().toString());
                    bundle.putString(LoginNativeContract.KEY_LASTNAME, surnameField.getText().toString());
                    bundle.putString(LoginNativeContract.KEY_EMAIL, emailField.getText().toString());
                    bundle.putSerializable(LoginNativeContract.KEY_BIRTHDAY, calendar.getTime());

                    signupSubject.onNext(bundle);
                } else {
                    loginSubject.onNext(bundle);
                }
            }

        });

        birthdayField.getEditText().setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(
                        getContext(),
                        R.style.DatePickerDialogTheme,
                        dateListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show();

                    InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindowToken(), 0);

                    requestFocus();
                }
            }
        });

        swapActionVisibilityField.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                swapActionVisibilityText();
                swapActionVisibilitySubject.onNext(isSignUp());
            }
        });
    }

    void hideSignUp() {
        swapActionVisibilityField.setText("Aun no tienes cuenta ? Create una !");
        signupExtrasContainer.setVisibility(View.GONE);
    }

    void showSignUp() {
        swapActionVisibilityField.setText("Ya tengo cuenta!");
        signupExtrasContainer.setVisibility(View.VISIBLE);
    }

    void swapActionVisibilityText() {
        if (isSignUp()) {
            hideSignUp();
        } else {
            showSignUp();
        }
    }

    void markErrors() {
        if (userNameField.getEditText().getText().toString().isEmpty()) {
            userNameField.setError("Este campo es obligatorio!");
        }

        if (passwordField.getEditText().getText().toString().isEmpty()) {
            passwordField.setError("Este campo es obligatorio!");
        }

        if (isSignUp()) {
            if (nameField.getEditText().getText().toString().isEmpty()) {
                nameField.setError("Este campo es obligatorio!");
            }

            if (surnameField.getEditText().getText().toString().isEmpty()) {
                surnameField.setError("Este campo es obligatorio!");
            }

            if (emailField.getEditText().getText().toString().isEmpty()) {
                emailField.setError("Este campo es obligatorio!");
            }

            if (birthdayField.getEditText().getText().toString().isEmpty()) {
                birthdayField.setError("Este campo es obligatorio!");
            }
        }
    }

    boolean isActionPerformable() {
        boolean can = true;
        can &= !userNameField.getEditText().getText().toString().isEmpty();
        can &= !passwordField.getEditText().getText().toString().isEmpty();

        if (isSignUp()) {
            can &= !nameField.getText().toString().isEmpty();
            can &= !surnameField.getText().toString().isEmpty();
            can &= !emailField.getText().toString().isEmpty();
            can &= !birthdayField.getText().toString().isEmpty();
            return can;
        } else {
            return can;
        }
    }

    boolean isSignUp() {
        return signupExtrasContainer.getVisibility() == View.VISIBLE;
    }

    @NonNull
    @Override
    public Observable<Bundle> observeLoginClicks() {
        return loginSubject;
    }

    @NonNull
    @Override
    public Observable<Bundle> observeSignUpClicks() {
        return signupSubject;
    }

    @NonNull
    @Override
    public Observable<Boolean> observeSignUpVisibilityChanges() {
        return swapActionVisibilitySubject;
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birthdayField.setText(new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime()));
        }

    };

}
