package junior.correa.nascimento.rubens.galeriapublica.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import junior.correa.nascimento.rubens.galeriapublica.R;
import junior.correa.nascimento.rubens.galeriapublica.adapter.ListAdapter;
import junior.correa.nascimento.rubens.galeriapublica.model.ImageData;
import junior.correa.nascimento.rubens.galeriapublica.model.MainViewModel;
import junior.correa.nascimento.rubens.galeriapublica.util.ImageDataComparator;

public class ListViewFragment extends Fragment {
    public static ListViewFragment newInstance() {
        return new ListViewFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainViewModel mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        ListAdapter listAdapter = new ListAdapter(new ImageDataComparator());
        LiveData<PagingData<ImageData>> liveData = mViewModel.getPageLv();
        liveData.observe(getViewLifecycleOwner(), objectPagingData -> listAdapter.submitData(getViewLifecycleOwner().getLifecycle(),objectPagingData));
        RecyclerView rvGallery = view.findViewById(R.id.rvList);
        rvGallery.setAdapter(listAdapter);
        rvGallery.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}