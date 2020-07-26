# Android Chat UI

Android Chat UI is a beautifully designed chat interface ready to be plugged into your amazing app. The library is designed to work with minimal additional configuration  
 and offers a seamless look and feeling that professional apps such as WhatsApp can offer.


Chat UI             |
:-------------------------:|
![](https://user-images.githubusercontent.com/8895134/88364693-644b6680-cd8c-11ea-83bd-3e866fb5f79f.png)  |

## Acknowledgment

The initial project is a fork from [Timi's Android Chat UI](https://github.com/timigod/android-chat-ui). However there are some key features I needed
for my particular use case. Some of the notable updates made to the library:-

* Upgraded the project to AndroidX
* Updated the list container to RecyclerView
* Added PagedList support to the list of chat messages
* Defined each Chat Message as an entity to allow easy saving of the messages to your Room database

## Getting Started

This module is designed to be as seamless as possible and should work as a plug and play to your existing android application
requiring minimal modification.

## How it Works

The library builds upon a stunning Chat UI and couples functionality that allows handling seamless loading of thousands of messages stored on
the local DB to the UI through pagination. The ```ChatMessage``` class is what one must create and store in their db. Then provide a paged list of  
ChatMessage object to the ChatView adapter and it will handle the rest.

### Prerequisites

What things you need to install the software and how to install them

```
Android Studio

A working Android Project

Minimum sdk used :- 16

```

### Installing

Use the following steps to get up and running as fast as possible and get back to building cool content.

#### Gradle

```
//on your project wide build.gradle file, add the maven repo link

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Then next step is to add the library dependency on your app build.grade file

```
//on your app build.gradle file, add the sign in ui

dependencies {
	        implementation 'com.github.Jackwitwicky:android-chat-ui:v0.0.2-alpha9'
	}
```

#### Customization

To actually use the library. Add the following piece of code in your calling activity XML file


```
<com.jacknkiarie.chatui.ChatView
        android:id="@+id/chat_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        chatview:backgroundColor="@color/off_white"
        chatview:bubbleElevation="elevated"
        chatview:bubbleBackgroundRcv="@color/blue"
        chatview:bubbleBackgroundSend="@color/white"
        chatview:inputHint="@string/start_typing"/>

```

That's it! That is all you need to display a full chat screen that will handle displaying of messages based on if the message is from a sender or receiver.

 However the library offers multiple configurations you can use setup properly


```
chatview:backgroundColor=""
chatview:inputBackgroundColor=""
chatview:inputUseEditorAction="" // true or false
chatview:inputTextAppearance=""
chatview:inputTextSize=""
chatview:inputTextColor=""
chatview:inputHint=""
chatview:inputHintColor=""
chatview:sendBtnIcon=""
chatview:sendBtnIconTint=""
chatview:sendBtnBackgroundTint=""

chatview:bubbleBackgroundRcv="" // color
chatview:bubbleBackgroundSend="" //color
chatview:bubbleElevation="" // "flat" or "elevated"

```

In your calling activity. You can implement several methods to help you get Chat UI working well. The list of messages is controlled by an adapter
that expects a paged list of ChatMessage objects.

```
    // get access to your chat view from XML
    val chatView = findViewById<View>(R.id.chat_view) as ChatView
    
    // observe any changes to list of messages stored in your local Room DB
    // this logic will automatically update the Chat UI when a new message is inserted
    mainViewModel.getChatMessages().observe(this, Observer { messages ->
            if (messages != null) chatView.chatViewListAdapter.submitList(messages)
    })
    
    // call this to save any messages after user clicks the send button 
    chatView.setOnSentMessageListener {
            mainViewModel.saveMessage(it)
            true
    }
```
### NB
The View Model that is responsible to fetching the list of messages and saving any new messages to Room DB has been omitted for brevity.
A full working chat app sample can be found here [Android-Chat-Sample](https://github.com/Jackwitwicky/android-chat-sample) for reference

## Built With

* [Ubuntu 18.04.3(https://www.ubuntu.com/desktop/) - Operating System Used
* [Android Studio](https://developer.android.com/studio/index.html) - Development Environment
* Love - Key ingredient

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Author

 **Jack Kiarie** - *Initial work* - [Other Works](https://incobeta.com)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details


