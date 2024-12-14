package junior.correa.nascimento.rubens.galeriapublica.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import junior.correa.nascimento.rubens.galeriapublica.R;
import junior.correa.nascimento.rubens.galeriapublica.activity.MainActivity;
import junior.correa.nascimento.rubens.galeriapublica.adapter.GridAdapter;
import junior.correa.nascimento.rubens.galeriapublica.model.ImageData;
import junior.correa.nascimento.rubens.galeriapublica.model.MainViewModel;
import junior.correa.nascimento.rubens.galeriapublica.util.ImageDataComparator;
import junior.correa.nascimento.rubens.galeriapublica.util.Util;

public class GridViewFragment extends Fragment {
    MainActivity mainActivity;
    public static GridViewFragment newInstance(MainActivity mainActivity) {
        return new GridViewFragment(mainActivity);
    }
    private GridViewFragment(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid_view, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModel mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        GridAdapter gridAdapter = new GridAdapter(new ImageDataComparator());
        LiveData<PagingData<ImageData>> liveData = mViewModel.getPageLv();
        liveData.observe(getViewLifecycleOwner(), objectPagingData -> gridAdapter.submitData(getViewLifecycleOwner().getLifecycle(),objectPagingData));
        RecyclerView rvGallery = view.findViewById(R.id.rvGrid);
        rvGallery.setAdapter(gridAdapter);
        float w = getResources().getDimension(R.dimen.im_width);
        int numberOfColumns = Util.calculateNoOfColumns(this.mainActivity, w);
        rvGallery.setLayoutManager(new GridLayoutManager(this.mainActivity, numberOfColumns));
    }
}