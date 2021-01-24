package fr.uha40.mendele.hospital.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import fr.uha40.mendele.hospital.R;
import fr.uha40.mendele.hospital.database.AppDatabase;
import fr.uha40.mendele.hospital.databinding.FragmentHospitalServiceBinding;
import fr.uha40.mendele.hospital.models.HospitalService;
import fr.uha40.mendele.hospital.models.factories.RandomHospitalServiceFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HospitalServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HospitalServiceFragment extends Fragment {

    private static final String TAG = "hospitalServiceFragment";

    private HospitalServiceViewModel mViewModel;
    private FragmentHospitalServiceBinding mBinding;

    private long id = -1;

    public HospitalServiceFragment() {
        // Required empty public constructor
    }

    public static HospitalServiceFragment newInstance() {
        return new HospitalServiceFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HospitalServiceViewModel.class);
        mViewModel.setHospitalServiceDao(AppDatabase.getInstance().hospitalServiceDao());
        mBinding.setHospitalService(new HospitalService());
        mViewModel.getHospitalService().observe(getViewLifecycleOwner(), hospitalService -> mBinding.setHospitalService(hospitalService));

        if (id != -1) {
            mViewModel.setId(id);
        } else {
            mBinding.setHospitalService(new HospitalService());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            id = HospitalServiceFragmentArgs.fromBundle(getArguments()).getId();
        } else {
            id = -1;
        }
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
                saveHospitalService();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hospital_service, container, false);
        
        return mBinding.getRoot();
    }

    private void saveHospitalService () {
        HospitalService hospitalServiceToSave = mBinding.getHospitalService();

        if (hospitalServiceToSave == null) {
            Log.e(TAG, "Can't save the hospital service provided by HospitalServiceFragmentBinding " +
                    "because it returned null");
        } else {
            Log.d(TAG, "Hospital service saved -> " + hospitalServiceToSave.toString());
            mViewModel.saveHospitalService(hospitalServiceToSave);
        }

        NavHostFragment.findNavController(this).popBackStack();
    }
}