package com.medulasales.products.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;

/**
 * The ParameterizedInput class
 *
 * @author Junior Mbe
 * @version 1.0
 * @since 26/06/2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParameterizedInput<T extends Serializable> {

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public static enum Issue {
        SUCCED,
        FAIL,
        EXCEPTION
    }
    private T value;
    private Issue issue;
    private String exceptionClass;
    private String description;
}
