package junior.correa.nascimento.rubens.galeriapublica.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import junior.correa.nascimento.rubens.galeriapublica.R;
import junior.correa.nascimento.rubens.galeriapublica.model.ImageData;

public class GridAdapter extends PagingDataAdapter<ImageData, MyViewHolder> {

    public GridAdapter(@NonNull DiffUtil.ItemCallback<ImageData> diffCallback) {
        super(diffCallback);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(view);
    }
    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        System.out.println("GridAdapter::onBindViewHolder");
        ImageData imageData = getItem(position);
        assert imageData != null;
        Bitmap thumb = imageData.thumb;
        ImageView imageView = holder.itemView.findViewById(R.id.imThumb);
        imageView.setImageBitmap(thumb);
    }
}