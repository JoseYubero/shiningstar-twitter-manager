package com.shiningstar.twitter.service;

import com.shiningstar.twitter.domain.model.HashtagMoreUsed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TweetService {

    private Twitter twitter;
    private TweetPersistenceService tweetPersistenceService;

    @Value("${tweet.hashtag.moreUsed.max:10}")
    private Integer maxHashtagMoreUsed;

    @Autowired
    public TweetService(final Twitter twitter,
                        final TweetPersistenceService tweetPersistenceService) {
        this.twitter = twitter;
        this.tweetPersistenceService = tweetPersistenceService;
    }

    public List<String> getLatestTweets() {
        List<String> tweets = new ArrayList<>();
        try {
            ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
            for (Status status : homeTimeline) {
                tweets.add(status.getText());
            }
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
        return tweets;
    }

    public List<Status> searchTweet(final String tweetText) {
        List<Status> tweets = new ArrayList<>();
        try {
            Query query = new Query(tweetText);
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                log.debug("@" + status.getUser().getScreenName() + ":" + status.getText());
                tweets.add(status);
            }
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
        return tweets;
    }

    public List<HashtagMoreUsed> getMoreUsedHashtagOfTweets() {
        List<HashtagMoreUsed> tweets = new ArrayList<>();
        try {
            ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
            Map<String, HashtagMoreUsed> hashtagMoreUsedMap = new HashMap<>();
            for (Status status : homeTimeline) {
                List<String> hashtagTextList2 =
                        (Arrays.stream(status.getHashtagEntities()).map(hashtagEntity -> hashtagEntity.getText())
                                .collect(Collectors.toList()));

                hashtagTextList2.stream().forEach(hashtagText -> {
                    Long count = 1L;
                    if (hashtagMoreUsedMap.keySet().contains(hashtagText)) {
                        count += hashtagMoreUsedMap.get(hashtagText).getCount();
                    }
                    hashtagMoreUsedMap.put(hashtagText, new HashtagMoreUsed(hashtagText, count));
                });
            }
            return hashtagMoreUsedMap.values().stream().sorted((o1, o2) -> o1.getCount().compareTo(o2.getCount()))
                    .limit(maxHashtagMoreUsed).collect(Collectors.toList());
        } catch (TwitterException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 0/15 * ? * *")
    public void getTweets() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance().addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                log.debug("Got a new status @" + status.getUser().getScreenName() + " - " + status.getText());
                tweetPersistenceService.persistStatus(status);
                log.debug("Persisted status: @" + status.getUser().getScreenName() + " - " + status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                log.debug("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                log.debug("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                log.debug("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                log.debug("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        }).sample();
    }

}
