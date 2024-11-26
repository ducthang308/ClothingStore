package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.List;

import DTO.MessageDTO;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListAdapterViewHolder> {

    private List<MessageDTO> messageList;
    private Context context;

    public MessageListAdapter(Context context, List<MessageDTO> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_list, parent, false);
        return new MessageListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapterViewHolder holder, int position) {
        MessageDTO message = messageList.get(position);
        holder.senderName.setText(message.getSenderFullName());
        holder.messageContent.setText(message.getContent());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageListAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView senderName, messageContent, messageTime;

        public MessageListAdapterViewHolder(View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.sender_name);
            messageContent = itemView.findViewById(R.id.message_content);
        }
    }
}

