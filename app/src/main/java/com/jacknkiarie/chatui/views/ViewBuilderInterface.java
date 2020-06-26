package com.jacknkiarie.chatui.views;

import android.content.Context;

/**
 * Interface for the viewbuilder, is used so that people can create their own
 * ViewBuilders to create custom views.
 *
 * Created by James Lendrem
 */
public interface ViewBuilderInterface {
    /**
     * Returns a MessageView object which is used to display messages that the chat-ui
     * has received.
     * @param context A context that is used to instantiate the view.
     * @return        MessageView object for displaying received messages.
     */
    MessageView buildRecvView(Context context);

    /**
     * Returns a MessageView object which is used to display messages that the chat-ui
     * has sent.
     * @param context A context that is used to instantiate the view.
     * @return        MessageView object for displaying sent messages.
     */
    MessageView buildSentView(Context context);

}
