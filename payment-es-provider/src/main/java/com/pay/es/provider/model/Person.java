package com.pay.es.provider.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {


    @JsonIgnore
    private Integer id;
    private String name;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;


}
