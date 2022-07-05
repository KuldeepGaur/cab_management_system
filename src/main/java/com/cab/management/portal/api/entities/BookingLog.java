package com.cab.management.portal.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cab_history")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingLog implements Serializable {
    @Id
    @Column(name = "cab_number")
    String cabNumber;

    @Column(name="cab_city")
    String city;

    @Column(name="cab_state")
    String cabState;

    @Column(name="date_time")
    Date dateTime;
}
