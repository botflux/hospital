package fr.uha40.mendele.hospital.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.HashMap;
import java.util.Map;

import fr.uha40.mendele.hospital.R;
import fr.uha40.mendele.hospital.database.AppDatabase;
import fr.uha40.mendele.hospital.databinding.BedEditionFragmentBinding;
import fr.uha40.mendele.hospital.models.Bed;
import fr.uha40.mendele.hospital.models.BedState;

public class BedEditionFragment extends Fragment {

    private BedEditionViewModel mViewModel;
    private BedEditionFragmentBinding mBinding;

    public static BedEditionFragment newInstance() {
        return new BedEditionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.bed_edition_fragment, container, false);
        mBinding.setBed(new Bed());
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BedEditionViewModel.class);
        mViewModel.setBedDao(AppDatabase.getInstance().bedDao());
        mViewModel.getBed().observe(getViewLifecycleOwner(), bed -> mBinding.setBed(bed));

        if (getArguments() != null) {
            BedEditionFragmentArgs args = BedEditionFragmentArgs.fromBundle(getArguments());
            mViewModel.setBedId(args.getBedId());
        }
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_save:
                mViewModel.saveBed(mBinding.getBed());
                NavHostFragment.findNavController(this).popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}