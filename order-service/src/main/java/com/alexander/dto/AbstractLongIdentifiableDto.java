package com.alexander.dto;

import com.alexander.model.Identifiable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractLongIdentifiableDto implements Identifiable<Long> {

    private Long id;

}
