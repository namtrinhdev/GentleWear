package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.Next_interface;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.models.Products;
import md06.fpoly.gentlewear.models.ResProduct;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ClotherViewHolder> {
    private Context context;
    private ArrayList<Products> marrayList;
    private Next_interface anInterface;

    public HomeAdapter(Context context, Next_interface anInterface) {
        this.context = context;
        this.anInterface = anInterface;
    }

    public void setData(ArrayList<Products> arrayList) {
        this.marrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClotherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ClotherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClotherViewHolder holder, int position) {
        Products model = marrayList.get(position);
        holder.tv_name.setText(model.getProductName());
        holder.tv_price.setText(model.getPrice()+ " đ");
        String url = APIClass.URL +"uploads/"+model.getSize().get(0).getColor().get(0).getImage();
        Glide.with(context)
                    .load(url)
                    .apply(RequestOptions.centerCropTransform())
                    .into(holder.imgavatar);
        holder.itemView.setOnClickListener(view -> {
            anInterface.onNextPage(model);
        });
    }

    @Override
    public int getItemCount() {
        if (marrayList != null) {
            return marrayList.size();
        }
        return 0;
    }

    public class ClotherViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price;
        private ImageView imgavatar, img_like;

        public ClotherViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name_product);
            tv_price = itemView.findViewById(R.id.gia_product);
            imgavatar = itemView.findViewById(R.id.img_product);
        }
    }
}
