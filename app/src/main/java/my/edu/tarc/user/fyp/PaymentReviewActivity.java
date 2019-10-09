package my.edu.tarc.user.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_review);

        double subtotal = getIntent().getDoubleExtra("subtotal", 0.00);
        TextView txtSubtotal = findViewById(R.id.subtotal);
        txtSubtotal.setText(getString(R.string.currency, subtotal));

        Button proceed_order = findViewById(R.id.proceed_order);
        proceed_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToOrder();
            }
        });
    }

    private void addToOrder(){
        final DatabaseReference cartRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child("U1001")
                .child("Cart");

        final DatabaseReference orderRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child("U1001")
                .child("Order");

        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderRef.child("D1001").setValue(dataSnapshot.getValue());
                cartRef.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
