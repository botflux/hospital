package fr.uha40.mendele.hospital.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.uha40.mendele.hospital.models.HospitalService;

@Dao
public interface HospitalServiceDao {

    @Query("SELECT * FROM hospitalservice")
    LiveData<List<HospitalService>> getAll();

    @Query("SELECT * FROM hospitalservice WHERE id = :id")
    HospitalService findById(long id);

    @Insert
    void insert (HospitalService hospitalServices);
}
