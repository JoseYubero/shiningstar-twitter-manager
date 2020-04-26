package com.shiningstar.twitter.mapper;

import com.shiningstar.twitter.common.TweetBuilder;
import com.shiningstar.twitter.domain.model.Entities;
import com.shiningstar.twitter.domain.model.Hashtag;
import com.shiningstar.twitter.domain.model.Place;
import com.shiningstar.twitter.domain.model.Tweet;
import com.shiningstar.twitter.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TwitterMapperTest {

/*
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
    public static final String HASHTAG_TEXT_1 = "uno";
    public static final String HASHTAG_TEXT_2 = "dos";
    public static final String HASHTAG_TEXT_3 = "tres";
*/

    @InjectMocks
    private TweetMapper tweetMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertTweetModelToEntity() {
        com.shiningstar.twitter.domain.entity.Tweet entityTweet = tweetMapper.convertTweetModelToEntity(TweetBuilder.buildModelTweet());
        Assertions.assertAll("entityTweet",
                () -> Assertions.assertEquals(TweetBuilder.TWEET_ID, entityTweet.getId(), "Entity tweet id error"),
                () -> Assertions.assertEquals(TweetBuilder.TWEET_TEXT, entityTweet.getText(), "Entity tweet text error"),
                () -> Assertions.assertEquals(TweetBuilder.TWEET_LANG, entityTweet.getLang(), "Entity tweet lang error"),
                () -> Assertions.assertEquals(Boolean.FALSE, entityTweet.getValidated(), "Entity tweet validated error")
        );
        Assertions.assertAll("entityTweet place",
                () -> Assertions.assertEquals(TweetBuilder.PLACE_ID, entityTweet.getPlace().getId(), "Entity tweet place_id error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_TYPE, entityTweet.getPlace().getPlaceType(), "Entity tweet place_type error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_NAME, entityTweet.getPlace().getName(), "Entity tweet place_name error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_FULL_NAME, entityTweet.getPlace().getFullName(), "Entity tweet place_fullName error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_COUNTRY, entityTweet.getPlace().getCountry(), "Entity tweet place_country error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_COUNTRY_CODE, entityTweet.getPlace().getCountryCode(), "Entity tweet place_countryCode error")
        );
        Assertions.assertAll("entityTweet user",
                () -> Assertions.assertEquals(TweetBuilder.USER_ID, entityTweet.getUser().getId(), "Entity tweet user_id error"),
                () -> Assertions.assertEquals(TweetBuilder.USER_NAME, entityTweet.getUser().getName(), "Entity tweet user_name error"),
                () -> Assertions.assertEquals(TweetBuilder.USER_SCREEN_NAME, entityTweet.getUser().getScreenName(), "Entity tweet user_screenName error"),
                () -> Assertions.assertEquals(TweetBuilder.USER_LOCATION, entityTweet.getUser().getLocation(), "Entity tweet user_location error"),
                () -> Assertions.assertEquals(TweetBuilder.USER_FOLLOWERS, entityTweet.getUser().getFollowersCount(), "Entity tweet user_followers error")
        );
        Assertions.assertAll("entityTweet hashtags",
                () -> Assertions.assertEquals(3, entityTweet.getHashtags().size(), "Entity tweet hashtags size"),
                () -> Assertions.assertEquals(TweetBuilder.HASHTAG_TEXT_1, entityTweet.getHashtags().get(0).getText(), "Entity tweet hashtag 1 text"),
                () -> Assertions.assertEquals(TweetBuilder.HASHTAG_TEXT_2, entityTweet.getHashtags().get(1).getText(), "Entity tweet hashtag 2 text"),
                () -> Assertions.assertEquals(TweetBuilder.HASHTAG_TEXT_3, entityTweet.getHashtags().get(2).getText(), "Entity tweet hashtag 3 text")
        );
    }

    @Test
    public void convertTweetEntityToModel() {
        Tweet modelTweet = tweetMapper.convertTweetEntityToModel(TweetBuilder.buildEntityTweet());
        Assertions.assertAll("modelTweet",
                () -> Assertions.assertEquals(new BigInteger(TweetBuilder.TWEET_ID), modelTweet.getId(), "Model tweet id error"),
                () -> Assertions.assertEquals(TweetBuilder.TWEET_ID, modelTweet.getIdStr(), "Model tweet id_str error"),
                () -> Assertions.assertEquals(TweetBuilder.TWEET_TEXT, modelTweet.getText(), "Model tweet text error"),
                () -> Assertions.assertEquals(TweetBuilder.TWEET_LANG, modelTweet.getLang(), "Model tweet lang error"),
                () -> Assertions.assertEquals(Boolean.FALSE, modelTweet.getValidated(), "Model tweet validated error")
        );
        Assertions.assertAll("modelTweet place",
                () -> Assertions.assertEquals(TweetBuilder.PLACE_ID, modelTweet.getPlace().getId(), "Model tweet place_id error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_TYPE, modelTweet.getPlace().getPlaceType(), "Model tweet place_type error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_NAME, modelTweet.getPlace().getName(), "Model tweet place_name error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_FULL_NAME, modelTweet.getPlace().getFullName(), "Model tweet place_fullName error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_COUNTRY, modelTweet.getPlace().getCountry(), "Model tweet place_country error"),
                () -> Assertions.assertEquals(TweetBuilder.PLACE_COUNTRY_CODE, modelTweet.getPlace().getCountryCode(), "Model tweet place_countryCode error")
        );
        Assertions.assertAll("modelTweet user",
                () -> Assertions.assertEquals(new BigInteger(TweetBuilder.USER_ID), modelTweet.getUser().getId(), "Model tweet user_id error"),
                () -> Assertions.assertEquals(TweetBuilder.USER_NAME, modelTweet.getUser().getName(), "Model tweet user_name error"),
                () -> Assertions.assertEquals(TweetBuilder.USER_SCREEN_NAME, modelTweet.getUser().getScreenName(), "Model tweet user_screenName error"),
                () -> Assertions.assertEquals(TweetBuilder.USER_LOCATION, modelTweet.getUser().getLocation(), "Model tweet user_location error"),
                () -> Assertions.assertEquals(TweetBuilder.USER_FOLLOWERS, modelTweet.getUser().getFollowersCount(), "Model tweet user_followers error")
        );
        Assertions.assertAll("modelTweet hashtags",
                () -> Assertions.assertNotNull(modelTweet.getEntities()),
                () -> Assertions.assertEquals(3, modelTweet.getEntities().getHashtags().size(), "Model tweet hashtags size"),
                () -> Assertions.assertEquals(TweetBuilder.HASHTAG_TEXT_1, modelTweet.getEntities().getHashtags().get(0).getText(), "Model tweet hashtag 1 text"),
                () -> Assertions.assertEquals(TweetBuilder.HASHTAG_TEXT_2, modelTweet.getEntities().getHashtags().get(1).getText(), "Model tweet hashtag 2 text"),
                () -> Assertions.assertEquals(TweetBuilder.HASHTAG_TEXT_3, modelTweet.getEntities().getHashtags().get(2).getText(), "Model tweet hashtag 3 text")
        );
    }

/*
    private Tweet buildModelTweet() {
        User user = new User(new BigInteger(USER_ID), USER_ID, USER_NAME, USER_SCREEN_NAME, USER_LOCATION, USER_FOLLOWERS);
        Tweet tweet = new Tweet(new BigInteger(TWEET_ID), TWEET_ID, TWEET_TEXT, user);
        tweet.setValidated(false);
        tweet.setLang(TWEET_LANG);
        tweet.setPlace(buildModelPlace());
        tweet.setEntities(new Entities());
        tweet.getEntities().setHashtags(buildModelHashTags());
        return tweet;
    }

    private Place buildModelPlace() {
        Place place = new Place(PLACE_ID, PLACE_NAME);
        place.setPlaceType(PLACE_TYPE);
        place.setFullName(PLACE_FULL_NAME);
        place.setCountry(PLACE_COUNTRY);
        place.setCountryCode(PLACE_COUNTRY_CODE);
        return place;
    }

    private List<Hashtag> buildModelHashTags() {
        List<Hashtag> hashtagList = new ArrayList<>();
        hashtagList.add(buildModelHashtag(1, 3, HASHTAG_TEXT_1));
        hashtagList.add(buildModelHashtag(4, 8, HASHTAG_TEXT_2));
        hashtagList.add(buildModelHashtag(10, 14, HASHTAG_TEXT_3));
        return hashtagList;
    }

    private Hashtag buildModelHashtag(final int index1, final int index2, final String text) {
        int[] indices = new int[2];
        indices[0] = index1;
        indices[1] = index2;
        return new Hashtag(indices, text);
    }

    private com.shiningstar.twitter.domain.entity.Tweet buildEntityTweet() {
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

    private com.shiningstar.twitter.domain.entity.User buildEntityUser() {
        com.shiningstar.twitter.domain.entity.User user = new com.shiningstar.twitter.domain.entity.User();
        user.setId(USER_ID);
        user.setName(USER_NAME);
        user.setScreenName(USER_SCREEN_NAME);
        user.setLocation(USER_LOCATION);
        user.setFollowersCount(USER_FOLLOWERS);
        return user;
    }

    private com.shiningstar.twitter.domain.entity.Place buildEntityPlace() {
        com.shiningstar.twitter.domain.entity.Place place = new com.shiningstar.twitter.domain.entity.Place();
        place.setId(PLACE_ID);
        place.setName(PLACE_NAME);
        place.setPlaceType(PLACE_TYPE);
        place.setFullName(PLACE_FULL_NAME);
        place.setCountry(PLACE_COUNTRY);
        place.setCountryCode(PLACE_COUNTRY_CODE);
        return place;
    }

    private List<com.shiningstar.twitter.domain.entity.Hashtag> buildEntityHashTags(final com.shiningstar.twitter.domain.entity.Tweet tweet) {
        List<com.shiningstar.twitter.domain.entity.Hashtag> hashtagList = new ArrayList<>();
        hashtagList.add(buildEntityHashtag(1, 3, HASHTAG_TEXT_1, tweet));
        hashtagList.add(buildEntityHashtag(4, 8, HASHTAG_TEXT_2, tweet));
        hashtagList.add(buildEntityHashtag(10, 14, HASHTAG_TEXT_3, tweet));
        return hashtagList;
    }

    private com.shiningstar.twitter.domain.entity.Hashtag buildEntityHashtag(final int index1,
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
*/



}
