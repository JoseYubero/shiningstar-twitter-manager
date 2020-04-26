package com.shiningstar.twitter.mapper;

import com.shiningstar.twitter.common.TweetBuilder;
import com.shiningstar.twitter.domain.model.Tweet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

public class TwitterMapperTest {

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
}
