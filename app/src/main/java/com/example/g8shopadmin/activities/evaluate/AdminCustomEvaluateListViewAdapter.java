package com.example.g8shopadmin.activities.evaluate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.Handle;
import com.example.g8shopadmin.activities.activity_admin_create_voucher;
import com.example.g8shopadmin.activities.activity_admin_evaluate;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdminCustomEvaluateListViewAdapter extends ArrayAdapter<AdminEvaluate> {

    Context curContext;
    ArrayList<AdminEvaluate> comments = new ArrayList<>();

    public AdminCustomEvaluateListViewAdapter(Context context, int resource, ArrayList<AdminEvaluate> comments) {
        super(context, resource, comments);
        this.comments = comments;
        this.curContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.admin_custom_listview_evaluate, null);

        TextView username = (TextView) v.findViewById(R.id.username);
        CircleImageView avatar = (CircleImageView) v.findViewById(R.id.avatar);
        ExpandableTextView text_evaluate = (ExpandableTextView) v.findViewById(R.id.text_evaluate);
        ImageView img_product = (ImageView) v.findViewById(R.id.img_product);
        TextView name_product = (TextView) v.findViewById(R.id.name_product);
        TextView size_color_product = (TextView) v.findViewById(R.id.size_color_product);
        TextView date_time = (TextView) v.findViewById(R.id.date_time);
        RelativeLayout reply_evaluate = (RelativeLayout) v.findViewById(R.id.reply_evaluate);
        TextView text_reply_evaluate = (TextView) v.findViewById(R.id.text_reply_evaluate);
        Button btn_phan_hoi = (Button) v.findViewById(R.id.btn_phan_hoi);


        // format ngày tháng năm
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Picasso.with(getContext()).load(comments.get(position).getImgProduct()).into(img_product);
        Picasso.with(getContext()).load(comments.get(position).getAvatar()).into(avatar);
        name_product.setText(comments.get(position).getNameProduct());
        username.setText(comments.get(position).getUser());
        size_color_product.setText("Size: " + comments.get(position).getSizeProduct() + ", Màu sắc: " + comments.get(position).getColorProduct());
        text_evaluate.setText(comments.get(position).getContent());
        date_time.setText(simpleDateFormat.format(comments.get(position).getCreatedAt()));

        if (comments.get(position).getReply() == "") {
            reply_evaluate.setVisibility(View.GONE);
            btn_phan_hoi.setVisibility(View.VISIBLE);
        } else {
            text_reply_evaluate.setText(comments.get(position).getReply());
            btn_phan_hoi.setVisibility(View.GONE);
        }

        Integer count_star = comments.get(position).getCountStar();

        View star1, star2, star3, star4, star5;
        star1 = (View) v.findViewById(R.id.star1);
        star2 = (View) v.findViewById(R.id.star2);
        star3 = (View) v.findViewById(R.id.star3);
        star4 = (View) v.findViewById(R.id.star4);
        star5 = (View) v.findViewById(R.id.star5);

        Handle.setStar(star1, star2, star3, star4, star5, count_star);

        btn_phan_hoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFormEvaluate(position, btn_phan_hoi);
            }
        });

        return v;

    }

    void showFormEvaluate(Integer position, Button btn_phan_hoi) {
        Dialog dialog = new Dialog(curContext);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.form_reply_evaluate);

        EditText text_evaluate = dialog.findViewById(R.id.text_evaluate);
        Button btn_confirm = dialog.findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Comment newComment = new Comment(orders.get(position).getId(), username, orders.get(position).getColor(), orders.get(position).getSize(), text_evaluate.getText().toString(), new Date(), count_star, "");
                //commentsRef.add(newComment);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
