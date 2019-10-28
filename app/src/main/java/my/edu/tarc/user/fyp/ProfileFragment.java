package my.edu.tarc.user.fyp;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ProfileFragment extends Fragment implements  View.OnClickListener{
    private TextView textViewName;
    private Button btnSetUP, btnEWallet, btnTrackShipping, btnSetAddress,btnSignout;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;


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
        textViewName = (TextView) view.findViewById(R.id.name);
        btnSetUP = (Button) view.findViewById(R.id.btnSetUp);
        btnEWallet = (Button) view.findViewById(R.id.btnEWallet);
        btnTrackShipping = (Button) view.findViewById(R.id.btnTrackShipping);
        btnSetAddress = (Button) view.findViewById(R.id.btnSetAddress);
        btnSignout = (Button) view.findViewById(R.id.btnSignOut);
        firebaseAuth = FirebaseAuth.getInstance();
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

    @Override
    public void onClick(View v) {
        if(v == btnSetUP){

        }
        if(v == btnEWallet){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            eWalletFragment eWalletFragment = new eWalletFragment();
            fragmentTransaction.replace(R.id.fragment_container,eWalletFragment);
            fragmentTransaction.commit();
        }
        if(v == btnSetAddress){

        }
        if(v == btnTrackShipping){

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
