package com.pay.es.provider.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoMap {

    private String id;
    /**
     * 名字
     */
    private String name;

    /**
     * 城市
     */
    private String city;
    /**
     * 位置
     */
    private Location location;
}
