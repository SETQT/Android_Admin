package com.example.g8shopadmin.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.g8shopadmin.FragmentCallbacks;
import com.example.g8shopadmin.MainCallbacks;
import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.activity_admin_order;

import java.util.ArrayList;

public class AdminOrderFragmentSecond extends Fragment implements FragmentCallbacks {
    activity_admin_order main;
    ListView listOrder;
    ArrayList<AdminOrder> orders = new ArrayList<AdminOrder>();

    ArrayList<Integer> avatar = new ArrayList<>();
    ArrayList<String> name_customer = new ArrayList<>();
    ArrayList<String> name_option = new ArrayList<>();
    ArrayList<Integer> picture = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> size = new ArrayList<>();
    ArrayList<String> count = new ArrayList<>();
    ArrayList<String> old_cost = new ArrayList<>();
    ArrayList<String> new_cost = new ArrayList<>();
    ArrayList<String> total = new ArrayList<>();
    ArrayList<String> button_option = new ArrayList<>();
    ArrayList<String> ma_don_hang = new ArrayList<>();

    public static AdminOrderFragmentSecond newInstance(String strArg1) {
        AdminOrderFragmentSecond fragment = new AdminOrderFragmentSecond();
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
        main = (activity_admin_order) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_second = (LinearLayout) inflater.inflate(R.layout.admin_custom_order_fragment_second, null);

        listOrder = (ListView) layout_second.findViewById(R.id.admin_order_listview);

        avatar.add(R.drawable.avatar_profile);avatar.add(R.drawable.avatar_profile);
        name_customer.add("duongminhhieu");name_customer.add("truongvanhao");
        name_option.add("Chờ xác nhận"); name_option.add("Chờ xác nhận");
        picture.add(R.drawable.mono1);picture.add(R.drawable.mono1);
        name.add("Áo khoác cực chất");name.add("Áo thun");
        size.add("Size L, Trắng");size.add("Size L, Đen");
        count.add("Số lượng: 2");count.add("Số lượng: 1");
        old_cost.add("đ400.000"); old_cost.add("đ300.000");
        new_cost.add("đ200.000"); new_cost.add("đ200.000");
        total.add("đ400.000"); total.add("đ200.000");
        button_option.add("Xác nhận");button_option.add("Xác nhận");
        ma_don_hang.add("#ADFGHJLL");ma_don_hang.add("#BGHTUYNH");


        for (int i = 0; i < picture.size(); i++) {
            orders.add(new AdminOrder(i, avatar.get(i), name_customer.get(i), name_option.get(i), picture.get(i), name.get(i), size.get(i), count.get(i), old_cost.get(i), new_cost.get(i), total.get(i), button_option.get(i), ma_don_hang.get(i)));
        }

        AdminCustomOrderListViewAdapter myAdapter = new AdminCustomOrderListViewAdapter(getActivity(), R.layout.admin_custom_listview_order, orders);
        listOrder.setAdapter(myAdapter);

        return layout_second;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {

        Log.i("TAG", "onMsgFromMainToFragment: " + strValue);

        if (strValue == "Cho xac nhan") {
            avatar.clear();name_customer.clear();name_option.clear();picture.clear();
            name.clear();size.clear();count.clear();old_cost.clear();new_cost.clear();
            total.clear();button_option.clear();

            avatar.add(R.drawable.avatar_profile);avatar.add(R.drawable.avatar_profile);
            name_customer.add("duongminhhieu");name_customer.add("truongvanhao");
            name_option.add("Chờ xác nhận"); name_option.add("Chờ xác nhận");
            picture.add(R.drawable.mono1);picture.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");name.add("Áo thun");
            size.add("Size L, Trắng");size.add("Size L, Đen");
            count.add("Số lượng: 2");count.add("Số lượng: 1");
            old_cost.add("đ400.000"); old_cost.add("đ300.000");
            new_cost.add("đ200.000"); new_cost.add("đ200.000");
            total.add("đ400.000"); total.add("đ200.000");
            button_option.add("Xác nhận");button_option.add("Xác nhận");
            ma_don_hang.add("#ADFGHJLL");ma_don_hang.add("#BGHTUYNH");

            orders.clear();

            for (int i = 0; i < picture.size(); i++) {
                orders.add(new AdminOrder(i, avatar.get(i), name_customer.get(i), name_option.get(i), picture.get(i), name.get(i), size.get(i), count.get(i), old_cost.get(i), new_cost.get(i), total.get(i), button_option.get(i), ma_don_hang.get(i)));
            }
        }

        if (strValue == "Dang giao") {
            avatar.clear();name_customer.clear();name_option.clear();picture.clear();
            name.clear();size.clear();count.clear();old_cost.clear();new_cost.clear();
            total.clear();button_option.clear();

            avatar.add(R.drawable.avatar_profile);avatar.add(R.drawable.avatar_profile);
            name_customer.add("duongminhhieu");name_customer.add("truongvanhao");
            name_option.add("Đang giao hàng"); name_option.add("Đang giao hàng");
            picture.add(R.drawable.mono1);picture.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");name.add("Áo thun");
            size.add("Size L, Trắng");size.add("Size L, Đen");
            count.add("Số lượng: 2");count.add("Số lượng: 1");
            old_cost.add("đ400.000"); old_cost.add("đ300.000");
            new_cost.add("đ200.000"); new_cost.add("đ200.000");
            total.add("đ400.000"); total.add("đ200.000");
            button_option.add("Tình trạng giao");button_option.add("Tình trạng giao");
            ma_don_hang.add("#HJKIUOLK");ma_don_hang.add("#GFRTGBGT");

            orders.clear();

            for (int i = 0; i < picture.size(); i++) {
                orders.add(new AdminOrder(i, avatar.get(i), name_customer.get(i), name_option.get(i), picture.get(i), name.get(i), size.get(i), count.get(i), old_cost.get(i), new_cost.get(i), total.get(i), button_option.get(i), ma_don_hang.get(i)));
            }
        }

        if (strValue == "Da giao") {
            avatar.clear();name_customer.clear();name_option.clear();picture.clear();
            name.clear();size.clear();count.clear();old_cost.clear();new_cost.clear();
            total.clear();button_option.clear();

            avatar.add(R.drawable.avatar_profile);avatar.add(R.drawable.avatar_profile);
            name_customer.add("duongminhhieu");name_customer.add("truongvanhao");
            name_option.add("Đã giao hàng"); name_option.add("Đã giao hàng");
            picture.add(R.drawable.mono1);picture.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");name.add("Áo thun");
            size.add("Size L, Trắng");size.add("Size L, Đen");
            count.add("Số lượng: 2");count.add("Số lượng: 1");
            old_cost.add("đ400.000"); old_cost.add("đ300.000");
            new_cost.add("đ200.000"); new_cost.add("đ200.000");
            total.add("đ400.000"); total.add("đ200.000");
            button_option.add("Xem đánh giá");button_option.add("Xem đánh giá");
            ma_don_hang.add("#GFGHTYHJ");ma_don_hang.add("#BXCVFGTT");

            avatar.add(R.drawable.avatar_profile);avatar.add(R.drawable.avatar_profile);
            name_customer.add("duongminhhieu");name_customer.add("truongvanhao");
            name_option.add("Đã giao hàng"); name_option.add("Đã giao hàng");
            picture.add(R.drawable.mono1);picture.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");name.add("Áo thun");
            size.add("Size L, Trắng");size.add("Size L, Đen");
            count.add("Số lượng: 2");count.add("Số lượng: 1");
            old_cost.add("đ400.000"); old_cost.add("đ300.000");
            new_cost.add("đ200.000"); new_cost.add("đ200.000");
            total.add("đ400.000"); total.add("đ200.000");
            button_option.add("Xem đánh giá");button_option.add("Xem đánh giá");
            ma_don_hang.add("#GTTGFDAS");ma_don_hang.add("#CDETRFRF");

            avatar.add(R.drawable.avatar_profile);avatar.add(R.drawable.avatar_profile);
            name_customer.add("duongminhhieu");name_customer.add("truongvanhao");
            name_option.add("Đã giao hàng"); name_option.add("Đã giao hàng");
            picture.add(R.drawable.mono1);picture.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");name.add("Áo thun");
            size.add("Size L, Trắng");size.add("Size L, Đen");
            count.add("Số lượng: 2");count.add("Số lượng: 1");
            old_cost.add("đ400.000"); old_cost.add("đ300.000");
            new_cost.add("đ200.000"); new_cost.add("đ200.000");
            total.add("đ400.000"); total.add("đ200.000");
            button_option.add("Xem đánh giá");button_option.add("Xem đánh giá");
            ma_don_hang.add("#NMKJHYGF");ma_don_hang.add("#CXSDFRFD");

            orders.clear();

            for (int i = 0; i < picture.size(); i++) {
                orders.add(new AdminOrder(i, avatar.get(i), name_customer.get(i), name_option.get(i), picture.get(i), name.get(i), size.get(i), count.get(i), old_cost.get(i), new_cost.get(i), total.get(i), button_option.get(i), ma_don_hang.get(i)));
            }
        }

        if (strValue == "Da huy") {
            avatar.clear();name_customer.clear();name_option.clear();picture.clear();
            name.clear();size.clear();count.clear();old_cost.clear();new_cost.clear();
            total.clear();button_option.clear();

            avatar.add(R.drawable.avatar_profile);
            name_customer.add("duongminhhieu");
            name_option.add("Đã hủy");
            picture.add(R.drawable.mono1);
            name.add("Áo khoác cực chất");
            size.add("Size L, Trắng");
            count.add("Số lượng: 2");
            old_cost.add("đ400.000");
            new_cost.add("đ200.000");
            total.add("đ400.000");
            button_option.add("Xem đơn hàng");
            ma_don_hang.add("#MKJTFGFD");

            orders.clear();

            for (int i = 0; i < picture.size(); i++) {
                orders.add(new AdminOrder(i, avatar.get(i), name_customer.get(i), name_option.get(i), picture.get(i), name.get(i), size.get(i), count.get(i), old_cost.get(i), new_cost.get(i), total.get(i), button_option.get(i), ma_don_hang.get(i)));
            }
        }

        AdminCustomOrderListViewAdapter myAdapter = new AdminCustomOrderListViewAdapter(getActivity(), R.layout.admin_custom_listview_order, orders);
        listOrder.setAdapter(myAdapter);

    }


}
