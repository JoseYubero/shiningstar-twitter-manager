package com.shiningstar.twitter.service;

import com.shiningstar.twitter.domain.entity.Hashtag;
import com.shiningstar.twitter.domain.model.HashtagMoreUsed;
import com.shiningstar.twitter.domain.model.Tweet;
import com.shiningstar.twitter.domain.repository.HashtagRepository;
import com.shiningstar.twitter.domain.repository.PlaceRepository;
import com.shiningstar.twitter.domain.repository.TweetRepository;
import com.shiningstar.twitter.domain.repository.UserRepository;
import com.shiningstar.twitter.mapper.TweetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TweetPersistenceService {

    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    private PlaceRepository placeRepository;
    private HashtagRepository hashtagRepository;

    private TweetMapper tweetMapper;

    @Value("${tweet.user.minFollowers:1500}")
    private Integer minFollowers;

    @Value("#{'${tweet.languages}'.split(',')}")
    private List<String> tweetLanguagesList;

    @Value("${tweet.hashtag.moreUsed.max:10}")
    private Integer maxHashtagMoreUsed;

    @Autowired
    public TweetPersistenceService(final TweetRepository tweetRepository,
                                   final UserRepository userRepository,
                                   final PlaceRepository placeRepository,
                                   final HashtagRepository hashtagRepository,
                                   final TweetMapper tweetMapper) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
        this.hashtagRepository = hashtagRepository;
        this.tweetMapper = tweetMapper;
    }

    public boolean persistTweet(final Tweet tweet) {
        if (validateTweetToPersist(tweet)) {
            com.shiningstar.twitter.domain.entity.Tweet entityTweet = tweetMapper.convertTweetModelToEntity(tweet);
            if (userRepository.findById(entityTweet.getUser().getId()) == null) {
                userRepository.save(entityTweet.getUser());
            }
            if (placeRepository.findById(entityTweet.getPlace().getId()) == null) {
                placeRepository.save(entityTweet.getPlace());
            }
            com.shiningstar.twitter.domain.entity.Tweet saveTweet = tweetRepository.save(entityTweet);
            for (Hashtag hashtag: entityTweet.getHashtags()) {
                hashtag.setTweet(saveTweet);
                hashtagRepository.save(hashtag);
            }
            return true;
        }
        return false;
    }

    private Boolean validateTweetToPersist(final Tweet tweet) {
        if (tweetLanguagesList.contains(tweet.getLang()) &&
                tweet.getUser().getFollowersCount().compareTo(minFollowers) >= 0) {
            return true;
        }
        return false;
    }

    public List<Tweet> searchTweets (final String text, final String userName,
                                     final String placeName, final Boolean validated) {
        List<com.shiningstar.twitter.domain.entity.Tweet> entityTweetList =
                tweetRepository.findAllByTextContainsAndUserNameContainsAndPlaceNameContains(text, userName, placeName);
        if (!entityTweetList.isEmpty() && validated != null) {
            entityTweetList = entityTweetList.stream().filter(tweet -> tweet.getValidated().equals(validated)).collect(Collectors.toList());
        }

        return entityTweetList.stream().map(tweet -> tweetMapper.convertTweetEntityToModel(tweet)).collect(Collectors.toList());
    }

    public List<Tweet> searchUserTweetsValidated (final String userName) {
        List<com.shiningstar.twitter.domain.entity.Tweet> entityTweetList =
                tweetRepository.findAllByUserNameIsAndValidatedIsTrue(userName);

        return entityTweetList.stream().map(tweet -> tweetMapper.convertTweetEntityToModel(tweet)).collect(Collectors.toList());
    }

    public boolean validateTweet (final String idStr) {
        com.shiningstar.twitter.domain.entity.Tweet entityTweet = tweetRepository.findById(idStr);
        entityTweet.setValidated(true);
        com.shiningstar.twitter.domain.entity.Tweet savedTweet = tweetRepository.save(entityTweet);
        return (entityTweet.getValidated().equals(savedTweet.getValidated()));
    }

    public List<HashtagMoreUsed> searchHashtagMoreUsed () {
        List<com.shiningstar.twitter.domain.model.HashtagMoreUsed> hashtagMoreUsedList =
                hashtagRepository.findHashtagMoreUsed();

        return hashtagMoreUsedList.stream().limit(maxHashtagMoreUsed).collect(Collectors.toList());
    }
}
