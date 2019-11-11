package my.edu.tarc.user.fyp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class eWalletFragment extends Fragment implements View.OnClickListener {

    DatabaseReference transRef,ref;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    private TextView textViewAmount;
    private Button btnTopup, btnWithdraw;

    public eWalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_e_wallet, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();
        textViewAmount = (TextView) view.findViewById(R.id.textViewAmount);
        getWalletBalance();

        btnTopup = (Button) view.findViewById(R.id.buttonTopUp);
        btnWithdraw = (Button) view.findViewById(R.id.buttonWithdraw);

        btnTopup.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);



        // Inflate the layout for this fragment
        return view;
    }

    private void getWalletBalance() {
        transRef = database.getReference("Users").child(user.getUid()).child("Transaction");
        final Query balanceQ = transRef.orderByKey().limitToLast(1);

        balanceQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   String transId = dataSnapshot.getChildren().iterator().next().getKey();
                   ref = transRef.child(transId);
                   ref.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           Transaction tran =  dataSnapshot.getValue(Transaction.class);
                           textViewAmount.setText("RM "+ tran.getBalance() + ".00");
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });

                }else{
                    textViewAmount.setText("RM 0.00");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == btnTopup){
            startActivity(new Intent(getActivity(),TopupActivity.class));
        }
        if(v == btnWithdraw){
            startActivity(new Intent(getActivity(),WithdrawActivity.class));
        }
    }



}
