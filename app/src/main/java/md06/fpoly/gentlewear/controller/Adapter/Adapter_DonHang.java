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

import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.models.Cart;


public class Adapter_DonHang extends RecyclerView.Adapter<Adapter_DonHang.DonHangViewHolder> {
    private Context context;
    private List<Cart> arrayList;

    public Adapter_DonHang(Context context) {
        this.context = context;
    }
    public void setData(List<Cart> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donhang,parent,false);
        return new DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        Cart model = arrayList.get(position);
        holder.tv_nameproducts.setText(model.getProducts().getProductName());
        holder.tv_price.setText(model.getProducts().getPrice()+" đ");
        holder.tv_quantity.setText(model.getSoLuong()+"");
//        Glide.with(context).load(model.getProducts().getImage()).apply(RequestOptions.centerCropTransform()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (arrayList.size() !=0 ){
            return arrayList.size();
        }
        return 0;
    }

    public class DonHangViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nameproducts, tv_price, tv_quantity;
        private ImageView img;
        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nameproducts = itemView.findViewById(R.id.tv_item_namepro_donhang);
            tv_price = itemView.findViewById(R.id.tv_item_price_donHang);
            tv_quantity = itemView.findViewById(R.id.tv_item_quantity_donHang);
            img = itemView.findViewById(R.id.img_item_donHang);
        }
    }
}
