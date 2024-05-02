package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.models.Favorites;
import md06.fpoly.gentlewear.models.Products;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    private List<Favorites> marrayList;

    public FavoriteAdapter(Context context, List<Favorites> favoriteProducts) {
        this.context = context;
        this.marrayList = favoriteProducts;
    }



    public void setData(List<Favorites> arrayList) {
        this.marrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorites model = marrayList.get(position);
        holder.tv_name.setText(model.getProducts().get(0).getProductName());
        holder.tv_price.setText(model.getProducts().get(0).getPrice()+ " đ");

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
        }
    }
}
