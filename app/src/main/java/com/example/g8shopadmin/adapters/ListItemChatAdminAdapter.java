package com.example.g8shopadmin.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.MyChatAdmin;
import com.example.g8shopadmin.databinding.CustomListViewAdminChatBinding;
import com.example.g8shopadmin.listeners.UserListener;
import com.example.g8shopadmin.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListItemChatAdminAdapter extends RecyclerView.Adapter<ListItemChatAdminAdapter.UserViewHolder> {

    private final List<User> users;
    private final UserListener userListener;

    public ListItemChatAdminAdapter(List<User> users, UserListener userListener)
    {
        this.users = users;
        this.userListener = userListener;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CustomListViewAdminChatBinding customListViewAdminChatBinding = CustomListViewAdminChatBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(customListViewAdminChatBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        CustomListViewAdminChatBinding binding;

        UserViewHolder(CustomListViewAdminChatBinding customListViewAdminChatBinding) {
            super(customListViewAdminChatBinding.getRoot());
            binding = customListViewAdminChatBinding;
        }

        void setUserData(User user) {
            binding.textName.setText(user.fullName);
            binding.textLastMessage.setText(user.phone);
            setUserImage(user.image,binding);
            //binding.imageProfile.setImageBitmap(getUserImage(user.image));
            binding.getRoot().setOnClickListener(v -> userListener.onUserClicked(user));
        }
    }

    private void setUserImage(String name, CustomListViewAdminChatBinding binding) {
        //name += "avatar";
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");
        StorageReference islandRef = storageRef.child("ProfileUser/" + name);
        try {
            File localFile = File.createTempFile("tempfile", ".jpg");
            //final Bitmap[] bitmap = new Bitmap[1];
            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //bitmap[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    binding.imageProfile.setImageBitmap(bitmap);
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("down", "onFailure: ");
                }
            });
            //return bitmap[0];

        } catch (IOException e) {
            Log.e("error", "downloadFile error ");
            //return null;
        }
    }


}
