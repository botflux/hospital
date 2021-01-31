package fr.uha40.mendele.hospital.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha40.mendele.hospital.database.BedDao;
import fr.uha40.mendele.hospital.models.Bed;

public class BedEditionViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private BedDao bedDao;
    private LiveData<Bed> bed;
    private MutableLiveData<Long> bedId = new MutableLiveData<>();

    public void setBedDao(BedDao bedDao) {
        this.bedDao = bedDao;
        this.bed = Transformations
                .switchMap(bedId, v -> this.bedDao.findBedById(v));
    }

    public void setBedId(long bedId) {
        this.bedId.postValue(bedId);
    }

    public void saveBed(Bed bedToSave) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> bedDao.update(bedToSave));
    }

    public LiveData<Bed> getBed () {
        return bed;
    }
}