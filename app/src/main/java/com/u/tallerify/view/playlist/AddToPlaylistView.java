package com.u.tallerify.view.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.contract.playlist.AddToPlaylistContract;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 4/8/17.
 */
public class AddToPlaylistView extends RecyclerView
        implements AddToPlaylistContract.View {

    Adapter adapter;

    public AddToPlaylistView(final Context context) {
        super(context);

        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setPlaylists(@NonNull final List<String> names) {
        setAdapter(adapter = new Adapter(names));
    }

    @NonNull
    @Override
    public Observable<Integer> observePlaylistPositionClicks() {
        return adapter.observePositionClicks();
    }

    @NonNull
    @Override
    public Observable<String> observePlaylistCreations() {
        return adapter.observeCreationClicks();
    }

    static class Adapter extends RecyclerView.Adapter<AddToPlaylistView.ViewHolder> {

        private static final int TYPE_NEW = 0;
        private static final int TYPE_EXISTENT = 1;

        private @NonNull List<String> names;

        private @Nullable Subscription subscription;

        @Nullable PublishSubject<Integer> positionClicks;
        @Nullable PublishSubject<String> newCreations;

        Adapter(@NonNull List<String> names) {
            this.names = names;
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            switch (viewType) {
                case TYPE_NEW:
                    return new ViewHolder(new AddToPlaylistInputView(parent.getContext()));
                case TYPE_EXISTENT:
                    return new ViewHolder(new TextView(parent.getContext()));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            switch (getItemViewType(position)) {
                case TYPE_NEW:
                    if (subscription != null && !subscription.isUnsubscribed()) {
                        subscription.unsubscribe();
                    }
                    subscription = ((AddToPlaylistInputView) holder.itemView).observeCreateClicks()
                        .observeOn(Schedulers.computation())
                        .subscribeOn(Schedulers.computation())
                        .compose(RxLifecycleAndroid.<String>bindView(holder.itemView))
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(final String name) {
                               if (newCreations != null) {
                                   newCreations.onNext(name);
                               }
                            }
                        });
                    break;
                case TYPE_EXISTENT:
                    ((TextView) holder.itemView).setText(names.get(position - 1));
                    holder.itemView.setTag(position - 1);
                    holder.itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            if (positionClicks != null) {
                                positionClicks.onNext((Integer) v.getTag());
                            }
                        }
                    });
            }
        }

        @Override
        public int getItemCount() {
            return names.size() + 1;
        }

        @Override
        public int getItemViewType(final int position) {
            if (position == 0)
                return TYPE_NEW;
            return TYPE_EXISTENT;
        }

        @NonNull Observable<Integer> observePositionClicks() {
            if (positionClicks == null) {
                positionClicks = PublishSubject.create();
            }
            return positionClicks;
        }

        @NonNull Observable<String> observeCreationClicks() {
            if (newCreations == null) {
                newCreations = PublishSubject.create();
            }
            return newCreations;
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(final View itemView) {
            super(itemView);
        }

    }

}
