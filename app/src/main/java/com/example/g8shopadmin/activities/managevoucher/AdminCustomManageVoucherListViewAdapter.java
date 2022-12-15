package com.example.g8shopadmin.activities.managevoucher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_create_voucher;
import com.example.g8shopadmin.models.Voucher;
import com.example.g8shopadmin.utilities.Constants;
import com.example.g8shopadmin.utilities.PreferenceManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminCustomManageVoucherListViewAdapter extends ArrayAdapter<Voucher> {
    ArrayList<Voucher> vouchers = new ArrayList<Voucher>();
    Context curContext;

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference vouchersRef = db.collection("vouchers");

    public AdminCustomManageVoucherListViewAdapter(Context context, int resource, ArrayList<Voucher> objects) {
        super(context, resource, objects);
        this.vouchers = objects;
        this.curContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.admin_custom_listview_manage_voucher, null);

        ImageView image = (ImageView) v.findViewById(R.id.logo_ma_giam_gia);
        TextView time = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_title);
        TextView cost_sale = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_cost_sale);
        TextView min_cost = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_min_cost);
        TextView text_da_su_dung = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_da_su_dung);
        TextView text_so_luong = (TextView) v.findViewById(R.id.admin_custom_listview_manage_voucher_so_luong);
        Button btn1 = (Button) v.findViewById(R.id.admin_custom_listview_manage_voucher_btn_01);
        Button admin_custom_listview_manage_voucher_button_xoa = (Button) v.findViewById(R.id.admin_custom_listview_manage_voucher_button_xoa);
        Button admin_custom_listview_manage_voucher_btn_01 = (Button) v.findViewById(R.id.admin_custom_listview_manage_voucher_btn_01);

        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        time.setText("Từ " + formatDate.format(vouchers.get(position).getStartedAt()) + " đến " + formatDate.format(vouchers.get(position).getFinishedAt()));
        cost_sale.setText("Số tiền giảm: đ" + vouchers.get(position).getMoneyDeals().toString());
        min_cost.setText("Đơn tối thiểu: đ" + vouchers.get(position).getMinimumCost().toString());
        text_da_su_dung.setText("Đã sử dung: " + vouchers.get(position).getAmoutOfUsed().toString());
        text_so_luong.setText("Còn lại: " + vouchers.get(position).getAmount().toString());
        Picasso.with(curContext).load(vouchers.get(position).getImage()).into(image);

        admin_custom_listview_manage_voucher_button_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(curContext)
                        .setMessage("Bạn có chức muốn xóa voucher này chứ?")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                vouchersRef.document(vouchers.get(position).getIdDoc()).delete();
                                Toast.makeText(curContext, "Đã xóa voucher thành công!", Toast.LENGTH_LONG).show();

                                vouchers.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });

        admin_custom_listview_manage_voucher_btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(curContext, activity_admin_create_voucher.class);
                moveActivity.putExtra(Constants.IDDOC_VOUCHER_TO_CREATE_VOUCHER, vouchers.get(position).getIdDoc());
                curContext.startActivity(moveActivity);
            }
        });

        return v;
    }
}

