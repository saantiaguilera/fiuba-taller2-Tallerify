package com.u.tallerify.view.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.R;
import com.u.tallerify.contract.profile.ChatInputContract;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 4/5/17.
 */
public class ChatInputView extends LinearLayout
        implements ChatInputContract.View {

    @NonNull EditText editText;
    @NonNull ImageView sendButton;

    @Nullable PublishSubject<String> sendSubject;

    public ChatInputView(final Context context) {
        this(context, null);
    }

    public ChatInputView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatInputView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);

        inflate(context, R.layout.view_chat_input, this);

        editText = (EditText) findViewById(R.id.view_chat_input_edit_text);
        sendButton = (ImageView) findViewById(R.id.view_chat_input_send);

        RxView.clicks(sendButton)
            .compose(RxLifecycleAndroid.<Void>bindView(this))
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                   send(editText.getText().toString());
                }
            });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    send(editText.getText().toString());
                    return true;
                }
                return false;
            }

        });
    }

    void send(@NonNull String message) {
        if (sendSubject != null) {
            sendSubject.onNext(message);
        }
        editText.setText("");
    }

    @NonNull
    @Override
    public Observable<String> observeUserMessages() {
        if (sendSubject == null) {
            sendSubject = PublishSubject.create();
        }
        return sendSubject;
    }

    @Override
    public void focus() {
        editText.requestFocus();

        InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

}
