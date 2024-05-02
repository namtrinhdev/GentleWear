package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.models.Colors;

public class Color_Adapter extends RecyclerView.Adapter<Color_Adapter.ColorViewHolder> {
    private List<Colors> colors;
    private Context context;

    public Color_Adapter(List<Colors> colors, Context context) {
        this.colors = colors;

    }
    public void setdata(List<Colors> colors) {
        this.colors = colors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mau, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        Colors color = colors.get(position);
        holder.colorName.setText(color.getColorCode().getNameColor());
        Glide.with(context).load(color.getImage()).into(holder.colorImage);
        Log.d("Color_Adapter", "Item at position " + position + " bound.");

    }
    @Override
    public int getItemCount() {
        return colors.size();
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        ImageView colorImage;
        TextView colorName;
        CheckBox checkBox;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            colorImage = itemView.findViewById(R.id.ImageColor);
            colorName = itemView.findViewById(R.id.textViewProductColorName);
            checkBox = itemView.findViewById(R.id.checkBoxProductColor);
        }
    }
}


