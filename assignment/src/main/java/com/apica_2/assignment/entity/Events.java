package com.apica_2.assignment.entity;

import jakarta.persistence.Entity;
import lombok.Data;


@Entity
@Data
public class Events extends BaseEntity{
    private String eventName;
    private String userId;

}
