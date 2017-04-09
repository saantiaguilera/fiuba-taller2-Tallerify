package com.u.tallerify.view.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.R;
import com.u.tallerify.contract.playlist.AddToPlaylistContract;
import com.u.tallerify.utils.MetricsUtils;
import java.util.ArrayList;
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

        int width = MetricsUtils.getScreenPixelBounds(context).x;
        width -= (width / 5);

        setLayoutParams(new ViewGroup.LayoutParams(width,
            ViewGroup.LayoutParams.WRAP_CONTENT));
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setAdapter(adapter = new Adapter(new ArrayList<String>()));
    }

    @Override
    public void setPlaylists(@NonNull final List<String> names) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return adapter.names.size() + 1;
            }

            @Override
            public int getNewListSize() {
                return names.size() + 1;
            }

            @Override
            public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
                return (oldItemPosition == 0 || newItemPosition == 0) ||
                    adapter.names.get(oldItemPosition - 1).equals(names.get(newItemPosition - 1));
            }
        });

        adapter.names(names);

        diffResult.dispatchUpdatesTo(adapter);
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

    @Override
    public void setEditable(final boolean editable) {
        adapter.editable(editable);
        adapter.notifyItemChanged(0);
    }

    static class Adapter extends RecyclerView.Adapter<AddToPlaylistView.ViewHolder> {

        private static final int TYPE_NEW = 0;
        private static final int TYPE_EXISTENT = 1;

        @NonNull List<String> names;

        private @Nullable Subscription subscription;

        @Nullable PublishSubject<Integer> positionClicks;
        @Nullable PublishSubject<String> newCreations;

        private boolean editable = true;

        Adapter(@NonNull List<String> names) {
            this.names = names;
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            switch (viewType) {
                case TYPE_NEW:
                    return new ViewHolder(new AddToPlaylistInputView(parent.getContext()));
                case TYPE_EXISTENT:
                    TextView view = new TextView(parent.getContext());
                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        parent.getContext().getResources().getDimensionPixelSize(R.dimen.view_playlist_add_item_height)));
                    view.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    view.setMaxLines(1);
                    view.setTextSize(16);
                    view.setEllipsize(TextUtils.TruncateAt.END);
                    return new ViewHolder(view);
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
                    ((AddToPlaylistInputView) holder.itemView).setEditable(editable);
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

        void editable(boolean editable) {
            this.editable = editable;
        }

        void names(@NonNull List<String> names) {
            this.names = names;
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
