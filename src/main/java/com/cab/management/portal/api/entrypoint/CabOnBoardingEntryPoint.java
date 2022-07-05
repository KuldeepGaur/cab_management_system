package com.cab.management.portal.api.entrypoint;

import com.cab.management.portal.api.dto.CabDTO;
import com.cab.management.portal.api.entities.Cab;
import com.cab.management.portal.api.service.CabService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController("CabOnBoardingEntryPoint")
@RequestMapping(path = "/v1/cab")
public class CabOnBoardingEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CabOnBoardingEntryPoint.class);

    @Autowired
    CabService cabService;

    @GetMapping(value = "/{cabId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cab> getCabDetails(@PathVariable("cabId") String cabId){
        try {
            return new ResponseEntity<>(cabService.getCab(cabId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cab>> filterCabs(){
        List<Cab> cab = cabService.filterCabs();
        return new ResponseEntity<>(cab, HttpStatus.OK);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity onboardCab(@RequestBody CabDTO cabOnBoardingRequest){
        try {
            cabService.onboard(cabOnBoardingRequest);
            return new ResponseEntity("Registered new cab : " + cabOnBoardingRequest.getCabNumber().toUpperCase(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Issue with cab registration - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/deboard/{cabId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deboardCab(@PathVariable("cabId") String cabId){
        try {
            cabService.deboard(cabId);
            return new ResponseEntity("De-boarded new cab : " + cabId.toLowerCase(Locale.ROOT), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Issue with cab De-Boarding - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
