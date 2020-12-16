package com.pay.es.provider.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Canal {
    @JsonIgnore
    private Long id;
    private String name;
    private String sex;
    private Integer age;
    private Double amount;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date occurTime;
}
