package com.cab.management.portal.api.repositories;

import com.cab.management.portal.api.entities.BookingLog;
import org.springframework.data.repository.CrudRepository;
public interface BookingLogRepository extends CrudRepository<BookingLog, String> {
}
