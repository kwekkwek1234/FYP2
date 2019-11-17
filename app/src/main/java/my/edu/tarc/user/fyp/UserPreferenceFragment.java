package my.edu.tarc.user.fyp;

import android.content.Context;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserPreferenceFragment extends Fragment implements View.OnClickListener {

    private RadioGroup radioCat;
    private Button btnSave;
    private RadioButton cat;
    private String up;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref,ref2;
    String usertype,sid;


    public UserPreferenceFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_preference, container, false);
        radioCat = view.findViewById(R.id.radioCat);
        btnSave = view.findViewById(R.id.save);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        btnSave.setOnClickListener(this);

        usertype = getArguments().getString("user");
        sid = getArguments().getString("staffId");

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnSave){
            int id = radioCat.getCheckedRadioButtonId();
            cat = radioCat.findViewById(id);
            up = cat.getText().toString();

           if(usertype.equals("Customer")){
            ref = database.getReference("Users").child(user.getUid()).child("Preferences");}
           if(usertype.equals("Staff")){
               ref = database.getReference("Staff").child(sid).child("Preferences");}

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ref.setValue(up);
                    Toast.makeText(getActivity(),"User Preference Set",Toast.LENGTH_LONG).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("user",usertype);
                    ProfileFragment profileFragment = new ProfileFragment();
                    profileFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container,profileFragment);
                    fragmentTransaction.commit();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
}
