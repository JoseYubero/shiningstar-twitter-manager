package com.shiningstar.twitter.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "PLACE")
public class Place implements Serializable {

    private static final long serialVersionUID = -2033769955890774260L;

    @Id
    @NotNull
    @Column(name = "ID")
    private String id;

    @Column(name = "PLACE_TYPE")
    private String placeType;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "COUNTRY")
    private String country;
}
