package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.MainActivity;
import md06.fpoly.gentlewear.apiServices.Next_interface;
import md06.fpoly.gentlewear.models.Products;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Products> marrayList;
    private Next_interface anInterface;

    public FavoriteAdapter(Context context, Next_interface anInterface) {
        this.context = context;
        this.anInterface = anInterface;
    }

    public void setData(ArrayList<Products> arrayList) {
        this.marrayList = arrayList;
        notifyDataSetChanged();
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
        Products model = marrayList.get(position);
        holder.tv_name.setText(model.getProductName());
        holder.tv_price.setText(String.valueOf(model.getPrice()));
//        Glide.with(context).load(model.getImage()).apply(RequestOptions.centerCropTransform()).into(holder.imgavatar);
        holder.itemView.setOnClickListener(view -> {
            anInterface.onNextPage(model);
        });
    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price;
        private ImageView imgavatar, img_like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name_product);
            tv_price = itemView.findViewById(R.id.gia_product);
            imgavatar = itemView.findViewById(R.id.img_product);
            img_like = itemView.findViewById(R.id.img_like);
        }
    }
}
