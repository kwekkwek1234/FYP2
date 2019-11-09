package my.edu.tarc.user.fyp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.edu.tarc.user.fyp.R;


public class AddressViewHolder extends RecyclerView.ViewHolder {

    public TextView receiverName, phoneNum, receiverAddress;

    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);

        receiverName = itemView.findViewById(R.id.rName);
        phoneNum = itemView.findViewById(R.id.rPhoneNum);
        receiverAddress = itemView.findViewById(R.id.rAddress);
    }
}
