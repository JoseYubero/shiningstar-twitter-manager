package com.shiningstar.twitter.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Entities implements Serializable {

    private static final long serialVersionUID = 5432166805792252821L;

    @JsonCreator
    public Entities() {
        this.hashtags = new ArrayList<>();
    }

    private List<Hashtag> hashtags;
}
