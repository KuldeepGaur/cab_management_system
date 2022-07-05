package com.cab.management.portal.api.entrypoint;

import com.cab.management.portal.api.common.exceptions.CabServiceUnavailableException;
import com.cab.management.portal.api.common.exceptions.NoCabsAvailableException;
import com.cab.management.portal.api.dto.CabDTO;
import com.cab.management.portal.api.dto.ReleaseDTO;
import com.cab.management.portal.api.entities.Cab;
import com.cab.management.portal.api.service.BookingService;
import com.cab.management.portal.api.service.CabService;
import com.cab.management.portal.api.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController("BookingEntryPoint")
@RequestMapping(path = "/v1")
public class BookingEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingEntryPoint.class);

    @Autowired
    CabService cabService;

    @Autowired
    CityService cityService;

    @Autowired
    BookingService bookingService;

    @PostMapping(value = "/book/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity bookCab(@PathVariable("cityId") String cityId) throws Exception {
        try {
            cityId = cityId.toLowerCase();
            if (!cityService.isCityActive(cityId)){
                throw new CabServiceUnavailableException("Cab service is not available in city.");
            }

            List<Cab> idleCabs = cabService.filterActiveCabsByCity(cityId);
            Cab cab = null;
            if(idleCabs.size()>0){
                cab = idleCabs.stream().min(Comparator.comparing(Cab::getLast_updated_on)).get();
                bookingService.bookCab(cab);
            } else {
                throw new NoCabsAvailableException("No cabs are available this time.");
            }

            return new ResponseEntity("Cab # : " + cab.getCabNumber() + " booked", HttpStatus.OK);
        } catch (NoCabsAvailableException | CabServiceUnavailableException e) {
            return new ResponseEntity("Issue while Cab Booking - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/release", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity releaseCab(@RequestBody ReleaseDTO releaseDTO){
        try {
            Cab cabDetails = cabService.getCab(releaseDTO.getCabNumber());
            bookingService.releaseCab(cabDetails.getCabNumber(), releaseDTO.getCity());

            return new ResponseEntity("Cab released. Thanks for commuting with us.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Issue with releasing the cab - " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
