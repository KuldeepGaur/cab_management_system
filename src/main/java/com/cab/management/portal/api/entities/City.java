package com.cab.management.portal.api.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cities", uniqueConstraints = { @UniqueConstraint(columnNames = { "city" }) })
public class City {

    @Id
    @Column(name = "city")
    String city;


    @Column(name="service_status")
    char serviceStatus;
}
