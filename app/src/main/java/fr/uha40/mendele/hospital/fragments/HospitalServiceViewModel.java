package fr.uha40.mendele.hospital.fragments;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha40.mendele.hospital.database.HospitalServiceDao;
import fr.uha40.mendele.hospital.models.HospitalService;

public class HospitalServiceViewModel extends ViewModel {
    private HospitalServiceDao hospitalServiceDao;

    public void saveHospitalService(HospitalService hospitalServiceToSave) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> hospitalServiceDao.insert(hospitalServiceToSave));
    }

    public void setHospitalServiceDao(HospitalServiceDao hospitalServiceDao) {
        this.hospitalServiceDao = hospitalServiceDao;
    }
}
