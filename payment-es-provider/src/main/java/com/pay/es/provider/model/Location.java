package com.pay.es.provider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    /**
     * 经度
     */
    private Double lon;
    /**
     * 纬度
     */
    private Double lat;

}
