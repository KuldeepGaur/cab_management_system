package com.cab.management.portal.api.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CabDTO {
    @NonNull
    String cabNumber;

    @NonNull
    String city;
}
