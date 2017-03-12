package com.u.tallerify.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;

/**
 * Object graph for presenters.
 *
 * This work for binding presenters to views, and when having child
 * presenters, you can simply have a presenter graph of the view group
 * and attach them all together
 *
 * Created by saguilera on 3/12/17.
 */
public abstract class AbstractPresenterGraph {

    private SparseArray<Presenter> presenters;

    protected void add(int key, @NonNull Presenter presenter) {
        if (presenters == null) {
            presenters = new SparseArray<>(size());
        }
        presenters.put(key, presenter);
    }

    protected @Nullable Presenter get(int key) {
        return presenters.get(key);
    }

    public abstract @Nullable Presenter<?> present(@NonNull View view);
    public abstract int size();

    public @Nullable Presenter<?>[] asArray() {
        if (presenters != null) {
            Presenter[] array = new Presenter[presenters.size()];
            for (int i = 0 ; i < presenters.size() ; ++i) {
                array[i] = presenters.get(i);
            }
            return array;
        }
        return null;
    }

}
