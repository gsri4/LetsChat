package com.innominds.letschat.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.innominds.letschat.R;
import com.innominds.letschat.helper.Constants;
import com.innominds.letschat.models.MessageHistory;
import com.innominds.letschat.services.LetsChatConnection;
import com.innominds.letschat.services.LetsChatConnectionService;

import java.util.ArrayList;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;


public class ChatActivity extends AppCompatActivity {
    private static final String TAG ="ChatActivity";

    private String contactJid;
    private ChatView mChatView;
    private BroadcastReceiver mBroadcastReceiver;
    private String senderJId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mChatView =(ChatView) findViewById(R.id.rooster_chat_view);

        mChatView.setOnSentMessageListener(new ChatView.OnSentMessageListener(){
            @Override
            public boolean sendMessage(ChatMessage chatMessage){
                // perform actual message sending
                if (LetsChatConnectionService.getState().equals(LetsChatConnection.ConnectionState.CONNECTED)) {
                    Log.d(TAG, "The client is connected to the server,Sending Message");
                    //Send the message to the server
                    Intent intent = new Intent(LetsChatConnectionService.SEND_MESSAGE);
                    intent.putExtra(LetsChatConnectionService.BUNDLE_MESSAGE_BODY,
                            mChatView.getTypedMessage());
                    intent.putExtra(LetsChatConnectionService.BUNDLE_TO, contactJid);
                    sendBroadcast(intent);

                    //storing message to database for mataining chat history
                    //Creating and saving data to Message history table
                    MessageHistory messageHistory = new MessageHistory();
                    messageHistory.setMsgId(mChatView.getTypedMessage());
                    messageHistory.setSenderJitId(senderJId);
                    messageHistory.setReceiverJitId(contactJid);
                    messageHistory.setDeliveryStatus(chatMessage.getType().name());
                    messageHistory.setTimeStamp(chatMessage.getTimestamp());
                    messageHistory.save();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Client not connected to server ,Message not sent!",
                            Toast.LENGTH_LONG).show();
                }
                //message sending ends here
                return true;
            }
        });





        Intent intent = getIntent();
        contactJid = intent.getStringExtra("EXTRA_CONTACT_JID");
        setTitle(contactJid);

         senderJId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(Constants.XMPP_JID,null);
        checkandAddChatHistory(senderJId,contactJid);
    }

    /**
     * Getting records from database and adding to list
     * @param
     */
    private void checkandAddChatHistory(String senderJId,String receiverJid) {
        ArrayList<MessageHistory> messageHistoriesList = MessageHistory.getAllMessageHistory(senderJId,receiverJid);
        if(!messageHistoriesList.isEmpty()) {
            ArrayList<ChatMessage> chatMessageArrayList = new ArrayList<>();
            ChatMessage chatMessage = null;
            for (MessageHistory messageHistory :
                    messageHistoriesList) {
                if(messageHistory.getDeliveryStatus().equalsIgnoreCase(Constants.TYPE_SENT)) {
                     chatMessage = new ChatMessage(messageHistory.getMsgId(), messageHistory.getTimeStamp(), ChatMessage.Type.SENT);
                }else{
                     chatMessage = new ChatMessage(messageHistory.getMsgId(), messageHistory.getTimeStamp(), ChatMessage.Type.RECEIVED);

                }
                chatMessageArrayList.add(chatMessage);
            }
            mChatView.addMessages(chatMessageArrayList);
        }else{
            //do nothing
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action)
                {
                    case LetsChatConnectionService.NEW_MESSAGE:
                        String from = intent.getStringExtra(LetsChatConnectionService.BUNDLE_FROM_JID);
                        String body = intent.getStringExtra(LetsChatConnectionService.BUNDLE_MESSAGE_BODY);

                        if ( from.equals(contactJid))
                        {
                            ChatMessage chatMessage = new ChatMessage(body,System.currentTimeMillis(), ChatMessage.Type.RECEIVED);
                            mChatView.addMessage(chatMessage);

                        }else
                        {
                            Log.d(TAG,"Got a message from jid :"+from);
                        }

                        return;
                }

            }
        };

        IntentFilter filter = new IntentFilter(LetsChatConnectionService.NEW_MESSAGE);
        registerReceiver(mBroadcastReceiver,filter);


    }
}
