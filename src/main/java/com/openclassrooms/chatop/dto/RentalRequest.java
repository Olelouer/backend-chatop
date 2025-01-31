package com.openclassrooms.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest {
    /** Name of the rental property */
    private String name;

    /** Surface area of the rental in square meters */
    private Integer surface;

    /** Price of the rental */
    private Integer price;

    /** URL or path to the rental's picture */
    private MultipartFile picture;

    /** Detailed description of the rental */
    private String description;
}
