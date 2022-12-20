package com.example.g8shopadmin.activities;


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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

        import com.example.g8shopadmin.R;
import com.example.g8shopadmin.activities.myproducts.Product;
import com.example.g8shopadmin.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

        import java.util.ArrayList;

        public class AdminCustomListCustomerAdapter extends ArrayAdapter<User>{

        
            ArrayList<User> customers = new ArrayList<User>();
    Context curContext;

            // firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference prodsRef = db.collection("products");
    public AdminCustomListCustomerAdapter(Context context, int resource, ArrayList<User> objects) {
                super(context, resource, objects);
                this.customers = objects;
                this.curContext=context;
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
                v = inflater.inflate(R.layout.custom_listview_list_customer, null);
        
                        TextView username = (TextView) v.findViewById(R.id.username_admin_list_customer);
                ImageView img = (ImageView) v.findViewById(R.id.custom_picture_admin_list_customer) ;
        
                        username.setText(customers.get(position).getUsername());
                Picasso.with(curContext).load(customers.get(position).getImage()).into(img);
        
                
                                RelativeLayout item_customer=(RelativeLayout)  v.findViewById(R.id.rectangle_custom_admin_list_customer);
                item_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                Intent mIntent = new Intent(getContext(), activity_admin_record_customer.class);
                //                Log.d("data", "onClick: "myProducts.get(position).getName().toString());
                                        mIntent.putExtra("username",customers.get(position).getUsername());
                                        mIntent.putExtra("idDoc",customers.get(position).getIdDoc());
                                        mIntent.putExtra("ban",customers.get(position).getStatus());
                                curContext.startActivity(mIntent);
                            }

                });
        
                        return v;
        
                    }
}

