package com.u.tallerify.view.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.u.tallerify.R;
import com.u.tallerify.contract.login.LoginNativeContract;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 4/13/17.
 */

public class LoginNativeDialogView extends ScrollView
        implements LoginNativeContract.View {

    @NonNull EditText userNameField;
    @NonNull EditText passwordField;

    @NonNull ViewGroup signupExtrasContainer;

    @NonNull View actionButton;
    @NonNull TextView signupVisibilityActionField;

    @NonNull EditText nameField;
    @NonNull EditText surnameField;
    @NonNull EditText emailField;
    @NonNull DatePicker birthdayField;

    @NonNull PublishSubject<Bundle> signupSubject = PublishSubject.create();
    @NonNull PublishSubject<Bundle> loginSubject = PublishSubject.create();

    public LoginNativeDialogView(final Context context) {
        super(context);

        inflate(context, R.layout.view_login_native_dialog, this);

        userNameField = (EditText) findViewById(R.id.view_login_native_dialog_username);
        passwordField = (EditText) findViewById(R.id.view_login_native_dialog_password);

        nameField = (EditText) findViewById(R.id.view_login_native_dialog_name);
        surnameField = (EditText) findViewById(R.id.view_login_native_dialog_surname);
        emailField = (EditText) findViewById(R.id.view_login_native_dialog_mail);
        birthdayField = (DatePicker) findViewById(R.id.view_login_native_dialog_birthday);

        signupExtrasContainer = (ViewGroup) findViewById(R.id.view_login_native_dialog_signup_extras_container);

        actionButton = findViewById(R.id.view_login_native_dialog_action_button);
        signupVisibilityActionField = (TextView) findViewById(R.id.view_login_native_dialog_signup_button);

        actionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (!isActionPerformable()) {
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putString(LoginNativeContract.KEY_USERNAME, userNameField.getText().toString());
                bundle.putString(LoginNativeContract.KEY_PASSWORD, passwordField.getText().toString());

                if (isSignUp()) {
                    bundle.putString(LoginNativeContract.KEY_FIRSTNAME, nameField.getText().toString());
                    bundle.putString(LoginNativeContract.KEY_LASTNAME, surnameField.getText().toString());
                    bundle.putString(LoginNativeContract.KEY_EMAIL, emailField.getText().toString());

                    int year = birthdayField.getYear();
                    int month = birthdayField.getMonth();
                    int day = birthdayField.getDayOfMonth();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    Date date = calendar.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd"); // TODO change it if needed

                    signupSubject.onNext(bundle);
                } else {
                    loginSubject.onNext(bundle);
                }
            }

        });

        signupVisibilityActionField.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (isSignUp()) {
                    signupVisibilityActionField.setText("Ya tengo cuenta!");
                    signupExtrasContainer.setVisibility(View.GONE);
                } else {
                    signupVisibilityActionField.setText("Aun no tienes cuenta ? Create una !");
                    signupExtrasContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    boolean isActionPerformable() {
        boolean can = true;
        can &= !userNameField.getText().toString().isEmpty();
        can &= !passwordField.getText().toString().isEmpty();

        if (isSignUp()) {
            can &= !nameField.getText().toString().isEmpty();
            can &= !surnameField.getText().toString().isEmpty();
            can &= !emailField.getText().toString().isEmpty();
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

}
