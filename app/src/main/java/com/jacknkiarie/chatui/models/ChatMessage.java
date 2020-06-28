package com.jacknkiarie.chatui.models;

import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.concurrent.TimeUnit;


/**
 * Chat Message model used when ChatMessages are required, either to be sent or received,
 * all messages that are to be shown in the chat-ui must be contained in this model.
 */
@Entity(tableName = "chat_message_table")
public class ChatMessage {
    @PrimaryKey
    @NonNull
    public String id;

    @NonNull
    public String chatId;

    public String message;
    public long timestamp;
    public Type type;
    public String sender;
    public int fromUserId;
    public int toUserId;
    public String flag;
    public boolean messageRead;


    @Ignore
    public ChatMessage( @NonNull String id, String message, long timestamp, Type type) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
    }

    @Ignore
    public ChatMessage(String id, String message, long timestamp, Type type, String sender) {
        this(id, message, timestamp, type);
        this.sender = sender;
    }

    public ChatMessage(String id, String message, long timestamp, Type type, String sender,
                       int fromUserId, int toUserId, String chatId, String flag, boolean messageRead) {
        this(id, message, timestamp, type, sender);
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.chatId = chatId;
        this.flag = flag;
        this.messageRead = messageRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public String getFormattedTime() {

        long oneDayInMillis = TimeUnit.DAYS.toMillis(1); // 24 * 60 * 60 * 1000;

        long timeDifference = System.currentTimeMillis() - timestamp;

        return timeDifference < oneDayInMillis
                ? DateFormat.format("hh:mm a", timestamp).toString()
                : DateFormat.format("dd MMM - hh:mm a", timestamp).toString();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @NonNull
    public String getChatId() {
        return chatId;
    }

    public void setChatId(@NonNull String chatId) {
        this.chatId = chatId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isMessageRead() {
        return messageRead;
    }

    public void setMessageRead(boolean messageRead) {
        this.messageRead = messageRead;
    }

    public enum Type {
        SENT(0), RECEIVED(1);

        private int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
