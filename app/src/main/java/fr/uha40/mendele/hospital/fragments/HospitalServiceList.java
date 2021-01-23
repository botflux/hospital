package fr.uha40.mendele.hospital.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.uha40.mendele.hospital.R;
import fr.uha40.mendele.hospital.database.AppDatabase;
import fr.uha40.mendele.hospital.databinding.HospitalServiceListItemBinding;
import fr.uha40.mendele.hospital.models.HospitalService;

public class HospitalServiceList extends Fragment {

    private HospitalServiceListViewModel mViewModel;
    private HospitalServiceListAdapter adapter;

    public static HospitalServiceList newInstance() {
        return new HospitalServiceList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.hospital_service_list_fragment, container, false);
        RecyclerView list = root.findViewById(R.id.hospitalServiceListView);
        list.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL, false));

        adapter = new HospitalServiceListAdapter();
        list.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(list.getContext(), DividerItemDecoration.VERTICAL);

        divider.getDrawable().setAlpha(69);
        list.addItemDecoration(divider);

        root.findViewById(R.id.fab).setOnClickListener(view1 -> NavHostFragment.findNavController(HospitalServiceList.this)
                .navigate(R.id.action_hospitalServiceList_to_hospitalServiceFragment2));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HospitalServiceListViewModel.class);
        mViewModel.setHospitalServiceDao(AppDatabase.getInstance().hospitalServiceDao());
        mViewModel.getHospitalServices().observe(getViewLifecycleOwner(), list -> adapter.setHospitalServices(list));
    }

    private class HospitalServiceListAdapter extends RecyclerView.Adapter<HospitalServiceListAdapter.HospitalServiceHolder> {

        private List<HospitalService> hospitalServices;

        private class HospitalServiceHolder extends RecyclerView.ViewHolder {
            HospitalServiceListItemBinding binding;

            public HospitalServiceHolder(@NonNull HospitalServiceListItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
                this.binding.getRoot().setOnClickListener(view -> {
                    int position = getLayoutPosition();
                    HospitalService hospitalService = hospitalServices.get(position);
//                    HospitalServiceListDirections.ActionHospitalServiceListToHospitalServiceFragment2 action =
//                            HospitalServiceListDirections.actionHospitalServiceListToHospitalServiceFragment2();

                    HospitalServiceListDirections.ActionHospitalServiceListToBedListFragment action =
                            HospitalServiceListDirections.actionHospitalServiceListToBedListFragment(hospitalService.getId());

                    action.setId(hospitalService.getId());

                    NavHostFragment
                            .findNavController(HospitalServiceList.this)
                            .navigate(action);
                });

                this.binding.getRoot().setOnLongClickListener(view -> {
                    int position = getLayoutPosition();
                    HospitalService hospitalService = hospitalServices.get(position);
                    mViewModel.deleteHospitalService(hospitalService);
                    return true;
                });
//                this.binding.getRoot().setOnClickListener();
            }
        }

        public void setHospitalServices(List<HospitalService> hospitalServices) {
            this.hospitalServices = hospitalServices;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public HospitalServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            HospitalServiceListItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.hospital_service_list_item, parent, false
            );
            return new HospitalServiceHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull HospitalServiceHolder holder, int position) {
            HospitalService hospitalService = hospitalServices.get(position);
            holder.binding.setHospitalService(hospitalService);
        }

        @Override
        public int getItemCount() {
            return hospitalServices == null
                ? 0
                : hospitalServices.size();
        }
    }
}