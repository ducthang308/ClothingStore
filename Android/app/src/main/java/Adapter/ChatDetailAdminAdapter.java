package Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import DTO.MessageDTO;
import Model.Message;

public class ChatDetailAdminAdapter extends RecyclerView.Adapter<ChatDetailAdminAdapter.ChatViewHolder> {

    private final List<MessageDTO> messageList;
    private final int senderId;

    public ChatDetailAdminAdapter(List<MessageDTO> messages, int senderId) {
        this.messageList = messages != null ? new ArrayList<>(messages) : new ArrayList<>();
        this.senderId = senderId;
    }

    public void updateMessages(List<MessageDTO> messages) {
        messageList.clear();
        if (messages != null) {
            messageList.addAll(messages);
        }
        notifyDataSetChanged();
    }

    public void addMessage(MessageDTO messageDTO) {
        if (messageDTO != null) {
            messageList.add(messageDTO);
            notifyItemInserted(messageList.size() - 1);
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        MessageDTO messageDTO = messageList.get(position);

        boolean isAdmin = messageDTO.getSenderId() == senderId;

        holder.messageContent.setText(messageDTO.getContent());

        if (isAdmin) {
            holder.imgUser.setVisibility(View.VISIBLE);
            holder.messageContent.setBackgroundResource(R.drawable.bg_corner_chat_sendme);
            holder.messageContent.setTextColor(Color.parseColor("#333333"));
        } else {
            holder.imgUser.setVisibility(View.GONE);
            holder.imgUser.setImageResource(R.drawable.logo_shop);
            holder.messageContent.setBackgroundResource(R.drawable.bg_corner_chat);
            holder.messageContent.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser;
        TextView messageContent;

        public ChatViewHolder(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            messageContent = itemView.findViewById(R.id.txt_message_content);
        }
    }
}

