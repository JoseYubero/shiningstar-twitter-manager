package com.shiningstar.twitter.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@ToString
public class Place implements Serializable {

    private static final String MUST_BE_NOT_NULL = "must be not null";

    @JsonCreator
    public Place(@JsonProperty("id") final String id,
                 @JsonProperty("name") final String name) {
        Assert.notNull(id, "name " + MUST_BE_NOT_NULL);
        Assert.notNull(name, "screen_name " + MUST_BE_NOT_NULL);

        this.id = id;
        this.name = name;
    }

    @NonNull
    private String id;

    @Setter
    private String placeType;

    @NonNull
    private String name;

    @Setter
    private String fullName;

    @Setter
    private String countryCode;

    @Setter
    private String country;
}
