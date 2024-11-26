package Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Interface.PreferenceManager;
import Model.Message;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> messageList;
    private int senderId;

    public ChatAdapter(List<Message> messages, int senderId) {
        this.messageList = messages != null ? messages : new ArrayList<>();
        this.senderId = senderId;
    }

    public void updateMessages(List<Message> messages) {
        this.messageList.clear();
        this.messageList.addAll(messages);
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
        notifyItemInserted(messageList.size() - 1);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = messageList.get(position);

        boolean isSender = message.getSenderId() == senderId;

        Log.d("ChatAdapter", "Sender ID: " + senderId + ", Message Sender ID: " + message.getSenderId());
        Log.d("ChatAdapter", "Is Sender: " + isSender);

        // Kiểm tra và thay đổi layoutParams cho TextView (messageContent)
        LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) holder.messageContent.getLayoutParams();
        if (textParams == null) {
            textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
        }
        textParams.gravity = isSender ? Gravity.END : Gravity.START; // Căn nội dung của TextView

        // Cập nhật lại layout params cho TextView
        holder.messageContent.setLayoutParams(textParams);

        // Kiểm tra và thay đổi layoutParams cho ImageView (imgUser)
        LinearLayout.LayoutParams imgParams = (LinearLayout.LayoutParams) holder.imgUser.getLayoutParams();
        if (imgParams == null) {
            imgParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
        }
        imgParams.gravity = isSender ? Gravity.END : Gravity.START; // Căn avatar

        // Cập nhật lại layout params cho ImageView
        holder.imgUser.setLayoutParams(imgParams);

        // Cập nhật các thuộc tính khác cho TextView và ImageView
        if (isSender) {
            // Người gửi: căn phải, ẩn avatar
            holder.imgUser.setVisibility(View.GONE); // Ẩn avatar
            holder.messageContent.setBackgroundResource(R.drawable.bg_corner_chat_sendme);
            holder.messageContent.setTextColor(Color.parseColor("#333333"));
        } else {
            // Người nhận: căn trái, hiển thị avatar
            holder.imgUser.setVisibility(View.VISIBLE); // Hiển thị avatar
            holder.messageContent.setBackgroundResource(R.drawable.bg_corner_chat);
            holder.messageContent.setTextColor(Color.parseColor("#FFFFFF"));
        }

        // Cập nhật nội dung tin nhắn
        holder.messageContent.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // ViewHolder cho từng item tin nhắn trong RecyclerView
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageContent;
        ImageView imgUser;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContent = itemView.findViewById(R.id.txt_message_content);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }
}
