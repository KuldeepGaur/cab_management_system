package com.cab.management.portal.api.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cabs")
@Setter
@Getter
@NoArgsConstructor
@Data
public class Cab {
    @Id
    @Column(name = "cab_number")
    String cabNumber;

    @Column(name="cab_city")
    String city;

    @Column(name="cab_state")
    String cabState;

    @Column(name="service_active")
    char serviceState;

    @Column(name="enrollment_date")
    Date enrollment_date;

    @Column(name="last_updated_on")
    Date last_updated_on;
}