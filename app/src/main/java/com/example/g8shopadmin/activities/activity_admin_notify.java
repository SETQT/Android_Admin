package com.example.g8shopadmin.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.notification.AdminCustomNotifyListViewAdapter;
import com.example.g8shopadmin.models.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class activity_admin_notify extends Activity implements View.OnClickListener {
    // khai báo biến xử lý
    ArrayList<Notification> listNotify = new ArrayList<>();
    String username;

    // khai báo biến UI
    ListView listNotification;
    View icon_back;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference notifyRef = db.collection("notifications");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notify);

        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);

        listNotification = (ListView) findViewById(R.id.notify_listview);

        notify_asyntask n_at = new notify_asyntask();
        n_at.execute();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_back.getId()) {
            // chuyển sang giao diện dashboard
            Intent moveActivity = new Intent(getApplicationContext(), DashboardActivity.class);
            startActivity(moveActivity);
        }
    }

    private class notify_asyntask extends AsyncTask<Void, Notification, Notification> {
        notify_asyntask() {
        }

        @Override
        protected Notification doInBackground(Void... voids) {
            try {
                notifyRef
                        .whereEqualTo("receiver", "admin")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        Notification notification = document.toObject(Notification.class);
                                        notification.setIdDoc(document.getId());
                                        publishProgress(notification);
                                    }
                                } else {
                                }
                            }
                        });
            } catch (Exception error) {
                return null;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Notification... notifications) {
            super.onProgressUpdate(notifications);
            listNotify.add(notifications[0]);

            SortArrayList(listNotify);

            AdminCustomNotifyListViewAdapter myAdapter = new AdminCustomNotifyListViewAdapter(getApplicationContext(), R.layout.admin_custom_notify_listview, listNotify);
            listNotification.setAdapter(myAdapter);
        }
    }

    class sortCompare implements Comparator<Notification> {
        public int compare(Notification s1, Notification s2) {
            return s2.getDate().compareTo(s1.getDate());
        }
    }

    public void SortArrayList(ArrayList<Notification> notify){
        Collections.sort(notify, new sortCompare());
    }
}

