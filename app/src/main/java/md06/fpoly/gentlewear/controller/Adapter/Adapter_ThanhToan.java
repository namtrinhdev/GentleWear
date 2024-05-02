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

import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.models.Cart;

public class Adapter_ThanhToan extends RecyclerView.Adapter<Adapter_ThanhToan.TTViewHolder> {
    private List<Cart> list;
    private Context context;
    public Adapter_ThanhToan(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
    }
    public void setData(List<Cart> arrayList){
        this.list = arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Adapter_ThanhToan.TTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new TTViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ThanhToan.TTViewHolder holder, int position) {
        Cart model = list.get(position);
        holder.tv_name.setText(model.getProducts().getProductName());
        holder.tv_size.setText("Kích cỡ : "+model.getProducts().getSize().get(0).getSizeCode().getSizeCode());
        holder.tv_color.setText("Màu sắc : "+model.getProducts().getSize().get(0).getColor().get(0).getColorCode().getNameColor());
        holder.tv_price.setText(model.getProducts().getPrice()+ " đ");
        holder.tv_qTy.setText(String.valueOf(model.getSoLuong()));
        String imageUrl = APIClass.URL + "uploads/" + model.getProducts().getSize().get(0).getColor().get(0).getImage();

        Glide.with(context)
                .load(imageUrl)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class TTViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_color, tv_size, tv_price, tv_qTy;
        private ImageView img;
        public TTViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_item_namepro_donhang);
            tv_color = itemView.findViewById(R.id.tv_item_color_donHang);
            tv_size = itemView.findViewById(R.id.tv_item_size_donHang);
            tv_price = itemView.findViewById(R.id.tv_item_price_donHang);
            tv_qTy = itemView.findViewById(R.id.tv_item_quantity_donHang);
            img = itemView.findViewById(R.id.img_item_donHang_TT);
        }
    }
}
