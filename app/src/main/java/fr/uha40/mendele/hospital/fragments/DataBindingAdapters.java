package fr.uha40.mendele.hospital.fragments;

import fr.uha40.mendele.hospital.models.BedState;

public class DataBindingAdapters {
    public static String toString (BedState bedState) {
        return "The bed is currently " + bedState.toString().toLowerCase();
    }
}
