package com.shiningstar.twitter.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "TWEET")
public class Tweet implements Serializable {

    private static final long serialVersionUID = -3190507810302179588L;

    @Id
    @NotNull
    @Column(name = "ID")
    private String id;

    @NotNull
    @Column(name = "TEXT")
    private String text;

    @NotNull
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "PLACE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Place place;

    @Column(name = "LANG")
    private String lang;

    @OneToMany(mappedBy = "tweet")
    private List<Hashtag> hashtags = new ArrayList<>();

    @NotNull
    @Column(name = "VALIDATED")
    private Boolean validated;
}
