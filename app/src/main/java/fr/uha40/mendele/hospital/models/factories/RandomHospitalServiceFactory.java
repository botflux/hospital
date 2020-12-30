package fr.uha40.mendele.hospital.models.factories;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.uha40.mendele.hospital.models.HospitalService;

public class RandomHospitalServiceFactory {
    private final Faker mFaker;

    public RandomHospitalServiceFactory() {
        mFaker = new Faker();
    }

    public HospitalService create () {
        String randomServiceName = mFaker.company().name();
        long serviceId = new Date().getTime();

        HospitalService service = new HospitalService(randomServiceName);
        service.setId(serviceId);

        return service;
    }

    public List<HospitalService> createMany (int serviceCount) {
        List<HospitalService> services = new ArrayList<>();

        for (int i = 0; i < serviceCount; i++) {
            services.add(this.create());
        }

        return services;
    }

    public static RandomHospitalServiceFactory getInstance() {
        return new RandomHospitalServiceFactory();
    }
}
