package fr.uha40.mendele.hospital.fragments;

import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha40.mendele.hospital.database.BedDao;
import fr.uha40.mendele.hospital.models.Bed;

public class BedCreationViewModel extends ViewModel {
    private BedDao bedDao;
    private long hospitalServiceId;

    public void setBedDao(BedDao bedDao) {
        this.bedDao = bedDao;
    }

    public void setHospitalId(long hospitalServiceId) {
        this.hospitalServiceId = hospitalServiceId;
    }

    public void saveBed(Bed bedToSave) {
        Executor executor = Executors.newSingleThreadExecutor();
        bedToSave.setHospitalServiceId(hospitalServiceId);
        executor.execute(() -> bedDao.insert(bedToSave));
    }
    // TODO: Implement the ViewModel


}