package com.irsan.springbootbackend.utils;

import lombok.*;

import java.io.Serializable;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorModel implements Serializable {

    private static final long serialVersionUID = 1275049606157249289L;
    private String field;
    private String message;

}
