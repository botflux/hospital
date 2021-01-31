package fr.uha40.mendele.hospital.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.javafaker.Faker;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha40.mendele.hospital.R;
import fr.uha40.mendele.hospital.database.AppDatabase;
import fr.uha40.mendele.hospital.database.BedDao;
import fr.uha40.mendele.hospital.databinding.BedItemBinding;
import fr.uha40.mendele.hospital.models.Bed;
import fr.uha40.mendele.hospital.models.BedState;

public class BedListFragment extends Fragment {

    private BedListViewModel mViewModel;
    private BedListAdapter mAdapter;
    private long id;

    public static BedListFragment newInstance() {
        return new BedListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_rename, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rename:
                navigateToServiceNameEdition();
                return true;
            case R.id.action_populate:
                populateDatabaseWithFakeData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateDatabaseWithFakeData() {
        BedDao bedDao = AppDatabase.getInstance().bedDao();
        Faker faker = new Faker(Locale.FRANCE);
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            for (int i = 0; i < 10; i ++) {
                Bed bed = new Bed(0, id, BedState.Empty, faker.name().title());
                bedDao.insert(bed);
            }
        });
    }

    private void navigateToServiceNameEdition() {
        BedListFragmentDirections.ActionBedListFragmentToHospitalServiceFragment2 action =
                BedListFragmentDirections.actionBedListFragmentToHospitalServiceFragment2();
        action.setId(id);

        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.bed_list_fragment, container, false);
        RecyclerView list = root.findViewById(R.id.bedListRecyclerView);
        list.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false));

        mAdapter = new BedListAdapter();
        list.setAdapter(mAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL);
        divider.getDrawable().setAlpha(69);
        list.addItemDecoration(divider);

        root.findViewById(R.id.fab)
                .setOnClickListener(view -> {
                    BedListFragmentDirections.ActionBedListFragmentToBedFragment action =
                            BedListFragmentDirections.actionBedListFragmentToBedFragment(id);

                    NavHostFragment.findNavController(BedListFragment.this)
                            .navigate(action);
                });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BedListViewModel.class);
        mViewModel.setHospitalServiceDao(AppDatabase.getInstance().hospitalServiceDao());
        mViewModel.setBedDao(AppDatabase.getInstance().bedDao());
        mViewModel.getHospitalServiceWithBeds()
            .observe(getViewLifecycleOwner(), hospitalServiceWithBeds -> mAdapter.setBeds(hospitalServiceWithBeds.getServiceBeds()));

        if (getArguments() != null) {
            BedListFragmentArgs args = BedListFragmentArgs.fromBundle(getArguments());
            mViewModel.setId(args.getId());
            id = args.getId();
        }
        // TODO: Use the ViewModel
    }

    private class BedListAdapter extends RecyclerView.Adapter<BedListAdapter.BedHolder> {
        private List<Bed> beds;

        public void setBeds(List<Bed> beds) {
            this.beds = beds;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public BedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BedItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.bed_item, parent, false
            );
            return new BedHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull BedHolder holder, int position) {
            Bed bed = beds.get(position);
            holder.binding.setBed(bed);
        }

        @Override
        public int getItemCount() {
            return beds != null
                    ? beds.size()
                    : 0;
        }


        private class BedHolder extends RecyclerView.ViewHolder {
            BedItemBinding binding;

            public BedHolder(@NonNull BedItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.getRoot().setOnClickListener(view -> {
                    int layoutPosition = getLayoutPosition();
                    Bed bed = beds.get(layoutPosition);

                    BedListFragmentDirections.ActionBedListFragmentToBedEditionFragment action =
                            BedListFragmentDirections.actionBedListFragmentToBedEditionFragment();

                    action.setBedId(bed.getBedId());

                    NavHostFragment.findNavController(BedListFragment.this)
                            .navigate(action);
                });

                binding.getRoot().setOnLongClickListener(view -> {
                    int layoutPosition = getLayoutPosition();
                    Bed bed = beds.get(layoutPosition);

                    mViewModel.deleteBed(bed);

                    return true;
                });
            }
        }
    }
}