package md06.fpoly.gentlewear.controller.Adapter;

import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.interfaces.DetailProd_Color_Interface;
import md06.fpoly.gentlewear.models.ColorCode;

public class Adapter_Color_DetailProd extends RecyclerView.Adapter<Adapter_Color_DetailProd.ColorViewHolder> {
    private List<ColorCode> list;
    private DetailProd_Color_Interface mInterface;

    public Adapter_Color_DetailProd(List<ColorCode> list, DetailProd_Color_Interface mInterface) {
        this.list = list;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public Adapter_Color_DetailProd.ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color_detail,parent,false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Color_DetailProd.ColorViewHolder holder, int position) {
        ColorCode model = list.get(position);
        holder.img.setBackgroundColor(Color.parseColor(model.getColorCode()));
        holder.itemView.setOnClickListener(v -> {
            mInterface.chooseColor(model);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {
        private ImageView img ;
        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_color_item_detail_product);
        }
    }
}
