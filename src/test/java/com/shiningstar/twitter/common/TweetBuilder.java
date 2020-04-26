package com.shiningstar.twitter.common;

import com.shiningstar.twitter.domain.model.Entities;
import com.shiningstar.twitter.domain.model.Hashtag;
import com.shiningstar.twitter.domain.model.Place;
import com.shiningstar.twitter.domain.model.Tweet;
import com.shiningstar.twitter.domain.model.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TweetBuilder {

    public static final String USER_ID = "100";
    public static final String USER_NAME = "Peter Nolas";
    public static final String USER_SCREEN_NAME = "Petnola";
    public static final String USER_LOCATION = "Albacete";
    public static final int USER_FOLLOWERS = 2500;
    public static final String TWEET_ID = "1234567890";
    public static final String TWEET_TEXT = "Un texto de prueba";
    public static final String TWEET_LANG = "es";
    public static final String PLACE_ID = "28100";
    public static final String PLACE_NAME = "Albacete";
    public static final String PLACE_TYPE = "city";
    public static final String PLACE_FULL_NAME = "Albacete, España";
    public static final String PLACE_COUNTRY = "España";
    public static final String PLACE_COUNTRY_CODE = "es";
    public static final String HASHTAG_TEXT_1 = "hashtag_1";
    public static final String HASHTAG_TEXT_2 = "hashtag_1";
    public static final String HASHTAG_TEXT_3 = "hashtag_2";

    public static Tweet buildModelTweet() {
        User user = new User(new BigInteger(USER_ID), USER_ID, USER_NAME, USER_SCREEN_NAME, USER_LOCATION, USER_FOLLOWERS);
        Tweet tweet = new Tweet(new BigInteger(TWEET_ID), TWEET_ID, TWEET_TEXT, user);
        tweet.setValidated(false);
        tweet.setLang(TWEET_LANG);
        tweet.setPlace(buildModelPlace());
        tweet.setEntities(new Entities());
        tweet.getEntities().setHashtags(buildModelHashTags());
        return tweet;
    }

    private static Place buildModelPlace() {
        Place place = new Place(PLACE_ID, PLACE_NAME);
        place.setPlaceType(PLACE_TYPE);
        place.setFullName(PLACE_FULL_NAME);
        place.setCountry(PLACE_COUNTRY);
        place.setCountryCode(PLACE_COUNTRY_CODE);
        return place;
    }

    private static List<Hashtag> buildModelHashTags() {
        List<Hashtag> hashtagList = new ArrayList<>();
        hashtagList.add(buildModelHashtag(1, 3, HASHTAG_TEXT_1));
        hashtagList.add(buildModelHashtag(4, 8, HASHTAG_TEXT_2));
        hashtagList.add(buildModelHashtag(10, 14, HASHTAG_TEXT_3));
        return hashtagList;
    }

    private static Hashtag buildModelHashtag(final int index1, final int index2, final String text) {
        int[] indices = new int[2];
        indices[0] = index1;
        indices[1] = index2;
        return new Hashtag(indices, text);
    }

    public static com.shiningstar.twitter.domain.entity.Tweet buildEntityTweet() {
        com.shiningstar.twitter.domain.entity.Tweet tweet = new com.shiningstar.twitter.domain.entity.Tweet();
        tweet.setId(TWEET_ID);
        tweet.setText(TWEET_TEXT);
        tweet.setLang(TWEET_LANG);
        tweet.setValidated(Boolean.FALSE);
        tweet.setUser(buildEntityUser());
        tweet.setPlace(buildEntityPlace());
        tweet.setHashtags(buildEntityHashTags(tweet));
        return tweet;
    }

    private static com.shiningstar.twitter.domain.entity.User buildEntityUser() {
        com.shiningstar.twitter.domain.entity.User user = new com.shiningstar.twitter.domain.entity.User();
        user.setId(USER_ID);
        user.setName(USER_NAME);
        user.setScreenName(USER_SCREEN_NAME);
        user.setLocation(USER_LOCATION);
        user.setFollowersCount(USER_FOLLOWERS);
        return user;
    }

    private static com.shiningstar.twitter.domain.entity.Place buildEntityPlace() {
        com.shiningstar.twitter.domain.entity.Place place = new com.shiningstar.twitter.domain.entity.Place();
        place.setId(PLACE_ID);
        place.setName(PLACE_NAME);
        place.setPlaceType(PLACE_TYPE);
        place.setFullName(PLACE_FULL_NAME);
        place.setCountry(PLACE_COUNTRY);
        place.setCountryCode(PLACE_COUNTRY_CODE);
        return place;
    }

    private static List<com.shiningstar.twitter.domain.entity.Hashtag> buildEntityHashTags(final com.shiningstar.twitter.domain.entity.Tweet tweet) {
        List<com.shiningstar.twitter.domain.entity.Hashtag> hashtagList = new ArrayList<>();
        hashtagList.add(buildEntityHashtag(1, 3, HASHTAG_TEXT_1, tweet));
        hashtagList.add(buildEntityHashtag(4, 8, HASHTAG_TEXT_2, tweet));
        hashtagList.add(buildEntityHashtag(10, 14, HASHTAG_TEXT_3, tweet));
        return hashtagList;
    }

    private static com.shiningstar.twitter.domain.entity.Hashtag buildEntityHashtag(final int index1,
                                                                                    final int index2,
                                                                                    final String text,
                                                                                    final com.shiningstar.twitter.domain.entity.Tweet tweet) {
        com.shiningstar.twitter.domain.entity.Hashtag hashtag = new com.shiningstar.twitter.domain.entity.Hashtag();
        hashtag.setIndex_1(index1);
        hashtag.setIndex_2(index2);
        hashtag.setText(text);
        hashtag.setTweet(tweet);
        return hashtag;
    }
}
