package com.example.g8shopadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.g8shopadmin.adapters.ListItemChatAdminAdapter;
import com.example.g8shopadmin.databinding.ActivityAdminListChatBinding;
import com.example.g8shopadmin.listeners.UserListener;
import com.example.g8shopadmin.models.UserChat;
import com.example.g8shopadmin.utilities.Constants;
import com.example.g8shopadmin.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_admin_list_chat extends AppCompatActivity implements UserListener {

    private ActivityAdminListChatBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminListChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
        getUsers();
    }

    private void setListeners(){
        binding.icBackAdminListChat.setOnClickListener(view -> onBackPressed());
    }

    private void getUsers() {
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    loading(false);
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<UserChat> users = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            UserChat user = new UserChat();
                            user.fullName = queryDocumentSnapshot.getString("fullname");
                            user.email = queryDocumentSnapshot.getString("email");
                            user.image = queryDocumentSnapshot.getString("image");
                            user.token = queryDocumentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.phone = queryDocumentSnapshot.getString("phone");
                            user.id = queryDocumentSnapshot.getId();
                            users.add(user);
                        }
                        if (users.size() > 0) {
                            ListItemChatAdminAdapter listItemChatAdminAdapter = new ListItemChatAdminAdapter(users, this, getApplicationContext());
                            binding.usersRecyclerView.setAdapter(listItemChatAdminAdapter);
                            binding.usersRecyclerView.setVisibility(View.VISIBLE);
                        } else {
                            showErrorMessage();
                        }
                    } else {
                        showErrorMessage();
                    }
                });
    }

    private void showErrorMessage() {
        binding.textErrorMessage.setText(String.format("%s", " Không có user nào"));
        binding.textErrorMessage.setVisibility(View.VISIBLE);
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onUserClicked(UserChat user){
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
        finish();
    }
}