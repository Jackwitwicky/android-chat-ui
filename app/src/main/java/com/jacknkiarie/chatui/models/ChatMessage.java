package com.jacknkiarie.chatui.models;

import android.text.format.DateFormat;

import androidx.room.Entity;
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
    public String id;
    public String message;
    public long timestamp;

    @TypeConverters(MessageTypeConverter.class)
    public Type type;

    public String sender;

    public ChatMessage(String message, long timestamp, Type type) {
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
    }

    public ChatMessage(String message, long timestamp, Type type, String sender) {
        this(message, timestamp, type);
        this.sender = sender;
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
