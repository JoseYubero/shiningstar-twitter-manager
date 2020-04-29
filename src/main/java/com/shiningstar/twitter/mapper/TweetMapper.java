package com.shiningstar.twitter.mapper;

import com.shiningstar.twitter.domain.model.Entities;
import com.shiningstar.twitter.domain.model.Hashtag;
import com.shiningstar.twitter.domain.model.Place;
import com.shiningstar.twitter.domain.model.Tweet;
import com.shiningstar.twitter.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        if(modelTweet.getEntities() != null) {
            entityTweet.setHashtags(modelTweet.getEntities().getHashtags().stream().map(hashtag -> convertHashTagModelToEntity(hashtag)).collect(Collectors.toList()));
        }
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
    private com.shiningstar.twitter.domain.entity.Hashtag convertHashTagModelToEntity(final Hashtag modelHashtag) {
        com.shiningstar.twitter.domain.entity.Hashtag entityHashtag = new com.shiningstar.twitter.domain.entity.Hashtag();
        entityHashtag.setIndex_1(modelHashtag.getIndices()[0]);
        entityHashtag.setIndex_2(modelHashtag.getIndices()[1]);
        entityHashtag.setText(modelHashtag.getText());
        return entityHashtag;
    }

    public Tweet convertTweetEntityToModel(final com.shiningstar.twitter.domain.entity.Tweet entityTweet) {
        Tweet modelTweet = new Tweet(new BigInteger(entityTweet.getId()),
                entityTweet.getId(),entityTweet.getText(),
                convertUserEntityToModel(entityTweet.getUser()));
        modelTweet.setPlace(convertPlaceEntityToModel(entityTweet.getPlace()));
        modelTweet.setValidated(entityTweet.getValidated());
        modelTweet.setLang(entityTweet.getLang());
        modelTweet.setEntities(new Entities());
        modelTweet.getEntities().setHashtags(entityTweet.getHashtags().stream().map(hashtag -> convertHashTagEntityToModel(hashtag)).collect(Collectors.toList()));
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

    private Hashtag convertHashTagEntityToModel(final com.shiningstar.twitter.domain.entity.Hashtag entityHashtag) {
        int[] indices = new int[2];
        indices[0] = entityHashtag.getIndex_1();
        indices[1] = entityHashtag.getIndex_2();
        return new Hashtag(indices, entityHashtag.getText());
    }

    public com.shiningstar.twitter.domain.entity.Tweet convertStatusToEntity(final Status status) {
        com.shiningstar.twitter.domain.entity.Tweet entityTweet = new com.shiningstar.twitter.domain.entity.Tweet();
        entityTweet.setId(String.valueOf(status.getId()));
        entityTweet.setText(status.getText());
        entityTweet.setLang(status.getLang());
        entityTweet.setValidated(Boolean.FALSE);
        entityTweet.setPlace(convertPlaceToEntity(status.getPlace()));
        entityTweet.setUser(convertUserToEntity(status.getUser()));
        if(status.getHashtagEntities() != null) {
            entityTweet.setHashtags(Arrays.stream(status.getHashtagEntities()).map(hashtag -> convertHashTagToEntity(hashtag)).collect(Collectors.toList()));
        }
        return entityTweet;
    }

    private com.shiningstar.twitter.domain.entity.Place convertPlaceToEntity(final twitter4j.Place place) {
        com.shiningstar.twitter.domain.entity.Place entityPlace = new com.shiningstar.twitter.domain.entity.Place();
        entityPlace.setId(place.getId());
        entityPlace.setName(place.getName());

        if (!StringUtils.isEmpty(place.getPlaceType())) {
            entityPlace.setPlaceType(place.getPlaceType());
        }
        if (!StringUtils.isEmpty(place.getFullName())) {
            entityPlace.setFullName(place.getFullName());
        }
        if (!StringUtils.isEmpty(place.getCountry())) {
            entityPlace.setCountry(place.getCountry());
        }
        if (!StringUtils.isEmpty(place.getCountryCode())) {
            entityPlace.setCountryCode(place.getCountryCode());
        }
        return entityPlace;
    }

    private com.shiningstar.twitter.domain.entity.User convertUserToEntity(final twitter4j.User user) {
        com.shiningstar.twitter.domain.entity.User entityUser = new com.shiningstar.twitter.domain.entity.User();
        entityUser.setId(String.valueOf(user.getId()));
        entityUser.setName(user.getName());
        entityUser.setScreenName(user.getScreenName());
        if (!StringUtils.isEmpty(user.getLocation())) {
            entityUser.setLocation(user.getLocation());
        }
        entityUser.setFollowersCount(user.getFollowersCount());
        return entityUser;
    }
    private com.shiningstar.twitter.domain.entity.Hashtag convertHashTagToEntity(final HashtagEntity hashtagEntity) {
        com.shiningstar.twitter.domain.entity.Hashtag entityHashtag = new com.shiningstar.twitter.domain.entity.Hashtag();
        entityHashtag.setIndex_1(hashtagEntity.getStart());
        entityHashtag.setIndex_2(hashtagEntity.getEnd());
        entityHashtag.setText(hashtagEntity.getText());
        return entityHashtag;
    }
}