package md06.fpoly.gentlewear.controller.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.QLDH_Interface;
import md06.fpoly.gentlewear.models.ThanhToan;

public class Adapter_QLDH extends RecyclerView.Adapter<Adapter_QLDH.WaitViewHolder> {
    private List<ThanhToan> list;
    private QLDH_Interface mInterface;

    public Adapter_QLDH(List<ThanhToan> list,QLDH_Interface mInterface) {
        this.list = list;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public Adapter_QLDH.WaitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_qldh, parent, false);
        return new WaitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_QLDH.WaitViewHolder holder, int position) {
        ThanhToan model = list.get(position);

        if (model.getCart().size() > 1) {
            holder.tv_more.setVisibility(View.VISIBLE);
            int more = model.getCart().size() - 1;
            holder.tv_more.setText("+ " + more + " sản phẩm");
        } else {
            holder.tv_more.setVisibility(View.GONE);
        }

        if (model.getTrangThai() == 1) {
            holder.btn_huy.setVisibility(View.VISIBLE);
        } else {
            holder.btn_huy.setVisibility(View.GONE);
        }

        switch (model.getTrangThai()) {
            case 0:
                holder.tv_status.setText("Đã hủy");
                break;
            case 1:
                holder.tv_status.setText("Chờ xác nhận");
                break;
            case 2:
                holder.tv_status.setText("Chờ lấy hàng");
                break;
            case 3:
                holder.tv_status.setText("Đang giao");
                break;
            case 4:
                holder.tv_status.setText("Đã giao");
                break;
        }

        holder.tv_time.setText(model.getThoiGian());
        holder.tv_color.setText("Màu sắc: "+model.getCart().get(0).getProducts().getSize().get(0).getColor().get(0).getColorCode().getNameColor());
        holder.tv_size.setText("Size: "+ model.getCart().get(0).getProducts().getSize().get(0).getSizeCode().getSizeCode());
        holder.tv_quantity.setText(String.valueOf(model.getCart().get(0).getSoLuong()));
        holder.tv_price.setText(model.getCart().get(0).getProducts().getPrice()+"đ");
        holder.tv_totalPrice.setText(model.getTongTien()+"đ");
        holder.tv_name.setText(model.getCart().get(0).getProducts().getProductName());

        //click huy
        holder.btn_huy.setOnClickListener(v -> {
            //huy
        });

        holder.itemView.setOnClickListener(v -> {
            mInterface.onReceive(model);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class WaitViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_time, tv_status, tv_name, tv_color, tv_size, tv_price, tv_quantity, tv_totalPrice, tv_more;
        private FrameLayout btn_huy;
        private ImageView img;

        public WaitViewHolder(@NonNull View itv) {
            super(itv);
            tv_time = itv.findViewById(R.id.tv_time_item_donHang);
            tv_status = itv.findViewById(R.id.tv_trangThaiDH);
            tv_name = itv.findViewById(R.id.tv_name_item_donHang);
            tv_color = itv.findViewById(R.id.tv_item_color_donHang);
            tv_size = itv.findViewById(R.id.tv_item_size_donHang);
            tv_price = itv.findViewById(R.id.tv_item_price_donHang);
            tv_quantity = itv.findViewById(R.id.tv_item_quantity_donHang);
            tv_totalPrice = itv.findViewById(R.id.tv_total_P_item_donHang);
            tv_more = itv.findViewById(R.id.tv_isMore_item_donHang);
            btn_huy = itv.findViewById(R.id.btn_huyDon_item_donHang);
            img = itv.findViewById(R.id.img_item_donHang);
        }
    }
}
