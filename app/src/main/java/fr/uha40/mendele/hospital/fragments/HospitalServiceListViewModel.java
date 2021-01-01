package fr.uha40.mendele.hospital.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.uha40.mendele.hospital.database.HospitalServiceDao;
import fr.uha40.mendele.hospital.models.HospitalService;

public class HospitalServiceListViewModel extends ViewModel {
    private HospitalServiceDao hospitalServiceDao;
    public MediatorLiveData<List<HospitalService>> hospitalServices;

    public void setHospitalServiceDao(HospitalServiceDao hospitalServiceDao) {
        this.hospitalServiceDao = hospitalServiceDao;
        hospitalServices = new MediatorLiveData<>();
        hospitalServices.addSource(hospitalServiceDao.getAll(), hospitalServices::setValue);
    }

    public LiveData<List<HospitalService>> getHospitalServices () {
        return hospitalServices;
    }

    // TODO: Implement the ViewModel
}