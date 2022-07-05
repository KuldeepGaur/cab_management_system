package com.cab.management.portal.api.service;

import com.cab.management.portal.api.common.exceptions.CabOnboardingException;
import com.cab.management.portal.api.dto.CabDTO;
import com.cab.management.portal.api.entities.Cab;
import com.cab.management.portal.api.repositories.CabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CabService {
    @Autowired
    CabRepository cabRepository;
    public void onboard(CabDTO cabOnBoardingRequest) throws Exception {
        Cab cab = new Cab();
        try {
            cab.setCabNumber(cabOnBoardingRequest.getCabNumber().toUpperCase());
            cab.setCabState("IDLE");
            cab.setCity(cabOnBoardingRequest.getCity().toLowerCase());
            cab.setEnrollment_date(new Date());
            cab.setServiceState('Y');
            cab.setLast_updated_on(new Date());

            if (!cabRepository.existsById(cab.getCabNumber().toUpperCase())) {
                cabRepository.save(cab);
            } else {
                throw new CabOnboardingException("Cab : " + cab.getCabNumber() + " is already onboarded");
            }
        } catch (NullPointerException npe) {
            throw new Exception(npe.getMessage());
        }
    }

    public void deboard(String cabName) throws Exception {
        Cab cab = new Cab();
        try {
            cab.setCabState(cabName.toUpperCase());
            cab.setServiceState('N');
            cab.setLast_updated_on(new Date());

            if (!cabRepository.existsById(cab.getCabNumber().toUpperCase())) {
                throw new CabOnboardingException("Cab : " + cab.getCabNumber() + " not onboarded earlier");
            } else {
                Cab previous_details = cabRepository.findById(cab.getCabNumber().toUpperCase()).get();

                previous_details.setServiceState('N');
                cabRepository.save(previous_details);
            }
        } catch (NullPointerException npe) {
            throw new CabOnboardingException(npe.getMessage());
        }
    }
    public Cab getCab(String cabName) throws Exception {
        cabName = cabName.toUpperCase();
        if (cabRepository.existsById(cabName)) {
            Optional<Cab> cab = cabRepository.findById(cabName);
            return cab.get();
        } else {
            throw new CabOnboardingException("No cab was onboarded with cabName : " + cabName);
        }
    }

    public List<Cab> filterCabs() {
        return (List<Cab>) cabRepository.findAll();
    }

    public List<Cab> filterActiveCabsByCity(String city){
        return (List<Cab>) cabRepository.findByCityAndCabStateAndServiceState(city, "IDLE", 'Y');
    }
}
