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
import java.math.BigInteger;

@Getter
@EqualsAndHashCode
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = -6961088369661008360L;

    private static final String MUST_BE_NOT_NULL = "must be not null";

    @JsonCreator
    public User(@JsonProperty("id") final BigInteger id,
                @JsonProperty("id_str") final String idStr,
                @JsonProperty("name") final String name,
                @JsonProperty("screen_name") final String screenName,
                @JsonProperty("location") final String location,
                @JsonProperty("followers_count") final Integer followersCount) {
        Assert.notNull(idStr, "id_str " + MUST_BE_NOT_NULL);
        Assert.notNull(idStr, "name " + MUST_BE_NOT_NULL);
        Assert.notNull(name, "screen_name " + MUST_BE_NOT_NULL);
        Assert.notNull(screenName, "screenName " + MUST_BE_NOT_NULL);
        Assert.notNull(followersCount, "followers_count " + MUST_BE_NOT_NULL);

        this.id = id;
        this.idStr = idStr;
        this.name = name;
        this.screenName = screenName;
        this.location = location;
        this.followersCount = followersCount;
    }

    @NonNull
    private BigInteger id;

    @NonNull
    private String idStr;

    @NonNull
    private String name;

    @NonNull
    private String screenName;

    @Setter
    private String location;

    @NonNull
    private Integer followersCount;
}
