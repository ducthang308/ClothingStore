package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duanandroid.R;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapter.ChatDetailAdminAdapter;
import Adapter.MessageListAdapter;
import DTO.MessageDTO;
import DTO.SendMessageRequest;
import Interface.APIClient;
import Interface.ApiMessage;
import Interface.PreferenceManager;
import Model.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMessages;
    private ChatDetailAdminAdapter messageAdapter;  // Thay vì MessageListAdapter
    private List<MessageDTO> messageList = new ArrayList<>();
    private ApiMessage apiMessage;
    private String token;
    private int userId;
    private int conversationId;
    private int senderId;
    private EditText edtMessage;
    private ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail_admin);

        edtMessage = findViewById(R.id.edt_message);
        sendButton = findViewById(R.id.send_button);

        conversationId = getIntent().getIntExtra("conversationId", -1);
        senderId = getIntent().getIntExtra("senderId", -1);
        if (conversationId == -1) {
            Toast.makeText(this, "Invalid conversationId", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));

        messageAdapter = new ChatDetailAdminAdapter(messageList, userId);  // Sử dụng ChatDetailAdminAdapter
        recyclerViewMessages.setAdapter(messageAdapter);

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();
        userId = preferenceManager.getUserId();

        apiMessage = APIClient.getClient().create(ApiMessage.class);

        loadMessagesForConversation(conversationId);

        ImageButton btnBack = findViewById(R.id.arrow_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ChatDetailAdminActivity.this, ChatAdminActivity.class);
            startActivity(intent);
        });

        sendButton.setOnClickListener(v -> {
            String messageContent = edtMessage.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                sendMessage(messageContent);
            } else {
                Toast.makeText(ChatDetailAdminActivity.this, "Message content cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMessagesForConversation(int conversationId) {
        Log.d("ChatDetailAdminActivity", "Loading messages for conversationId: " + conversationId);

        apiMessage.getAllMessagesByConversationId("Bearer " + token, conversationId)
                .enqueue(new Callback<List<Message>>() {
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Message> messages = response.body();
                            Log.d("ChatDetailAdminActivity", "Received " + messages.size() + " messages");
                            messageList.clear();
                            for (Message message : messages) {
                                MessageDTO messageDTO = convertToMessageDTO(message);
                                messageList.add(messageDTO);
                            }
                            messageAdapter.updateMessages(messageList);
                        } else {
                            Log.e("API Error", "Error code: " + response.code());
                            Toast.makeText(ChatDetailAdminActivity.this, "Failed to load messages for conversation", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {
                        Log.e("API Failure", "Failed to fetch messages for conversation", t);
                        Toast.makeText(ChatDetailAdminActivity.this, "Network error while loading conversation messages.", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private MessageDTO convertToMessageDTO(Message message) {
        if (message == null) return null;
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(message.getContent());
        messageDTO.setReceiverId(message.getReceiverId());
        messageDTO.setSenderId(message.getSenderId());
        messageDTO.setCreateAt(message.getCreateAt());
        messageDTO.setConversationId(message.getConversationId());
        return messageDTO;
    }

    private Message convertToMessage(MessageDTO messageDTO) {
        if (messageDTO == null) return null;
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setReceiverId(messageDTO.getReceiverId());
        message.setSenderId(messageDTO.getSenderId());
        message.setCreateAt(messageDTO.getCreateAt());
        message.setConversationId(messageDTO.getConversationId());
        return message;
    }


    private void sendMessage(String messageContent) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(userId, senderId, messageContent, new Date());
        apiMessage.sendMessage("Bearer " + token, sendMessageRequest).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Message newMessage = response.body();
                    MessageDTO newMessageDTO = convertToMessageDTO(newMessage);
                    messageAdapter.addMessage(newMessageDTO);
                    recyclerViewMessages.scrollToPosition(messageAdapter.getItemCount() - 1);
                    edtMessage.setText(""); // Làm sạch ô nhập
                } else {
                    Toast.makeText(ChatDetailAdminActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(ChatDetailAdminActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
