package junior.correa.nascimento.rubens.galeriapublica.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import junior.correa.nascimento.rubens.galeriapublica.model.ImageData;

public class ImageDataComparator extends DiffUtil.ItemCallback<ImageData> {
    @Override
    public boolean areItemsTheSame(@NonNull ImageData oldItem, @NonNull ImageData newItem) {
        // Id is unique.
        return oldItem.uri.equals(newItem.uri);
    }

    @Override
    public boolean areContentsTheSame(@NonNull ImageData oldItem, @NonNull ImageData newItem) {
        return oldItem.uri.equals(newItem.uri);
    }
}