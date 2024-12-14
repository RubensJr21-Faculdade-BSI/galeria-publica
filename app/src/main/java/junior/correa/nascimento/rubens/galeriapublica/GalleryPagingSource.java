package junior.correa.nascimento.rubens.galeriapublica;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Executors;

import junior.correa.nascimento.rubens.galeriapublica.Repository.GalleryRepository;
import junior.correa.nascimento.rubens.galeriapublica.model.ImageData;

public class GalleryPagingSource extends ListenableFuturePagingSource<Integer, ImageData> {
    GalleryRepository galleryRepository;
    Integer initialLoadSize = 0;
    public GalleryPagingSource(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }
    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, ImageData> pagingState) {
        return null;
    }
    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, ImageData>> loadFuture(@NonNull LoadParams<Integer> loadParams) {
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
            initialLoadSize = loadParams.getLoadSize();
        }
        Integer offSet;
        if(nextPageNumber == 2) {
            offSet = initialLoadSize;
        }
        else {
            offSet = ((nextPageNumber - 1) * loadParams.getLoadSize()) + (initialLoadSize - loadParams.getLoadSize());
        }
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        Integer finalOffSet = offSet;
        Integer finalNextPageNumber = nextPageNumber;
        return service.submit(() -> {
            List<ImageData> imageDataList;
            try {
                imageDataList = galleryRepository.loadImageData(loadParams.getLoadSize(), finalOffSet);
                Integer nextKey = null;
                if(imageDataList.size() >= loadParams.getLoadSize()) {
                    nextKey = finalNextPageNumber + 1;
                }
                return new LoadResult.Page<>(imageDataList,
                        null,
                        nextKey);
            } catch (FileNotFoundException e) {
                return new LoadResult.Error<>(e);
            }
        });
    }
}