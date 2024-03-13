package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.MainActivity;
import md06.fpoly.gentlewear.models.Cart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    List<Cart> listcard;

    public CartAdapter(Context context, List<Cart> listcard) {
        this.context = context;
        this.listcard = listcard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_cart, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listcard.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
