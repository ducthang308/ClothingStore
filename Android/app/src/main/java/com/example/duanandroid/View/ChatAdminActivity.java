package com.example.duanandroid.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.MessageListAdapter;
import DTO.MessageDTO;
import Interface.APIClient;
import Interface.ApiMessage;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMessages;
    private MessageListAdapter messageAdapter;
    private List<MessageDTO> messageList = new ArrayList<>();
    private ApiMessage apiMessage;
    private String token;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_admin);

        recyclerViewMessages = findViewById(R.id.recycler_view_messages);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));

        messageAdapter = new MessageListAdapter(this, messageList);
        recyclerViewMessages.setAdapter(messageAdapter);

        PreferenceManager preferenceManager = new PreferenceManager(this);
        token = preferenceManager.getToken();
        userId = preferenceManager.getUserId();

        apiMessage = APIClient.getClient().create(ApiMessage.class);

        ImageButton btnback = findViewById(R.id.back_arrow);
        btnback.setOnClickListener(v -> {
            Intent intent = new Intent(ChatAdminActivity.this, mainpageAdminActivity.class);
            startActivity(intent);
        });

        loadMessages();
    }

    private void loadMessages() {
        apiMessage.getAllMessagesByReceiverId("Bearer " + token, userId)
                .enqueue(new Callback<List<MessageDTO>>() {
                    @Override
                    public void onResponse(Call<List<MessageDTO>> call, Response<List<MessageDTO>> response) {
                        if (response.isSuccessful()) {
                            messageList.addAll(response.body());
                            messageAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("API Error", "Error code: " + response.code());
                            Toast.makeText(ChatAdminActivity.this, "Failed to load messages. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MessageDTO>> call, Throwable t) {
                        Log.e("API Failure", "Failure message: " + t.getMessage(), t);
                        Toast.makeText(ChatAdminActivity.this, "Network error: Could not load messages. Please check your internet connection.", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
