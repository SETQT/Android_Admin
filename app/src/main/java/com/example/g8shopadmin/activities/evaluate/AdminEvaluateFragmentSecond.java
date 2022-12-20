package com.example.g8shopadmin.activities.evaluate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.FragmentCallbacks;
import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_evaluate;
import com.example.g8shopadmin.activities.myproducts.Product;
import com.example.g8shopadmin.models.AdminEvaluate;
import com.example.g8shopadmin.models.Comment;
import com.example.g8shopadmin.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminEvaluateFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_evaluate main;
    ListView admin_evaluate_listview;

    ArrayList<AdminEvaluate> listComments = new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference commentsRef = db.collection("comments");
    CollectionReference productsRef = db.collection("products");
    CollectionReference usersRef = db.collection("users");

    public static AdminEvaluateFragmentSecond newInstance(String strArg1) {
        AdminEvaluateFragmentSecond fragment = new AdminEvaluateFragmentSecond();
        Bundle bundle = new Bundle();
        bundle.putString("arg1", strArg1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException("Activity must implement MainCallbacks");
        }
        main = (activity_admin_evaluate) getActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_evaluate_fragment_second, null);

        admin_evaluate_listview = (ListView) layout_second.findViewById(R.id.admin_evaluate_listview);

        AdminEvaluateFragmentSecond.comment_asynctask mv_at = new AdminEvaluateFragmentSecond.comment_asynctask("0");
        mv_at.execute();

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        AdminEvaluateFragmentSecond.comment_asynctask mv_at = new AdminEvaluateFragmentSecond.comment_asynctask(strValue);
        mv_at.execute();
    }

    private class comment_asynctask extends AsyncTask<Void, AdminEvaluate, AdminEvaluate> {
        String option;

        comment_asynctask(String option) {
            this.option = option;
        }

        String idDoc = "";

        AdminEvaluate commentEvaluate = new AdminEvaluate();

        @Override
        protected AdminEvaluate doInBackground(Void... voids) {
            try {
                listComments.clear();

                switch (option) {
                    case "0":
                        commentsRef
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Boolean isHave = false;

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Comment comment = document.toObject(Comment.class);
                                                comment.setIdDoc(document.getId());
                                                isHave = true;
                                                idDoc = comment.getIdProduct();

                                                productsRef.document(idDoc).get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    DocumentSnapshot document2 = task.getResult();
                                                                    Product product = document2.toObject(Product.class);

                                                                    usersRef
                                                                            .whereEqualTo("username", comment.getUser())
                                                                            .get()
                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                    for (QueryDocumentSnapshot document3 : task.getResult()) {

                                                                                        User user = document3.toObject(User.class);
                                                                                        commentEvaluate = merge(comment, product, user);
                                                                                        publishProgress(commentEvaluate);

                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }

                                            if (!isHave) {
                                                publishProgress();
                                            }
                                        } else {
                                            Log.d("TAG", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case "1":
                        commentsRef
                                .whereEqualTo("reply", "")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Boolean isHave = false;

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Comment comment = document.toObject(Comment.class);
                                                comment.setIdDoc(document.getId());
                                                isHave = true;
                                                idDoc = comment.getIdProduct();

                                                productsRef.document(idDoc).get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    DocumentSnapshot document2 = task.getResult();
                                                                    Product product = document2.toObject(Product.class);
                                                                    usersRef
                                                                            .whereEqualTo("username", comment.getUser())
                                                                            .get()
                                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                    for (QueryDocumentSnapshot document3 : task.getResult()) {

                                                                                        User user = document3.toObject(User.class);
                                                                                        commentEvaluate = merge(comment, product, user);
                                                                                        publishProgress(commentEvaluate);

                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });
                                            }

                                            if (!isHave) {
                                                publishProgress();
                                            }
                                        } else {
                                            Log.d("TAG", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    default:
                        break;
                }
            } catch (Exception error) {
                Log.e("ERROR", "activity_see_evaluate: " + error);
            }
            return null;
        }

        protected void onProgressUpdate(AdminEvaluate... comments) {
            super.onProgressUpdate(comments);

            if (comments.length == 0) {
                listComments.clear();
            } else {
                listComments.add(comments[0]);
            }
            try {
                AdminCustomEvaluateListViewAdapter myAdapter = new AdminCustomEvaluateListViewAdapter(getActivity(), R.layout.admin_custom_listview_evaluate, listComments, option);
                admin_evaluate_listview.setAdapter(myAdapter);
            } catch (Exception error) {
                Log.e("ERROR", "activity_see_evaluate: ", error);
                return;
            }
        }
    }

    public AdminEvaluate merge(Comment cmt, Product pro, User u) {
        AdminEvaluate commentEvaluate = new AdminEvaluate();

        commentEvaluate.setNameProduct(pro.getName());
        commentEvaluate.setImgProduct(pro.getImage());
        commentEvaluate.setColorProduct(cmt.getColorProduct());
        commentEvaluate.setContent(cmt.getContent());
        commentEvaluate.setCountStar(cmt.getCountStar());
        commentEvaluate.setCreatedAt(cmt.getCreatedAt());
        commentEvaluate.setIdProduct(cmt.getIdProduct());
        commentEvaluate.setSizeProduct(cmt.getSizeProduct());
        commentEvaluate.setReply(cmt.getReply());
        commentEvaluate.setUser(cmt.getUser());
        commentEvaluate.setIdDoc(cmt.getIdDoc());
        commentEvaluate.setAvatar(u.getImage());

        return commentEvaluate;
    }
}
