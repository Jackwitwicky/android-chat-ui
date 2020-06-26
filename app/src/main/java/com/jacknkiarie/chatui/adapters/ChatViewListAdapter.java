package com.jacknkiarie.chatui.adapters;

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

//    @Override
//    public int getCount() {
//        return chatMessages.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return chatMessages.get(position);
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
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
        holder.setMessage(chatMessages.get(position).getMessage());
        holder.setTimestamp(chatMessages.get(position).getFormattedTime());
        holder.setElevation(bubbleElevation);
        holder.setBackground(type);
        String sender = chatMessages.get(position).getSender();
        if (sender != null) {
            holder.setSender(sender);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return chatMessages.get(position).getType().ordinal();
    }

//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        MessageViewHolder holder;
//        int type = getItemViewType(position);
//        if (convertView == null) {
//            switch (type) {
//                case STATUS_SENT:
//                    convertView = viewBuilder.buildSentView(context);
//                    break;
//                case STATUS_RECEIVED:
//                    convertView = viewBuilder.buildRecvView(context);
//                    break;
//            }
//
//            holder = new MessageViewHolder(convertView, backgroundRcv, backgroundSend, bubbleBackgroundRcv, bubbleBackgroundSend);
//            convertView.setTag(holder);
//        } else {
//            holder = (MessageViewHolder) convertView.getTag();
//        }
//
//        holder.setMessage(chatMessages.get(position).getMessage());
//        holder.setTimestamp(chatMessages.get(position).getFormattedTime());
//        holder.setElevation(bubbleElevation);
//        holder.setBackground(type);
//        String sender = chatMessages.get(position).getSender();
//        if (sender != null) {
//            holder.setSender(sender);
//        }
//
//        return convertView;
//    }

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

                @Override
                public boolean areContentsTheSame(ChatMessage oldMessage,
                                                  ChatMessage newMessage) {
                    return (oldMessage.getSender().equals(newMessage.getSender()) &&
                            oldMessage.getMessage().equals(newMessage.getMessage()) &&
                            oldMessage.getTimestamp() == newMessage.getTimestamp());
                }
            };

}