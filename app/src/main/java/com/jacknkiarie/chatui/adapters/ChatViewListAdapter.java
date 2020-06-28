package com.jacknkiarie.chatui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jacknkiarie.chatui.models.ChatMessage;
import com.jacknkiarie.chatui.viewholders.MessageViewHolder;
import com.jacknkiarie.chatui.views.ViewBuilder;
import com.jacknkiarie.chatui.views.ViewBuilderInterface;

import java.util.ArrayList;


/**
 * Paged List Adapter for use in the recycler view to display messages using the Message View Holder
 * <p>
 * Created by Timi
 * Extended by James Lendrem, Samuel Ojo, Jack Kiarie
 */

public class ChatViewListAdapter extends PagedListAdapter<ChatMessage, MessageViewHolder> {


    public final int STATUS_SENT = 0;
    public final int STATUS_RECEIVED = 1;

    private int backgroundRcv, backgroundSend;
    private int bubbleBackgroundRcv, bubbleBackgroundSend;
    private float bubbleElevation;
    private ViewBuilderInterface viewBuilder = new ViewBuilder();

    ArrayList<ChatMessage> chatMessages;

    Context context;
    LayoutInflater inflater;

    public ChatViewListAdapter(Context context, ViewBuilderInterface viewBuilder, int backgroundRcv, int backgroundSend, int bubbleBackgroundRcv, int bubbleBackgroundSend, float bubbleElevation) {
        super(DIFF_CALLBACK);
        this.chatMessages = new ArrayList<>();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.backgroundRcv = backgroundRcv;
        this.backgroundSend = backgroundSend;
        this.bubbleBackgroundRcv = bubbleBackgroundRcv;
        this.bubbleBackgroundSend = bubbleBackgroundSend;
        this.bubbleElevation = bubbleElevation;
        this.viewBuilder = viewBuilder;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View convertView = viewBuilder.buildRecvView(context);
        if (viewType == STATUS_SENT) {
            convertView = viewBuilder.buildSentView(context);
        }

        convertView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        MessageViewHolder messageViewHolder = new MessageViewHolder(convertView, backgroundRcv, backgroundSend, bubbleBackgroundRcv, bubbleBackgroundSend);

        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        int type = getItemViewType(position);
        ChatMessage chatMessage = getItem(position);
        if (chatMessage != null) {
            holder.setMessage(getItem(position).getMessage());
            holder.setTimestamp(getItem(position).getFormattedTime());
            holder.setElevation(bubbleElevation);
            holder.setBackground(type);
            String sender = getItem(position).getSender();
            if (sender != null) {
                holder.setSender(sender);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage currentMessage = getItem(position);
        if (currentMessage != null) {
            return currentMessage.getType().ordinal();
        }

        // else default to sent message
        return ChatMessage.Type.SENT.getCode();
    }

    public void addMessage(ChatMessage message) {
        chatMessages.add(message);
        notifyDataSetChanged();
    }

    public void addMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages.addAll(chatMessages);
        notifyDataSetChanged();
    }

    public void removeMessage(int position) {
        if (this.chatMessages.size() > position) {
            this.chatMessages.remove(position);
        }
    }

    public void clearMessages() {
        this.chatMessages.clear();
        notifyDataSetChanged();
    }

    private static DiffUtil.ItemCallback<ChatMessage> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ChatMessage>() {
                // Message details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(ChatMessage oldMessage, ChatMessage newMessage) {
                    return oldMessage.getId() == newMessage.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(ChatMessage oldMessage,
                                                  ChatMessage newMessage) {
                    return (oldMessage.getMessage() == newMessage.getMessage() &&
                            oldMessage.getTimestamp() == newMessage.getTimestamp());
                }
            };

}