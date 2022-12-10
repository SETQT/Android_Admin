package com.example.g8shopadmin.activities.managevoucher;

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
import com.example.g8shopadmin.activities.activity_admin_manage_voucher;
import com.example.g8shopadmin.models.Voucher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class AdminManageVoucherFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_manage_voucher main;
    ListView listViewVoucher;
    ArrayList<AdminManageVoucher> Voucher = new ArrayList<AdminManageVoucher>();

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference vouchersRef = db.collection("vouchers");

    // biến xử lý
    ArrayList<Voucher> listVoucher = new ArrayList<>();

    ArrayList<Integer> image = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> cost_sale = new ArrayList<>();
    ArrayList<String> min_cost = new ArrayList<>();
    ArrayList<String> text_da_su_dung = new ArrayList<>();
    ArrayList<String> text_so_luong = new ArrayList<>();
    ArrayList<String> btn1 = new ArrayList<>();

    public static AdminManageVoucherFragmentSecond newInstance(String strArg1) {
        AdminManageVoucherFragmentSecond fragment = new AdminManageVoucherFragmentSecond();
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
        main = (activity_admin_manage_voucher) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_manage_voucher_fragment_second, null);

        listViewVoucher = (ListView) layout_second.findViewById(R.id.admin_manage_voucher_listview);

//        AdminCustomManageVoucherListViewAdapter myAdapter = new AdminCustomManageVoucherListViewAdapter(getActivity(), R.layout.admin_custom_listview_manage_voucher, Voucher);
//        listVoucher.setAdapter(myAdapter);

        manage_voucher_asynctask mv_at = new manage_voucher_asynctask("1");
        mv_at.execute();

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        manage_voucher_asynctask mv_at = new manage_voucher_asynctask(strValue);
        mv_at.execute();
    }

    private class manage_voucher_asynctask extends AsyncTask<Void, Voucher, Voucher> {
        Date curDate;
        String state;

        manage_voucher_asynctask(String state) {
            this.curDate = new Date();
            this.state = state;
        }

        @Override
        protected Voucher doInBackground(Void... voids) {
            try {
                vouchersRef
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                boolean isHave = false;

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Voucher voucher = document.toObject(Voucher.class);

                                    isHave = true;

                                    switch (state) {
                                        case "1":
                                            if (curDate.after(voucher.getStartedAt()) && curDate.before(voucher.getFinishedAt())) {
                                                publishProgress(voucher);
                                            } else {
                                                isHave = false;
                                            }
                                            break;
                                        case "2":
                                            if (curDate.before(voucher.getStartedAt())) {
                                                publishProgress(voucher);
                                            } else {
                                                isHave = false;
                                            }
                                            break;
                                        case "3":
                                            if (curDate.after(voucher.getFinishedAt())) {
                                                publishProgress(voucher);
                                            } else {
                                                isHave = false;
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }

                                if (!isHave) {
                                    onProgressUpdate();
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "AdminMangerVoucherFragmentSecond doInBackground: ", error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Voucher... vouchers) {
            super.onProgressUpdate(vouchers);

            if (vouchers.length == 0) {
                listVoucher.clear();
            } else {
                listVoucher.add(vouchers[0]);
            }

            AdminCustomManageVoucherListViewAdapter myAdapter = new AdminCustomManageVoucherListViewAdapter(getActivity(), R.layout.admin_custom_listview_manage_voucher, listVoucher);
            listViewVoucher.setAdapter(myAdapter);
        }
    }

}
