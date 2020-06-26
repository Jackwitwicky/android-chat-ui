package com.jacknkiarie.chatui.models;

import androidx.room.TypeConverter;

public class MessageTypeConverter {

    @TypeConverter
    public static ChatMessage.Type toType(int type) {
        if (type == ChatMessage.Type.SENT.getCode()) {
            return ChatMessage.Type.SENT;
        }
        else if (type == ChatMessage.Type.RECEIVED.getCode()) {
            return ChatMessage.Type.RECEIVED;
        }
        else {
            throw new IllegalArgumentException("Could not recognize type");
        }
    }

    @TypeConverter
    public static int toInteger(ChatMessage.Type type) {
        return type.getCode();
    }
}
