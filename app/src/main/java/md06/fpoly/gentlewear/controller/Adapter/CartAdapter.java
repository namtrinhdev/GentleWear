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
import md06.fpoly.gentlewear.activitys.MainActivity;
import md06.fpoly.gentlewear.apiServices.Cart_Update_Interface;
import md06.fpoly.gentlewear.models.Cart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    private ArrayList<Cart> arrayList;
    private Cart_Update_Interface anInterface;
    private int count;

    public CartAdapter(Context context, Cart_Update_Interface anInterface) {
        this.context = context;
        this.anInterface = anInterface;
    }
    public void setData(ArrayList<Cart> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
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
        Cart model = arrayList.get(position);
        holder.tv_namesp.setText(model.getProducts().getProductName());
        holder.tv_price.setText(String.valueOf(model.getProducts().getPrice()));
        holder.tv_quantity.setText(String.valueOf(model.getSoLuong()));
        holder.tv_color.setText("Màu sắc: "+model.getProducts().getSize().get(0).getColor().get(0).getColorCode().getNameColor());
        holder.tv_size.setText("Kich cỡ: "+model.getProducts().getSize().get(0).getSizeCode().getSizeCode());
        Glide.with(context).load(model.getProducts().getImage()).apply(RequestOptions.centerCropTransform()).into(holder.image_sp);
        count = model.getSoLuong();
        holder.img_increase.setOnClickListener(view -> {
            if (count < model.getProducts().getQuantity() && count < 15){
                count++;
                holder.tv_quantity.setText(String.valueOf(count));
                model.setSoLuong(count);
                anInterface.onUpdateCount();
            }
        });
        holder.img_diminish.setOnClickListener(view -> {
            if (count > 1){
                count--;
                holder.tv_quantity.setText(String.valueOf(count));
                model.setSoLuong(count);
                anInterface.onUpdateCount();
            }else {
                anInterface.onDelete(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(view -> {
            anInterface.onDelete(holder.getAdapterPosition());
            return false;
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList.size() !=0 ){
            return arrayList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_namesp, tv_price, tv_quantity,tv_color, tv_size;
        private ImageView image_sp, img_increase, img_diminish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_namesp = itemView.findViewById(R.id.tv_namesp);
            tv_price = itemView.findViewById(R.id.tv_price_card);
            tv_quantity = itemView.findViewById(R.id.tv_item_quantity_cart);
            image_sp = itemView.findViewById(R.id.image_spcart);\
            tv_color = itemView.findViewById(R.id.tv_item_color);
            tv_size = itemView.findViewById(R.id.tv_item_size);
            img_increase = itemView.findViewById(R.id.img_item_add_cart);
            img_diminish = itemView.findViewById(R.id.img_item_remove_cart);



        }
    }
}
