package my.edu.tarc.user.fyp;


import android.R.layout;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class ProfileFragment extends Fragment implements  View.OnClickListener{
    private TextView name;
    private Button btnSetUP, btnEWallet, btnTrackShipping, btnSetAddress,btnSignout;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    FirebaseUser user;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnSetUP = (Button) view.findViewById(R.id.btnSetUp);
        btnEWallet = (Button) view.findViewById(R.id.btnEWallet);
        btnTrackShipping = (Button) view.findViewById(R.id.btnTrackShipping);
        btnSetAddress = (Button) view.findViewById(R.id.btnSetAddress);
        btnSignout = (Button) view.findViewById(R.id.btnSignOut);
        name = (TextView) view.findViewById(R.id.Name);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

         user = firebaseAuth.getCurrentUser();

        displayName();

        if(firebaseAuth.getCurrentUser() == null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoginFragment loginFragment = new LoginFragment();
            fragmentTransaction.replace(R.id.fragment_container,loginFragment);
            fragmentTransaction.commit();

        }




        btnSetUP.setOnClickListener(this);
        btnEWallet.setOnClickListener(this);
        btnTrackShipping.setOnClickListener(this);
        btnSetAddress.setOnClickListener(this);
        btnSignout.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    private void displayName() {
        ref = firebaseDatabase.getReference("Users").child(user.getUid()).child("Details");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User cuser = dataSnapshot.getValue(User.class);
                name.setText(cuser.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}


    @Override
    public void onClick(View v) {
        if(v == btnSetUP){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            UserPreferenceFragment userPreferenceFragment = new UserPreferenceFragment();
            fragmentTransaction.replace(R.id.fragment_container,userPreferenceFragment);
            fragmentTransaction.commit();
        }
        if(v == btnEWallet){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            eWalletFragment eWalletFragment = new eWalletFragment();
            fragmentTransaction.replace(R.id.fragment_container,eWalletFragment);
            fragmentTransaction.commit();
        }
        if(v == btnSetAddress){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AddressFragment addressFragment = new AddressFragment();
            fragmentTransaction.replace(R.id.fragment_container,addressFragment);
            fragmentTransaction.commit();
        }
        if(v == btnTrackShipping){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DeliveryServiceFragment deliveryServiceFragment = new DeliveryServiceFragment();
            fragmentTransaction.replace(R.id.fragment_container,deliveryServiceFragment);
            fragmentTransaction.commit();
        }
        if(v == btnSignout){
            FirebaseAuth.getInstance().signOut();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoginFragment loginFragment = new LoginFragment();
            fragmentTransaction.replace(R.id.fragment_container,loginFragment);
            fragmentTransaction.commit();
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
