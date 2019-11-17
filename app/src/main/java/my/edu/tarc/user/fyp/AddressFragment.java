package my.edu.tarc.user.fyp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import my.edu.tarc.user.fyp.ViewHolder.AddressViewHolder;


public class AddressFragment extends Fragment {

    private String userNode;
    private String addressNode,sid,usertype,staffNode;

    private FirebaseRecyclerOptions<Address> options;
    private FirebaseRecyclerAdapter<Address, AddressViewHolder> adapter;
    private FirebaseAuth auth;
    private FirebaseUser user;
    DatabaseReference addressRef;
    private RecyclerView address_list;
    private Button btnSetAddress;

    public AddressFragment() {
        // Required empty public constructor
        staffNode = "Staff";
        addressNode = "Address";
        userNode = "Users";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        address_list = view.findViewById(R.id.addressRecycleView);
        btnSetAddress = view.findViewById(R.id.btnAddAddress);
        usertype = getArguments().getString("user");
        sid = getArguments().getString("staffId");
        address_list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        address_list.setLayoutManager(layoutManager);
        loadAddress();

        btnSetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SetAddressActivity.class);
                intent.putExtra("user",usertype);
                intent.putExtra("staffId",sid);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadAddress() {

        if(usertype.equals("Customer")) {
            addressRef = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child(userNode)
                    .child(user.getUid())
                    .child(addressNode);
        }
        if(usertype.equals("Staff")) {
            addressRef = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child(staffNode)
                    .child(sid)
                    .child(addressNode);
        }

        options = new FirebaseRecyclerOptions.Builder<Address>()
                .setQuery(addressRef, Address.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Address, AddressViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AddressViewHolder addressViewHolder, int i, @NonNull final Address address) {
                addressViewHolder.receiverAddress.setText(address.getAddress() + " " +address.getPostalCode() + " "+ address.getArea() + " "+ address.getState());

                addressViewHolder.phoneNum.setText(address.getPhoneNo());

                addressViewHolder.receiverName.setText(address.getReceiverName());
                addressViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });


            }

            @NonNull
            @Override
            public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_listing, parent, false);
                return new AddressViewHolder(view);
            }
        };
        adapter.startListening();
        address_list.setAdapter(adapter);}

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
