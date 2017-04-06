package com.u.tallerify.view.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
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
    public void setAdapter(@NonNull FirebaseRecyclerAdapter adapter) {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                smoothScrollToPosition(getAdapter().getItemCount() - 1);
            }
        });

        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(final View v, final int left, final int top, final int right, final int bottom,
                    final int oldLeft, final int oldTop, final int oldRight,
                    final int oldBottom) {
                if (bottom < oldBottom) {
                    ChatListView.this.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (getAdapter().getItemCount() > 0) {
                                smoothScrollToPosition(getAdapter().getItemCount() - 1);
                            }
                        }
                    }, 100);
                }
            }
        });

        super.setAdapter(adapter);
    }

}
