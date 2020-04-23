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
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = -6961088369661008360L;

    @Id
    @NotNull
    @Column(name = "ID")
    private String id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "SCREEN_NAME")
    private String screenName;

    @Column(name = "LOCATION")
    private String location;

    @NotNull
    @Column(name = "FOLLOWERS_COUNT")
    private Integer followersCount;
}
