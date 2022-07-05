package com.cab.management.portal.api.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReleaseDTO {
    @NonNull
    String cabNumber;

    @NonNull
    String city;
}
