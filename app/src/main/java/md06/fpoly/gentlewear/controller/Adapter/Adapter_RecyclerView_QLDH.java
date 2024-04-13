package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import md06.fpoly.gentlewear.apiServices.ChangeStatusDonNapInterface;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.models.ThanhToan;
import md06.fpoly.gentlewear.R;

public class Adapter_RecyclerView_QLDH extends RecyclerView.Adapter<Adapter_RecyclerView_QLDH.QLDH_ViewHolder> {
    private Context context;
    private List<ThanhToan> arrayList;
    private Adapter_DonHang adapter;
    private SessionManager sessionManager;
    private ChangeStatusDonNapInterface mInterface;

    public Adapter_RecyclerView_QLDH(Context context, ChangeStatusDonNapInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
        adapter = new Adapter_DonHang(context);
    }

    public void setData(List<ThanhToan> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QLDH_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qldh, parent, false);
        return new QLDH_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLDH_ViewHolder holder, int position) {
        ThanhToan model = arrayList.get(position);
        sessionManager = new SessionManager(context);
        adapter.setData(model.getCart());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.recyclerView.setAdapter(adapter);
        holder.tv_name.setText("Đơn hàng của " + model.getUser().getFullname());
        holder.tv_total_price.setText("Tổng tiền : " + model.getTongTien() + " đ");
        boolean vaitro = sessionManager.getVaiTro() == 0 ? true : false;
        holder.btn_change_status.setVisibility(View.VISIBLE);
        if (model.getTrangThai() == 1) {
            if (vaitro) {
                holder.tv_status.setText("Xác nhận");
            } else {
                holder.tv_status.setText("Hủy đơn");
            }
        } else {
            if (vaitro) {
                if (model.getTrangThai() == 2) {
                    holder.tv_status.setText("Xác nhận");
                } else if (model.getTrangThai() == 3) {
                    holder.tv_status.setText("Xác nhận");
                }else {
                    holder.btn_change_status.setVisibility(View.GONE);
                }
            } else {
                holder.btn_change_status.setVisibility(View.GONE);
            }
        }

        holder.btn_change_status.setOnClickListener(view -> {
            mInterface.onChange(model.get_id());
            notifyDataSetChanged();
        });

        holder.itemView.setOnClickListener(view -> {
            mInterface.openDetail(model);
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList.size() != 0) {
            return arrayList.size();
        }
        return 0;
    }

    public class QLDH_ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView tv_name, tv_total_price, tv_status;
        private FrameLayout btn_change_status;

        public QLDH_ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.id_recycle_item_qldh);
            tv_name = itemView.findViewById(R.id.tv_item_nameUser_qldh);
            tv_total_price = itemView.findViewById(R.id.tv_item_total_price_qldh);
            tv_status = itemView.findViewById(R.id.tv_item_change_status_qldh);
            btn_change_status = itemView.findViewById(R.id.btn_item_change_status_qldh);
        }
    }
}
