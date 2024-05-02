package md06.fpoly.gentlewear.controller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import md06.fpoly.gentlewear.R;

public class SlideShowAdapter extends RecyclerView.Adapter<SlideShowAdapter.PhotoViewPager> {
    private final List<String> list ;
    private final Context context;

    public SlideShowAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public SlideShowAdapter.PhotoViewPager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo,parent, false);
        return new PhotoViewPager(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideShowAdapter.PhotoViewPager holder, int position) {
        String a = list.get(position);
        Glide.with(context).load(a).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class PhotoViewPager extends RecyclerView.ViewHolder {
        private ImageView img;
        public PhotoViewPager(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_slideshow);
        }
    }
}
