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
import md06.fpoly.gentlewear.models.Products;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    Context context;
    List<Products> listfv;

    public FavoriteAdapter(Context context, List<Products> listfv) {
        this.context = context;
        this.listfv = listfv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_favorite, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listfv.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
