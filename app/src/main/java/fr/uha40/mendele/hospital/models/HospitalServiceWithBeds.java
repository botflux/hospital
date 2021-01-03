package fr.uha40.mendele.hospital.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class HospitalServiceWithBeds {
    @Embedded
    protected HospitalService hospitalService;

    @Relation(
            entityColumn = "hospitalServiceId",
            parentColumn = "id"
    )
    protected List<Bed> serviceBeds;

    public HospitalServiceWithBeds() {
        this(null, new ArrayList<>());
    }

    @Ignore
    public HospitalServiceWithBeds(HospitalService hospitalService, List<Bed> serviceBeds) {
        this.hospitalService = hospitalService;
        this.serviceBeds = serviceBeds;
    }

    public HospitalService getHospitalService() {
        return hospitalService;
    }

    public void setHospitalService(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    public List<Bed> getServiceBeds() {
        return serviceBeds;
    }

    public void setServiceBeds(List<Bed> serviceBeds) {
        this.serviceBeds = serviceBeds;
    }
}
