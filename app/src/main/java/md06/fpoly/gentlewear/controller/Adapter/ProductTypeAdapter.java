package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import md06.fpoly.gentlewear.models.ProductType;
import md06.fpoly.gentlewear.R;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ProductTypeViewHolder> {

    private Context context;
    private List<ProductType> productTypes;

    public ProductTypeAdapter(Context context, List<ProductType> productTypes) {
        this.context = context;
        this.productTypes = productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_type, parent, false);
        return new ProductTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTypeViewHolder holder, int position) {
        ProductType productType = productTypes.get(position);
        holder.bind(productType);

        // Đặt trạng thái của checkbox dựa trên trạng thái đã chọn của loại sản phẩm
        holder.checkBox.setChecked(productType.isSelected());

        // Xử lý sự kiện chọn loại sản phẩm
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                productType.setSelected(isChecked);
            }
        });
    }


    public String getSelectedProductTypeId() {
        // Iterate through the list of product types to find the selected one
        for (ProductType productType : productTypes) {
            if (productType.isSelected()) {
                return productType.get_id();
            }
        }
        return null; // Return null if no product type is selected
    }
    @Override
    public int getItemCount() {
        return productTypes.size();
    }

    public String getSelectedProductType() {
        for (ProductType productType : productTypes) {
            if (productType.isSelected()) {
                return productType.getTenLoai();
            }
        }
        return null; // Trả về null nếu không có loại sản phẩm nào được chọn
    }

    public class ProductTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewProductTypeName;
        private CheckBox checkBox;

        public ProductTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.btn_check);
            textViewProductTypeName = itemView.findViewById(R.id.textViewProductTypeName);
        }

        public void bind(ProductType productType) {
            textViewProductTypeName.setText(productType.getTenLoai());
        }
    }
}


