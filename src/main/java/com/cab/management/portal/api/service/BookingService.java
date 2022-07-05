package com.cab.management.portal.api.service;

import com.cab.management.portal.api.entities.BookingLog;
import com.cab.management.portal.api.entities.Cab;
import com.cab.management.portal.api.entities.City;
import com.cab.management.portal.api.repositories.BookingLogRepository;
import com.cab.management.portal.api.repositories.CabRepository;
import com.cab.management.portal.api.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    CityRepository cityRepository;

    @Autowired
    CabRepository cabRepository;

    @Autowired
    BookingLogRepository bookingLogRepository;

    public void bookCab(Cab cab) throws Exception {
        // set the cab status on trip
        // set cab location as in transit
        cab.setCabState("ON_TRIP");
        cab.setCity("ON_TRIP");
        cab.setLast_updated_on(new Date());

        cabRepository.save(cab);
        // set the cab history
        BookingLog bookingLog = new BookingLog();
        bookingLog.setCabNumber(cab.getCabNumber());
        bookingLog.setCabNumber(cab.getCity());
        bookingLog.setCabNumber(cab.getCabState());
        bookingLog.setDateTime(new Date());

        bookingLogRepository.save(bookingLog);

    }

    public void releaseCab(String cabNum, String city) throws Exception {
        // set the cab status idle
        // set cab location as city
        Optional<Cab> cabResult = cabRepository.findById(cabNum);
        if(!cabResult.isPresent()){
            throw new Exception("cab not available");
        }

        Cab cab = cabResult.get();

        cab.setCabState("IDLE");
        cab.setCity(city.toLowerCase());
        cab.setLast_updated_on(new Date());

        cabRepository.save(cab);

        // set the cab history
        BookingLog bookingLog = new BookingLog();
        bookingLog.setCabNumber(cab.getCabNumber());
        bookingLog.setCabNumber(cab.getCity());
        bookingLog.setCabNumber(cab.getCabState());
        bookingLog.setDateTime(new Date());

        bookingLogRepository.save(bookingLog);
    }
}
