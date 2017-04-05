package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.u.tallerify.R;
import com.u.tallerify.contract.profile.ChatListContract;
import com.u.tallerify.model.Message;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.utils.FrescoImageController;
import java.util.HashMap;
import org.json.JSONObject;

/**
 * Created by saguilera on 4/4/17.
 */
public class ChatListPresenter extends Presenter<ChatListContract.View>
        implements ChatListContract.Presenter {

    private static final @NonNull String DATABASE_KEY = "messages";

    @NonNull User him;
    @NonNull User me;

    public ChatListPresenter(@NonNull User me, @NonNull User him) {
        this.him = him;
        this.me = me;
    }

    @Override
    protected void onAttach(@NonNull final ChatListContract.View view) {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>( // TODO, this should be a separate class with the VH
            Message.class,
            R.layout.view_chat_message,
            MessageViewHolder.class,
            database.child(DATABASE_KEY)
        ) {

            @Override
            protected Message parseSnapshot(final DataSnapshot snapshot) {
                // Since the default parses asks us to keep either setter/getters (we dont allow mutable models
                // Or public fields (wtf m8), we do it on our own
                return new Gson().fromJson(
                    new Gson().toJson(snapshot.getValue()),
                    Message.class
                );
            }

            @Override
            protected void populateViewHolder(final MessageViewHolder viewHolder, final Message model,
                    final int position) {
                viewHolder.textView.setText(model.message());
                FrescoImageController.create()
                    .load(model.senderId() == me.id() ? me.pictures().get(0) : him.pictures().get(0))
                    .into(viewHolder.imageView);
            }

        };

        view.setAdapter(adapter);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        private @NonNull TextView textView;
        private @NonNull SimpleDraweeView imageView;

        public MessageViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.view_chat_message_text);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.view_chat_message_image);
        }

    }

}
