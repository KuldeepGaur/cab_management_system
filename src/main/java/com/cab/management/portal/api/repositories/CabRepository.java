package com.cab.management.portal.api.repositories;

import com.cab.management.portal.api.entities.Cab;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CabRepository extends CrudRepository<Cab, String> {
    List<Cab> findByCity(String city);
    List<Cab> findByCityAndCabStateAndServiceState(String city,String cabState, char serviceState);

}
