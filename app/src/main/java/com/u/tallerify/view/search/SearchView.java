package com.u.tallerify.view.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.search.SearchContract;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/24/17.
 */
public class SearchView extends android.support.v7.widget.SearchView
        implements SearchContract.View {

    @Nullable PublishSubject<String> subject;

    public SearchView(final Context context) {
        super(context);
        setSubmitButtonEnabled(false);
        setIconified(false);
        setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (subject != null) {
                    subject.onNext(newText);
                }
                return true;
            }
        });
        requestFocus();
    }

    @NonNull
    @Override
    public Observable<String> observeInputs() {
        if (subject == null) {
            subject = PublishSubject.create();
        }

        return subject;
    }

}
