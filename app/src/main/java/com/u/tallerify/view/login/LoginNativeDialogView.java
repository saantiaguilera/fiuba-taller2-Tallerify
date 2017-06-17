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
import com.u.tallerify.networking.RestClient;
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
    @NonNull InputTextView countryField;
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
        countryField = (InputTextView) findViewById(R.id.view_login_native_dialog_country);
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
                    bundle.putString(LoginNativeContract.KEY_FIRSTNAME, nameField.getEditText().getText().toString());
                    bundle.putString(LoginNativeContract.KEY_LASTNAME, surnameField.getEditText().getText().toString());
                    bundle.putString(LoginNativeContract.KEY_EMAIL, emailField.getEditText().getText().toString());
                    bundle.putString(LoginNativeContract.KEY_BIRTHDAY, birthdayField.getEditText().getText().toString());
                    bundle.putString(LoginNativeContract.KEY_COUNTRY, countryField.getEditText().getText().toString());

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

        InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(userNameField, InputMethodManager.SHOW_IMPLICIT);
    }

    void hideSignUp() {
        swapActionVisibilityField.setText(getResources().getString(R.string.view_dialog_login_create_account));
        signupExtrasContainer.setVisibility(View.GONE);
    }

    void showSignUp() {
        swapActionVisibilityField.setText(getResources().getString(R.string.view_dialog_login_already_have_account));
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
            userNameField.setError(getResources().getString(R.string.view_dialog_login_error_required));
        } else {
            if (!userNameField.getEditText().getText().toString().replaceAll("[a-zA-Z_0-9\\-\\.]+", "").isEmpty()) {
                userNameField.setError(getResources().getString(R.string.view_dialog_login_error_illegal_username));
            }
        }

        if (passwordField.getEditText().getText().toString().isEmpty()) {
            passwordField.setError(getResources().getString(R.string.view_dialog_login_error_required));
        } else if (passwordField.getEditText().getText().toString().length() < 4) {
            passwordField.setError(getResources().getString(R.string.view_dialog_login_error_short_password));
        }

        if (isSignUp()) {
            if (nameField.getEditText().getText().toString().isEmpty()) {
                nameField.setError(getResources().getString(R.string.view_dialog_login_error_required));
            }

            if (surnameField.getEditText().getText().toString().isEmpty()) {
                surnameField.setError(getResources().getString(R.string.view_dialog_login_error_required));
            }

            if (emailField.getEditText().getText().toString().isEmpty()) {
                emailField.setError(getResources().getString(R.string.view_dialog_login_error_required));
            } else if (!emailField.getEditText().getText().toString().contains("@")) {
                emailField.setError(getResources().getString(R.string.view_dialog_login_error_invalid_email));
            }

            if (birthdayField.getEditText().getText().toString().isEmpty()) {
                birthdayField.setError(getResources().getString(R.string.view_dialog_login_error_required));
            } else if (calendar.after(Calendar.getInstance())) {
                birthdayField.setError(getResources().getString(R.string.view_dialog_login_error_invalid_birthdate));
            }

            if (countryField.getEditText().getText().toString().isEmpty()) {
                countryField.setError(getResources().getString(R.string.view_dialog_login_error_required));
            }
        }
    }

    boolean isActionPerformable() {
        boolean can = true;
        can &= !userNameField.getEditText().getText().toString().isEmpty();
        can &= userNameField.getEditText().getText().toString().replaceAll("[a-zA-Z_]+", "").isEmpty();
        can &= !passwordField.getEditText().getText().toString().isEmpty();
        can &= passwordField.getEditText().getText().toString().length() >= 8;

        if (isSignUp()) {
            can &= !nameField.getEditText().getText().toString().isEmpty();
            can &= !surnameField.getEditText().getText().toString().isEmpty();
            can &= !emailField.getEditText().getText().toString().isEmpty();
            can &= !birthdayField.getEditText().getText().toString().isEmpty();
            can &= emailField.getEditText().getText().toString().contains("@");
            can &= !calendar.after(Calendar.getInstance());
            can &= !countryField.getEditText().getText().toString().isEmpty();
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

    @Override
    public void suggestCountry(@NonNull final String country) {
        if (countryField.getEditText().getText().toString().isEmpty()) {
            countryField.setText(country);
        }
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            birthdayField.setText(new SimpleDateFormat(RestClient.DATE_FORMAT).format(calendar.getTime()));
        }

    };

}
