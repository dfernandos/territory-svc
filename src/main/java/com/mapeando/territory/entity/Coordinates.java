package com.mapeando.territory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
