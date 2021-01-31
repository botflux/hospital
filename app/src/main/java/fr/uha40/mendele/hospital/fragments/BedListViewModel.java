package fr.uha40.mendele.hospital.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha40.mendele.hospital.database.BedDao;
import fr.uha40.mendele.hospital.database.HospitalServiceDao;
import fr.uha40.mendele.hospital.models.Bed;
import fr.uha40.mendele.hospital.models.HospitalServiceWithBeds;

public class BedListViewModel extends ViewModel {
    private HospitalServiceDao hospitalServiceDao;
    private BedDao bedDao;
    private MutableLiveData<Long> id = new MutableLiveData<>();
    private LiveData<HospitalServiceWithBeds> hospitalServiceWithBeds;

    public LiveData<HospitalServiceWithBeds> getHospitalServiceWithBeds () {
        return hospitalServiceWithBeds;
    }

    public void setId (long id) {
        this.id.postValue(id);
    }

    public void setHospitalServiceDao(HospitalServiceDao hospitalServiceDao) {
        this.hospitalServiceDao = hospitalServiceDao;
        this.hospitalServiceWithBeds = Transformations
                .switchMap(id, v -> this.hospitalServiceDao.findHospitalServiceWithBedsById(v));
    }

    public void deleteBed(Bed bed) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> bedDao.delete(bed));
    }

    public void setBedDao(BedDao bedDao) {
        this.bedDao = bedDao;
    }
}