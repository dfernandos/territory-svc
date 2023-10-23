package com.mapeando.territory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Coordinates {

    private String id;
    private double latitude;
    private double longitude;
    private String name;

}
