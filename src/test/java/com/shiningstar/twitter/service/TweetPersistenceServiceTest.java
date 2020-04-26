package com.shiningstar.twitter.service;

import com.shiningstar.twitter.common.TweetBuilder;
import com.shiningstar.twitter.domain.entity.Hashtag;
import com.shiningstar.twitter.domain.model.HashtagMoreUsed;
import com.shiningstar.twitter.domain.model.Tweet;
import com.shiningstar.twitter.domain.repository.HashtagRepository;
import com.shiningstar.twitter.domain.repository.PlaceRepository;
import com.shiningstar.twitter.domain.repository.TweetRepository;
import com.shiningstar.twitter.domain.repository.UserRepository;
import com.shiningstar.twitter.mapper.TweetMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TweetPersistenceServiceTest {

    @InjectMocks
    private TweetPersistenceService tweetPersistenceService;

    @Mock
    private TweetRepository tweetRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private HashtagRepository hashtagRepository;

    private TweetMapper tweetMapper;

    private Tweet modelTweet;
    private com.shiningstar.twitter.domain.entity.Tweet entityTweet;
    @BeforeEach()
    public void init() {
        MockitoAnnotations.initMocks(this);
        modelTweet = TweetBuilder.buildModelTweet();
        entityTweet = TweetBuilder.buildEntityTweet();

        ReflectionTestUtils.setField(tweetPersistenceService, "tweetMapper", new TweetMapper());
        ReflectionTestUtils.setField(tweetPersistenceService, "minFollowers", 1500);
        List<String> languagesList = new ArrayList<>();
        languagesList.add("es");
        languagesList.add("fr");
        languagesList.add("it");
        ReflectionTestUtils.setField(tweetPersistenceService, "tweetLanguagesList", languagesList);
        ReflectionTestUtils.setField(tweetPersistenceService, "maxHashtagMoreUsed", 1);
   }

    @Test
    public void persistTweetIfMeetTheConditions() {
        when(userRepository.findById("100")).thenReturn(null);
        when(userRepository.save(any(com.shiningstar.twitter.domain.entity.User.class))).thenReturn(null);
        when(placeRepository.findById("28100")).thenReturn(null);
        when(placeRepository.save(any(com.shiningstar.twitter.domain.entity.Place.class))).thenReturn(null);
        when(tweetRepository.save(entityTweet)).thenReturn(entityTweet);
        when(hashtagRepository.save(any(Hashtag.class))).thenReturn(null);
        Assertions.assertTrue(tweetPersistenceService.persistTweet(modelTweet));
    }

    @Test
    public void persistTweetIfNotMeetTheConditions() {
        modelTweet.setLang("de");
        Assertions.assertFalse(tweetPersistenceService.persistTweet(modelTweet));
    }

    @Test
    public void searchTweets() {
        List<com.shiningstar.twitter.domain.entity.Tweet> entityTweetList = new ArrayList<>();
        entityTweetList.add(entityTweet);
        when(tweetRepository.findAllByTextContainsAndUserNameContainsAndPlaceNameContains(null, "John Smith", null)).thenReturn(entityTweetList);
        List<Tweet> modelTweetList = tweetPersistenceService.searchTweets(null, "John Smith", null, false);
        Assertions.assertEquals(TweetBuilder.TWEET_ID, modelTweetList.get(0).getIdStr());
    }

    @Test
    public void searchUserTweetsValidated() {
        List<com.shiningstar.twitter.domain.entity.Tweet> entityTweetList = new ArrayList<>();
        entityTweet.setValidated(true);
        entityTweetList.add(entityTweet);
        when(tweetRepository.findAllByUserNameIsAndValidatedIsTrue("Peter Nolas")).thenReturn(entityTweetList);
        List<Tweet> modelTweetList = tweetPersistenceService.searchUserTweetsValidated("Peter Nolas");
        Assertions.assertEquals(TweetBuilder.TWEET_ID, modelTweetList.get(0).getIdStr());
    }

    @Test
    public void validateTweet() {
        when(tweetRepository.findById(TweetBuilder.TWEET_ID)).thenReturn(entityTweet);
        entityTweet.setValidated(true);
        when(tweetRepository.save(entityTweet)).thenReturn(entityTweet);
        Assertions.assertTrue(tweetPersistenceService.validateTweet(TweetBuilder.TWEET_ID));
    }

    @Test
    public void hashtagMoreUsed() {
        List<HashtagMoreUsed> hashtagMoreUsedList = new ArrayList<>();
        HashtagMoreUsed hashtagMoreUsed = new HashtagMoreUsed();
        hashtagMoreUsed.setText("Hashtag_text");
        hashtagMoreUsed.setCount(14l);
        hashtagMoreUsedList.add(hashtagMoreUsed);
        hashtagMoreUsed = new HashtagMoreUsed();
        hashtagMoreUsed.setText("Hashtag_text_2");
        hashtagMoreUsed.setCount(6l);
        hashtagMoreUsedList.add(hashtagMoreUsed);
        when(hashtagRepository.findHashtagMoreUsed()).thenReturn(hashtagMoreUsedList);
        List<HashtagMoreUsed> resultList = tweetPersistenceService.searchHashtagMoreUsed();
        Assertions.assertEquals(1, resultList.size());
        HashtagMoreUsed hashtag = resultList.get(0);
        Assertions.assertEquals("Hashtag_text", hashtag.getText());
        Assertions.assertEquals(14, hashtag.getCount());
    }

}
