package my.edu.tarc.user.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import my.edu.tarc.user.fyp.Model.Cart;
import my.edu.tarc.user.fyp.Model.Product;

public class DisplayProductDetails extends AppCompatActivity {

    private ImageView productImageUrl;
    private TextView productName, productPrice;
    private String productId = "";
    private Button btnAddCart;
    private static int defaultAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product_details);

        productId = getIntent().getStringExtra("productId");

        productImageUrl = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        btnAddCart = findViewById(R.id.btnAddCart);

        getProductDetails(productId);

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartList();
            }
        });
    }

    private void addToCartList() {
        DatabaseReference tempCartRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child("U1001")
                .child("Cart");
        tempCartRef.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    defaultAmount = 1;
                    DatabaseReference cartRef = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Users");

                    final HashMap<String, Object> cartMap = new HashMap<>();
                    cartMap.put("productId", productId);
                    cartMap.put("productName", productName.getText());
                    cartMap.put("productPrice", productPrice.getText());
                    cartMap.put("productAmount", defaultAmount);

                    cartRef.child("U1001").child("Cart").child(productId).updateChildren(cartMap);
                }
                else{
                    Cart tempCart = dataSnapshot.getValue(Cart.class);
                    defaultAmount = Objects.requireNonNull(tempCart).getProductAmount();
                    DatabaseReference cartRef = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Users");
                    defaultAmount++;
                    final HashMap<String, Object> cartMap = new HashMap<>();
                    cartMap.put("productId", productId);
                    cartMap.put("productName", productName.getText());
                    cartMap.put("productPrice", productPrice.getText());
                    cartMap.put("productAmount", defaultAmount);

                    cartRef.child("U1001").child("Cart").child(productId).updateChildren(cartMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*String storeDate, storeTime;

        Calendar date = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        storeDate = dateFormat.format(date.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");
        storeDate = timeFormat.format(date.getTime());*/


    }

    private void getProductDetails(String tempId) {
        DatabaseReference productRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Products");

        productRef.child(tempId).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    productName.setText("NO");
                }
                else{
                    Product product = dataSnapshot.getValue(Product.class);

                    productName.setText(Objects.requireNonNull(product).getProductName());
                    productPrice.setText(product.getProductPrice());
                    Picasso.get().load(product.getProductImageUrl()).into(productImageUrl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
