package my.edu.tarc.user.fyp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class eWalletFragment extends Fragment implements View.OnClickListener {

    DatabaseReference transRef,ref;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    private TextView textViewAmount;
    private Button btnTopup, btnWithdraw;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView transList;
    String usertype,sid;
    public eWalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_e_wallet, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();
        textViewAmount = (TextView) view.findViewById(R.id.textViewAmount);
        transList = view.findViewById(R.id.transactionList);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        usertype = getArguments().getString("user");
        sid = getArguments().getString("staffId");

        if(usertype.equals("Customer")){
            transRef = database.getReference("Users").child(user.getUid()).child("Transaction");
        }

        if(usertype.equals("Staff")){
            transRef = database.getReference("Staff").child(sid).child("Transaction");
        }
        getWalletBalance();
      //  displayHistory();



        btnTopup = (Button) view.findViewById(R.id.buttonTopUp);
        btnWithdraw = (Button) view.findViewById(R.id.buttonWithdraw);

        btnTopup.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);



        // Inflate the layout for this fragment
        return view;
    }

//
//    private void displayHistory() {
//        transList.setAdapter(adapter);
//
//        ref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String value = dataSnapshot.getValue(Transaction.class).toString();
//                arrayList.add(value);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void getWalletBalance() {

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
            Intent intent = new Intent(getActivity(),TopupActivity.class);
            intent.putExtra("user",usertype);
            intent.putExtra("staffId",sid);
            startActivity(intent);

        }
        if(v == btnWithdraw){
            Intent intent = new Intent(getActivity(),TopupActivity.class);
            intent.putExtra("user",usertype);
            intent.putExtra("staffId",sid);
            startActivity(intent);
        }
    }



}
