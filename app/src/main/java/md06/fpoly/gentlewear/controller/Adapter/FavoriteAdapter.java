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
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.models.Products;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Products> marrayList;
    private Next_interface anInterface;

    public FavoriteAdapter(Context context, Next_interface anInterface) {
        this.context = context;
        this.anInterface = anInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favorite, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products model = marrayList.get(position);
        holder.tv_name.setText(model.getProductName());
        holder.tv_price.setText(String.valueOf(model.getPrice()));
        String url = APIClass.URL + "uploads" +model.getSize().get(0).getColor().get(0).getImage();
        Glide.with(context).load(url).apply(RequestOptions.centerCropTransform()).into(holder.imgavatar);
        holder.itemView.setOnClickListener(view -> {
            anInterface.onNextPage(model);
        });
        holder.img_delete.setOnClickListener(v -> {
            anInterface.onClickItem(model.get_id());
        });
    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price;
        private ImageView imgavatar, img_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_namesp);
            tv_price = itemView.findViewById(R.id.tv_price);
            imgavatar = itemView.findViewById(R.id.image_sp);
            img_delete = itemView.findViewById(R.id.img_delete_favorite);
        }
    }
}
