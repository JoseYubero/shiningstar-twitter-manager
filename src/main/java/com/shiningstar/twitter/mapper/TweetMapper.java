package com.shiningstar.twitter.mapper;

import com.shiningstar.twitter.domain.model.Place;
import com.shiningstar.twitter.domain.model.Tweet;
import com.shiningstar.twitter.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigInteger;

@Component
public class TweetMapper {
    public com.shiningstar.twitter.domain.entity.Tweet convertTweetModelToEntity(final Tweet modelTweet) {
        com.shiningstar.twitter.domain.entity.Tweet entityTweet = new com.shiningstar.twitter.domain.entity.Tweet();
        entityTweet.setId(modelTweet.getIdStr());
        entityTweet.setText(modelTweet.getText());
        entityTweet.setLang(modelTweet.getLang());
        entityTweet.setValidated(Boolean.FALSE);
        entityTweet.setPlace(convertPlaceModelToEntity(modelTweet.getPlace()));
        entityTweet.setUser(convertUserModelToEntity(modelTweet.getUser()));
        return entityTweet;
    }

    private com.shiningstar.twitter.domain.entity.Place convertPlaceModelToEntity(final Place modelPlace) {
        com.shiningstar.twitter.domain.entity.Place entityPlace = new com.shiningstar.twitter.domain.entity.Place();
        entityPlace.setId(modelPlace.getId());
        entityPlace.setName(modelPlace.getName());

        if (!StringUtils.isEmpty(modelPlace.getPlaceType())) {
            entityPlace.setPlaceType(modelPlace.getPlaceType());
        }
        if (!StringUtils.isEmpty(modelPlace.getFullName())) {
            entityPlace.setFullName(modelPlace.getFullName());
        }
        if (!StringUtils.isEmpty(modelPlace.getCountry())) {
            entityPlace.setCountry(modelPlace.getCountry());
        }
        if (!StringUtils.isEmpty(modelPlace.getCountryCode())) {
            entityPlace.setCountryCode(modelPlace.getCountryCode());
        }
        return entityPlace;
    }

    private com.shiningstar.twitter.domain.entity.User convertUserModelToEntity(final User modelUser) {
        com.shiningstar.twitter.domain.entity.User entityUser = new com.shiningstar.twitter.domain.entity.User();
        entityUser.setId(modelUser.getIdStr());
        entityUser.setName(modelUser.getName());
        entityUser.setScreenName(modelUser.getScreenName());
        if (!StringUtils.isEmpty(modelUser.getLocation())) {
            entityUser.setLocation(modelUser.getLocation());
        }
        entityUser.setFollowersCount(modelUser.getFollowersCount());
        return entityUser;
    }

    public Tweet convertTweetEntityToModel(final com.shiningstar.twitter.domain.entity.Tweet entityTweet) {
        Tweet modelTweet = new Tweet(new BigInteger(entityTweet.getId()),
                entityTweet.getId(),entityTweet.getText(),
                convertUserEntityToModel(entityTweet.getUser()));
        modelTweet.setPlace(convertPlaceEntityToModel(entityTweet.getPlace()));
        modelTweet.setValidated(entityTweet.getValidated());
        modelTweet.setLang(entityTweet.getLang());
        return modelTweet;
    }

    private User convertUserEntityToModel(final com.shiningstar.twitter.domain.entity.User entityUser) {
        User modelUser = new User(new BigInteger(entityUser.getId()), entityUser.getId(), entityUser.getName(),
                entityUser.getScreenName(), entityUser.getLocation(), entityUser.getFollowersCount());
        modelUser.setLocation(entityUser.getLocation());
        return modelUser;
    }

    private Place convertPlaceEntityToModel(final com.shiningstar.twitter.domain.entity.Place entityPlace) {
        Place modelPlace = new Place(entityPlace.getId(), entityPlace.getName());
        modelPlace.setPlaceType(entityPlace.getPlaceType());
        modelPlace.setFullName(entityPlace.getFullName());
        modelPlace.setCountryCode(entityPlace.getCountryCode());
        modelPlace.setCountry(entityPlace.getCountry());
        return modelPlace;
    }
}