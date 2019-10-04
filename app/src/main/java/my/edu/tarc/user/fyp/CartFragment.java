package my.edu.tarc.user.fyp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import my.edu.tarc.user.fyp.Model.Cart;
import my.edu.tarc.user.fyp.ViewHolder.CartViewHolder;



public class CartFragment extends Fragment{

    private DatabaseReference cartRef;
    FirebaseRecyclerOptions<Cart> options;
    FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter;

    private RecyclerView cartList;
    RecyclerView.LayoutManager layoutManager;

    private double tempSubtotal = 0;
    private TextView subtotal;

    private Button btn_checkout;

    public CartFragment(){
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        subtotal = view.findViewById(R.id.subtotal);
        btn_checkout = view.findViewById(R.id.checkout);

        cartList = view.findViewById(R.id.cart_list);
        cartList.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cartList.setLayoutManager(layoutManager);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PaymentReviewActivity.class);
                intent.putExtra("subtotal", tempSubtotal);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child("U1001")
                .child("Cart");

        options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartRef, Cart.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.productName.setText(cart.getProductName());
                cartViewHolder.productQuantity.setText(cart.getProductQuantity());
                cartViewHolder.productPrice.setText(getString(R.string.currency, Double.parseDouble(cart.getItemsPrice())));
                tempSubtotal+= Double.parseDouble(cart.getItemsPrice());
                subtotal.setText(getString(R.string.currency, tempSubtotal));
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);

                return new CartViewHolder(view);
            }
        };
        adapter.startListening();
        cartList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
