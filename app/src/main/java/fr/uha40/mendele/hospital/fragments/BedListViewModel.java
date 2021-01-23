package fr.uha40.mendele.hospital.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import fr.uha40.mendele.hospital.database.HospitalServiceDao;
import fr.uha40.mendele.hospital.models.HospitalServiceWithBeds;

public class BedListViewModel extends ViewModel {
    private HospitalServiceDao hospitalServiceDao;
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
}