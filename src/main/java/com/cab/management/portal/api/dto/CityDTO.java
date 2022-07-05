package com.cab.management.portal.api.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CityDTO {
    @NonNull
    String city;

    @NonNull
    char serviceActive;

    public String getCity(){
        return city;
    }

    public char getServiceStatus() {
        return serviceActive;
    }
}
