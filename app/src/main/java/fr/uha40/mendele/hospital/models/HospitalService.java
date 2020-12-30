package fr.uha40.mendele.hospital.models;

public class HospitalService {
    protected long id;
    protected String serviceName;

    public HospitalService() { this.id = 0; }

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
}
