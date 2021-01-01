package fr.uha40.mendele.hospital.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha40.mendele.hospital.database.HospitalServiceDao;
import fr.uha40.mendele.hospital.models.HospitalService;

public class HospitalServiceViewModel extends ViewModel {
    private HospitalServiceDao hospitalServiceDao;
    private MutableLiveData<Long> id = new MutableLiveData<>();
    private LiveData<HospitalService> hospitalService;

    public void saveHospitalService(HospitalService hospitalServiceToSave) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> hospitalServiceDao.insert(hospitalServiceToSave));
    }

    public LiveData<HospitalService> getHospitalService () {
        return hospitalService;
    }

    public void setId (long id) {
        this.id.postValue(id);
    }

    public void setHospitalServiceDao(HospitalServiceDao hospitalServiceDao) {
        this.hospitalServiceDao = hospitalServiceDao;
        this.hospitalService = Transformations.switchMap(id, v -> this.hospitalServiceDao.findById(v));
    }
}
