package com.u.tallerify.view.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.u.tallerify.contract.profile.ChatListContract;

/**
 * Created by saguilera on 4/4/17.
 */
public class ChatListView extends RecyclerView
        implements ChatListContract.View {

    public ChatListView(final Context context) {
        super(context);
    }

    public ChatListView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatListView(final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@NonNull final FirebaseRecyclerAdapter adapter) {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int count = adapter.getItemCount();
                int lastVisiblePosition =
                    ((LinearLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition();

                if (lastVisiblePosition == -1 ||
                    (positionStart >= (count - 1) &&
                        lastVisiblePosition == (positionStart - 1))) {
                    scrollToPosition(positionStart);
                }
            }
        });

        super.setAdapter(adapter);
    }

}
