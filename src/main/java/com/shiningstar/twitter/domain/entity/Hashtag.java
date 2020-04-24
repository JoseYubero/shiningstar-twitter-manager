package com.shiningstar.twitter.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "HASHTAG")
public class Hashtag implements Serializable {

    private static final long serialVersionUID = 934121942418874694L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @NotNull
    @Column(name = "INDEX_1")
    private Integer index_1;

    @NotNull
    @Column(name = "INDEX_2")
    private Integer index_2;

    @NotNull
    @Column(name = "TEXT")
    private String text;

    @JoinColumn(name = "TWEET_ID", referencedColumnName = "ID")
    @ManyToOne()
    private Tweet tweet;
}
