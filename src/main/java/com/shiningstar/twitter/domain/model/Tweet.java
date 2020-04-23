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
public class Tweet implements Serializable {

    private static final long serialVersionUID = -6456760554696198105L;

    private static final String MUST_BE_NOT_NULL = "must be not null";

    @JsonCreator
    public Tweet(@JsonProperty("id") final BigInteger id,
                 @JsonProperty("id_str") final String idStr,
                 @JsonProperty("text") final String text,
                 @JsonProperty("user") final User user) {
        Assert.notNull(id, "id " + MUST_BE_NOT_NULL);
        Assert.notNull(idStr, "id_str " + MUST_BE_NOT_NULL);
        Assert.notNull(text, "text " + MUST_BE_NOT_NULL);
        Assert.notNull(text, "user " + MUST_BE_NOT_NULL);

        this.id = id;
        this.idStr = idStr;
        this.text = text;
        this.user = user;
    }

    @NonNull
    private BigInteger id;

    @NonNull
    private String idStr;

    @NonNull
    private String text;

    @NonNull
    private User user;

    @Setter
    private String lang;

    @Setter
    private Place place;

    @Setter
    private Boolean validated;
}
