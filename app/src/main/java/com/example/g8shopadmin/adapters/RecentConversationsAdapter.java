package com.example.g8shopadmin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g8shopadmin.databinding.ItemContainerReceivedMessageBinding;
import com.example.g8shopadmin.databinding.ItemContainerRecentConversionBinding;
import com.example.g8shopadmin.listeners.ConversionListener;
import com.example.g8shopadmin.models.ChatMessage;
import com.example.g8shopadmin.models.UserChat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final ConversionListener conversionListener;
    private final Context context;

    public RecentConversationsAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener, Context context) {
        this.chatMessages = chatMessages;
        this.context = context;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                ItemContainerRecentConversionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder {
        ItemContainerRecentConversionBinding binding;

        ConversionViewHolder(ItemContainerRecentConversionBinding itemContainerRecentConversionBinding) {
            super(itemContainerRecentConversionBinding.getRoot());
            binding = itemContainerRecentConversionBinding;
        }

        void setData(ChatMessage chatMessage) {
            loadImage(chatMessage.conversionImage, binding);
            binding.textName.setText(chatMessage.conversionName);
            binding.textRecentMessage.setText(chatMessage.message);
            binding.getRoot().setOnClickListener(v -> {
                UserChat user = new UserChat();
                user.id = chatMessage.conversionId;
                user.fullName = chatMessage.conversionName;
                user.image = chatMessage.conversionImage;
                conversionListener.onConversionClicked(user);
            });
        }
    }

    private void loadImage(String image, ItemContainerRecentConversionBinding binding) {
        try {
            Picasso.with(context).load(image).into(binding.imageProfile);
        } catch (Exception error) {
            Log.e("ERROR", "activity_profile loadImage: ", error);
        }
    }


}
