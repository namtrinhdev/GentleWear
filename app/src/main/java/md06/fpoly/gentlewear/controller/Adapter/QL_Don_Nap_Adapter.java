package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.apiServices.ChangeStatusDonNapInterface;
import md06.fpoly.gentlewear.classs.APIClass;
import md06.fpoly.gentlewear.classs.SessionManager;
import md06.fpoly.gentlewear.models.NapTien;


public class QL_Don_Nap_Adapter extends RecyclerView.Adapter<QL_Don_Nap_Adapter.QHDNViewHolder> {
    private Context context;
    private ArrayList<NapTien> arrayList;
    private ChangeStatusDonNapInterface mInterface;

    public QL_Don_Nap_Adapter(Context context, ChangeStatusDonNapInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }
    public void setData(ArrayList<NapTien> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QHDNViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_don_nap_tien,parent,false);
        return new QHDNViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QHDNViewHolder holder, int position) {
        NapTien model = arrayList.get(position);
        SessionManager sessionManager = new SessionManager(context);
        holder.tv_money.setText(String.valueOf(model.getSoTienNap()));
        holder.tv_title.setText(model.getUserModel().getFullname()+" nạp tiền");
        holder.tv_time.setText(model.getThoiGian());
        String url = APIClass.URL + "uploads/" +model.getAnhGiaoDich();

        Glide.with(context).load(url).placeholder(R.drawable.img_default_user).error(R.drawable.img_default_user).into(holder.img);
        if (model.getTrangThai() == 1){
            holder.btn_tt.setVisibility(View.VISIBLE);
            if (sessionManager.getVaiTro()==0){
                holder.tv_txt.setText("Xác nhận");
            }else {
                holder.tv_txt.setText("Hủy đơn");
            }
        }else {
            holder.btn_tt.setVisibility(View.GONE);
        }
        holder.btn_tt.setOnClickListener(v ->{
            //thuc hien thay doi trang thai
            mInterface.onChange(model.get_id());
        });

        holder.itemView.setOnClickListener(view -> {
            mInterface.openDetailMoney(model);
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList.size()!=0){
            return arrayList.size();
        }
        return 0;
    }

    public class QHDNViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_money,tv_time,tv_txt, tv_title;
        private ImageView img;
        private FrameLayout btn_tt;
        public QHDNViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_money = itemView.findViewById(R.id.tv_tien_nap_item);
            tv_title = itemView.findViewById(R.id.tv_tieuDeNapTien);
            tv_time = itemView.findViewById(R.id.tv_time_nap_item);
            tv_txt = itemView.findViewById(R.id.tv_doi_tt_nap_item);
            btn_tt = itemView.findViewById(R.id.btn_doi_tt_nap_item);
            img = itemView.findViewById(R.id.img_item_donnap);
        }
    }
}
