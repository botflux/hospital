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

import com.google.android.material.snackbar.Snackbar;

import fr.uha40.mendele.hospital.R;
import fr.uha40.mendele.hospital.database.AppDatabase;
import fr.uha40.mendele.hospital.databinding.BedFragmentBinding;
import fr.uha40.mendele.hospital.models.Bed;

public class BedFragment extends Fragment {

    private BedViewModel mViewModel;
    private BedFragmentBinding mBinding;

    private long hospitalServiceId = -1;

    public static BedFragment newInstance() {
        return new BedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.bed_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BedViewModel.class);
        mViewModel.setBedDao(AppDatabase.getInstance().bedDao());
        mBinding.setBed(new Bed());

        if (hospitalServiceId != -1) {
            mViewModel.setHospitalId(hospitalServiceId);
        }

        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                saveBed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveBed() {
        Bed bedToSave = mBinding.getBed();
        mViewModel.saveBed(bedToSave);
        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            BedFragmentArgs args = BedFragmentArgs.fromBundle(getArguments());
            hospitalServiceId = args.getHospitalServiceId();
        } else {
            hospitalServiceId = -1;
        }
    }
}