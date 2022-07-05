package com.cab.management.portal.api.entrypoint;

import com.cab.management.portal.api.dto.CityDTO;
import com.cab.management.portal.api.entities.City;
import com.cab.management.portal.api.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

@RestController("CityOnBoardingEntryPoint")
@RequestMapping(path = "/v1/city")
public class CityOnBoardingEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityOnBoardingEntryPoint.class);

    @Autowired
    CityService cityService;

    @GetMapping(value = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> getCityDetails(@PathVariable("cityId") String cityId){
        try {
            return new ResponseEntity<>(cityService.getCity(cityId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<City>> filterCities(){
        List<City> city = cityService.filterCities();
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PostMapping(value = "/onboard", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity onboardCity(@RequestBody CityDTO cityOnBoardingRequest){
        try {
            cityService.onboard(cityOnBoardingRequest);
            return new ResponseEntity("Onboarded new city : " + cityOnBoardingRequest.getCity().toLowerCase(Locale.ROOT), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Issue with city Onboard - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/deboard/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deboardCity(@PathVariable("cityId") String cityId){
        try {
            cityService.deboard(cityId);
            return new ResponseEntity("De-boarded new city : " + cityId.toLowerCase(Locale.ROOT), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Issue with city De-Boarding - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
