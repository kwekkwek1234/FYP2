package my.edu.tarc.user.fyp.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import my.edu.tarc.user.fyp.Interface.ItemClickListener;
import my.edu.tarc.user.fyp.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView productName, productPrice, productQuantity;
    public ItemClickListener listener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.productName);
        productPrice = itemView.findViewById(R.id.productPrice);
        productQuantity = itemView.findViewById(R.id.productQuantity);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
