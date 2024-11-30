package com.example.duanandroid.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapter.ChatAdapter;
import DTO.SendMessageRequest;
import Interface.APIClient;
import Interface.ApiMessage;
import Interface.PreferenceManager;
import Model.Message;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chatUserActivity extends AppCompatActivity {

    private EditText edtMessage;
    private ImageButton sendButton;
    private RecyclerView recyclerViewMessages;
    private ChatAdapter chatAdapter;
    private int senderId;
    private int conversationId;
    private int receiverId = 1;
    private ApiMessage apiMessage;
    private String token;
    private ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);

        edtMessage = findViewById(R.id.edt_message);
        sendButton = findViewById(R.id.send_button);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);

        // Lấy thông tin người dùng
        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();
        senderId = preferenceManager.getUserId();
        conversationId = preferenceManager.getId();

        apiMessage = APIClient.getClient().create(ApiMessage.class);

        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(new ArrayList<>(), senderId);
        recyclerViewMessages.setAdapter(chatAdapter);


        fetchMessages();

        sendButton.setOnClickListener(v -> {
            String messageContent = edtMessage.getText().toString().trim();
            if (!messageContent.isEmpty()) {
                sendMessage(messageContent);
            } else {
                Toast.makeText(chatUserActivity.this, "Message content cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        arrow_back = findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent accountIntent = new Intent(chatUserActivity.this, mainpageActivity.class);
                finish();
            }
        });
    }

    // Lấy danh sách tin nhắn từ API
    private void fetchMessages() {
        apiMessage.getAllMessagesByConversationId("Bearer " + token, conversationId).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Message> messages = response.body();
                    chatAdapter.updateMessages(messages); // Cập nhật danh sách tin nhắn
                    recyclerViewMessages.scrollToPosition(chatAdapter.getItemCount() - 1); // Cuộn đến cuối
                } else {
                    Log.e("API Error", "Failed to fetch messages");
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("API Error", "Request failed: " + t.getMessage());
                Toast.makeText(chatUserActivity.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Gửi tin nhắn
    private void sendMessage(String messageContent) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(senderId, receiverId, messageContent, new Date());
        apiMessage.sendMessage("Bearer " + token, sendMessageRequest).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Message newMessage = response.body();
                    chatAdapter.addMessage(newMessage);
                    recyclerViewMessages.scrollToPosition(chatAdapter.getItemCount() - 1);
                    edtMessage.setText("");
                } else {
                    Toast.makeText(chatUserActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(chatUserActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}