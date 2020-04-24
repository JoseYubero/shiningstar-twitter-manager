package com.shiningstar.twitter.domain.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class HashtagMoreUsed implements Serializable {

    private static final long serialVersionUID = 897199072203431551L;

    private String text;
    private Long count;

    public HashtagMoreUsed() {}

    public HashtagMoreUsed(final String text, final Long count) {
        this.text = text;
        this.count = count;
    }
}
