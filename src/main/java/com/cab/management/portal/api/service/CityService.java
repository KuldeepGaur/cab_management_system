package com.cab.management.portal.api.service;

import com.cab.management.portal.api.dto.CityDTO;
import com.cab.management.portal.api.entities.City;
import com.cab.management.portal.api.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    CityRepository cityRepository;
    public void onboard(CityDTO cityOnBoardingRequest) throws Exception {
        City city = new City();
        try {
            city.setCity(cityOnBoardingRequest.getCity().toLowerCase());
            city.setServiceStatus('Y');

            if (!cityRepository.existsById(city.getCity())) {
                cityRepository.save(city);
            } else {
                throw new Exception("City : " + city.getCity() + " is already onboarded");
            }
        } catch (NullPointerException npe) {
            throw new Exception(npe.getMessage());
        }
    }

    public void deboard(String cityName) throws Exception {
        City city = new City();
        try {
            city.setCity(cityName.toLowerCase());
            city.setServiceStatus('N');

            if (!cityRepository.existsById(city.getCity())) {
                throw new Exception("City : " + city.getCity() + " not onboarded earlier");
            } else {
                cityRepository.save(city);
            }
        } catch (NullPointerException npe) {
            throw new Exception(npe.getMessage());
        }
    }
    public City getCity(String cityName) throws Exception {
        cityName = cityName.toLowerCase();
        if (cityRepository.existsById(cityName)) {
            Optional<City> city = cityRepository.findById(cityName);
            return city.get();
        } else {
            throw new Exception("no city onboarded with cityName : " + cityName);
        }
    }

    public List<City> filterCities() {
        return (List<City>) cityRepository.findAll();
    }

    public boolean isCityActive(String city){
        List<City> cityWithStatus =  cityRepository.findByCityAndServiceStatus(city,'Y');
        return cityWithStatus!=null && cityWithStatus.size()>0;
    }
}
