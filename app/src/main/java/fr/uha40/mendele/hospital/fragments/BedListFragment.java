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
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.uha40.mendele.hospital.R;
import fr.uha40.mendele.hospital.database.AppDatabase;
import fr.uha40.mendele.hospital.databinding.BedItemBinding;
import fr.uha40.mendele.hospital.models.Bed;

public class BedListFragment extends Fragment {

    private BedListViewModel mViewModel;
    private BedListAdapter mAdapter;
    private long id;

    public static BedListFragment newInstance() {
        return new BedListFragment();
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
            }
        }
    }
}