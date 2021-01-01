package fr.uha40.mendele.hospital.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class HospitalService {
    @PrimaryKey(autoGenerate = true)
    protected long id;
    protected String serviceName;

    public HospitalService() { this.id = 0; }

    @Ignore
    public HospitalService(String serviceName) {
        this();
        this.serviceName = serviceName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "HospitalService{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}
