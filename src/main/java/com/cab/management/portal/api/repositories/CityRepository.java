package com.cab.management.portal.api.repositories;

import com.cab.management.portal.api.entities.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, String> {
    List<City> findByCityAndServiceStatus(String city, char serviceStatus);
}
