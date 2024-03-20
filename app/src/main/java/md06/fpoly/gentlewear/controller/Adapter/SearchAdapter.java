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

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.Next_interface;
import md06.fpoly.gentlewear.models.Products;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private ArrayList<Products> searchList;
    private Next_interface anInterface;

    // Constructor và các phương thức khác của Adapter

//    public void updateData(List<Products> newData) {
//        searchList.clear();
//        productList.addAll(newData);
//        notifyDataSetChanged();
//    }

    public void updateData(ArrayList<Products> searchList) {
        this.searchList = searchList;
        notifyDataSetChanged();
    }

    public SearchAdapter(Context context, Next_interface anInterface) {
        this.context = context;
        this.anInterface = anInterface;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Products model = searchList.get(position);
        holder.tv_name.setText(model.getProductName());
        holder.tv_price.setText(String.valueOf(model.getPrice()));
        Glide.with(context).load(model.getImage()).apply(RequestOptions.centerCropTransform()).into(holder.imgavatar);
        holder.itemView.setOnClickListener(view -> {
            anInterface.onNextPage(model);
        });

    }

    @Override
    public int getItemCount() {
        if (searchList != null) {
            return searchList.size();
        }
        return 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price;
        private ImageView imgavatar, img_like;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name_product);
            tv_price = itemView.findViewById(R.id.gia_product);
            imgavatar = itemView.findViewById(R.id.img_product);
            img_like = itemView.findViewById(R.id.img_like);
        }
    }

}
