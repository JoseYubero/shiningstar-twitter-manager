package com.shiningstar.twitter.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@ToString
public class Hashtag implements Serializable {

    private static final long serialVersionUID = -1495806720533136813L;

    private static final String MUST_BE_NOT_NULL = "must be not null";

    @JsonCreator
    public Hashtag(@JsonProperty("indices") final int[] indices,
                   @JsonProperty("text") final String text) {
        Assert.notNull(indices, "indices " + MUST_BE_NOT_NULL);
        Assert.notNull(text, "screen_name " + MUST_BE_NOT_NULL);

        this.indices = indices;
        this.text = text;
    }

    @NonNull
    private int[] indices;

    @NonNull
    private String text;
}
