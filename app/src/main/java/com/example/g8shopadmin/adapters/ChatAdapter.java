package com.example.g8shopadmin.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g8shopadmin.databinding.CustomListViewAdminChatBinding;
import com.example.g8shopadmin.databinding.ItemContainerReceivedImageMessageBinding;
import com.example.g8shopadmin.databinding.ItemContainerReceivedMessageBinding;
import com.example.g8shopadmin.databinding.ItemContainerSentImageMessageBinding;
import com.example.g8shopadmin.databinding.ItemContainerSentMessageBinding;
import com.example.g8shopadmin.models.ChatMessage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ChatMessage> chatMessages;
    private String receiverProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;
    public static final int VIEW_TYPE_SENT_IMG = 3;
    public static final int VIEW_TYPE_RECEIVED_IMG = 4;


    public void setReceiverProfileImage(String image){
        receiverProfileImage = image;
    }

    public ChatAdapter(List<ChatMessage> chatMessages, String receiverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        } else if(viewType == VIEW_TYPE_RECEIVED) {
            return new ReceiverMessageViewHolder(
                    ItemContainerReceivedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        } else if(viewType == VIEW_TYPE_SENT_IMG) {
            return new SentMessageImageViewHolder(
                    ItemContainerSentImageMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        } else {
            return new ReceiverMessageImageViewHolder(
                    ItemContainerReceivedImageMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
        }else if(getItemViewType(position) == VIEW_TYPE_RECEIVED) {
            ((ReceiverMessageViewHolder) holder).setData(chatMessages.get(position), receiverProfileImage);
        } else if(getItemViewType(position) == VIEW_TYPE_SENT_IMG){
            ((SentMessageImageViewHolder) holder).setData(chatMessages.get(position));
        } else {
            ((ReceiverMessageImageViewHolder) holder).setData(chatMessages.get(position), receiverProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).senderId.equals(senderId)) {
            if(chatMessages.get(position).messageImage != null){
                return VIEW_TYPE_SENT_IMG;
            }
            return VIEW_TYPE_SENT;
        } else {
            if(chatMessages.get(position).messageImage !=  null){
                return VIEW_TYPE_RECEIVED_IMG;
            }
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding) {
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;
        }

        void setData(ChatMessage chatMessage) {
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }

    static class SentMessageImageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerSentImageMessageBinding binding;

        SentMessageImageViewHolder(ItemContainerSentImageMessageBinding itemContainerSentImageMessageBinding) {
            super(itemContainerSentImageMessageBinding.getRoot());
            binding = itemContainerSentImageMessageBinding;
        }

        void setData(ChatMessage chatMessage) {
            byte[] bytes = Base64.decode(chatMessage.messageImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            binding.imageMessage.setImageBitmap(bitmap);
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }

    static class ReceiverMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerReceivedMessageBinding binding;

        ReceiverMessageViewHolder(ItemContainerReceivedMessageBinding itemContainerReceivedMessageBinding) {
            super(itemContainerReceivedMessageBinding.getRoot());
            binding = itemContainerReceivedMessageBinding;
        }

        void setData(ChatMessage chatMessage, String receiverProfileImage) {
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            //binding.imageProfile.setImageBitmap(receiverProfileImage);
            //setUserImage(receiverProfileImage, binding);
            if(receiverProfileImage != null){
                loadImage(receiverProfileImage, binding);
            }
        }

        private void loadImage(String image, ItemContainerReceivedMessageBinding binding) {
            try {
                Picasso.with(itemView.getContext()).load(image).into(binding.imageProfile);
            } catch (Exception error) {
                Log.e("ERROR", "activity_profile loadImage: ", error);
            }
        }
    }

    static class ReceiverMessageImageViewHolder extends RecyclerView.ViewHolder {
        private final ItemContainerReceivedImageMessageBinding binding;

        ReceiverMessageImageViewHolder(ItemContainerReceivedImageMessageBinding itemContainerReceivedImageMessageBinding) {
            super(itemContainerReceivedImageMessageBinding.getRoot());
            binding = itemContainerReceivedImageMessageBinding;
        }

        void setData(ChatMessage chatMessage, String receiverProfileImage) {
            byte[] bytes = Base64.decode(chatMessage.messageImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            binding.imageMessage.setImageBitmap(bitmap);
            binding.textDateTime.setText(chatMessage.dateTime);
            //binding.imageProfile.setImageBitmap(receiverProfileImage);
            //setUserImage(receiverProfileImage, binding);
            if(receiverProfileImage != null){
                loadImage(receiverProfileImage, binding);
            }
        }

        private void loadImage(String image, ItemContainerReceivedImageMessageBinding binding) {
            try {
                Picasso.with(itemView.getContext()).load(image).into(binding.imageProfile);
            } catch (Exception error) {
                Log.e("ERROR", "activity_profile loadImage: ", error);
            }
        }
    }


}
