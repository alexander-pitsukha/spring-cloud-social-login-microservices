package com.alexander.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class ProductDto extends AbstractLongIdentifiableDto {

    @NotBlank
    private String name;

    @NotBlank
    private String version;

    private String edition;

    @NotBlank
    private String description;

    private LocalDate validFrom;

}
