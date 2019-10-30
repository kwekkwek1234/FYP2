package my.edu.tarc.user.fyp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class eWalletFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private TextView textViewAmount;
    private Button btnTopup, btnWithdraw;

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
        textViewAmount = (TextView) view.findViewById(R.id.textViewAmount);
        btnTopup = (Button) view.findViewById(R.id.buttonTopUp);
        btnWithdraw = (Button) view.findViewById(R.id.buttonWithdraw);

        btnTopup.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnTopup){
            startActivity(new Intent(getActivity(),TopupActivity.class));
        }
        if(v == btnWithdraw){
            //startActivity(new Intent(getActivity(),WithdrawActivity.class));
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
