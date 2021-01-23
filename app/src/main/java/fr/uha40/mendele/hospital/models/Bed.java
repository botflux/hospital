package fr.uha40.mendele.hospital.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Bed {
    @PrimaryKey
    protected long bedId;
    protected String bedName;
    protected long hospitalServiceId;
    protected BedState currentState;

    public Bed() {
        this(0, -1, BedState.Empty, "");
    }

    @Ignore
    public Bed(long bedId, long hospitalServiceId, BedState currentState, String bedName) {
        this.bedId = bedId;
        this.hospitalServiceId = hospitalServiceId;
        this.currentState = currentState;
    }

    public long getBedId() {
        return bedId;
    }

    public void setBedId(long bedId) {
        this.bedId = bedId;
    }

    public long getHospitalServiceId() {
        return hospitalServiceId;
    }

    public void setHospitalServiceId(long hospitalServiceId) {
        this.hospitalServiceId = hospitalServiceId;
    }

    public BedState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(BedState currentState) {
        this.currentState = currentState;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }
}
