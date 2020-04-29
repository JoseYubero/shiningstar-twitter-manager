package com.shiningstar.twitter.rest;

import com.shiningstar.twitter.domain.model.HashtagMoreUsed;
import com.shiningstar.twitter.domain.model.Tweet;
import com.shiningstar.twitter.service.TweetPersistenceService;
import com.shiningstar.twitter.service.TweetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;

import java.util.List;

@Api(value = "TwitterController")
@RequestMapping("/")
@RestController
@Slf4j
public class TwitterController {

    private static final int HTTP_OK = 200;
    private static final int HTTP_SERVICE_UNAVAILABLE = 503;

    private TweetPersistenceService tweetPersistenceService;

    private TweetService tweetService;

    public TwitterController(final TweetPersistenceService tweetPersistenceService,
                             final TweetService tweetService) {
        this.tweetPersistenceService = tweetPersistenceService;
        this.tweetService = tweetService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for loading tweet", notes = "endpoint for loading tweet")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
                    @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @PostMapping(path = "/load", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> loadTweet(@RequestBody final Tweet tweet) {
        ResponseEntity<Boolean> responseEntity = null;
        try {
            log.debug(tweet.toString());
            if (tweetPersistenceService.persistTweet(tweet)) {
                responseEntity = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_MODIFIED);
            }
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for loading tweet by Stream API",
                  notes = "endpoint for loading tweet by Stream API")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
                    @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @PostMapping(path = "/web/load", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> loadStatus() {
        ResponseEntity<Boolean> responseEntity = null;
        try {
            log.debug("Loading tweets from Stream API...");
            tweetService.getTweets();
            responseEntity = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for searching tweet", notes = "endpoint for searching tweet")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
            @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @GetMapping(path = "api/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tweet>> searchTweet(@RequestParam(value = "text", required = false, defaultValue = "") final String text,
                                                   @RequestParam(value = "userName", required = false, defaultValue = "") final String userName,
                                                   @RequestParam(value = "placeName", required = false, defaultValue = "") final String placeName,
                                                   @RequestParam(value = "validated", required = false) final Boolean validated) {
        ResponseEntity<List<Tweet>> responseEntity;
        try {
            List<Tweet> tweetList = tweetPersistenceService.searchTweets(text, userName, placeName, validated);
            responseEntity = new ResponseEntity<>(tweetList, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for search user's status", notes = "endpoint for search user's tweets")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
                    @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @GetMapping(path = "api/search/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Tweet>> searchUserTweet(@PathVariable(value = "userName") final String userName) {
        ResponseEntity<List<Tweet>> responseEntity;
        try {
            log.debug("Searching tweet with user: \"" + userName + "\" on DDBB");
            List<Tweet> tweetList = tweetPersistenceService.searchUserTweetsValidated(userName);
            responseEntity = new ResponseEntity<>(tweetList, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for searching tweet by text",
                  notes = "endpoint for searching tweet by text")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
                    @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @GetMapping(path = "api/web/search/{tweetText}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Status>> searchTweetByText(@PathVariable(value = "tweetText") final String tweetText) {
        ResponseEntity<List<Status>> responseEntity;
        try {
            log.debug("Searching tweet with text: \"" + tweetText + "\" on Twitter");
            List<Status> tweetList = tweetService.searchTweet(tweetText);
            responseEntity = new ResponseEntity<>(tweetList, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for validate tweet", notes = "endpoint for validate tweet")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
                    @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @PutMapping(path = "api/validate/{tweetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validateTweet(@PathVariable(value = "tweetId") final String tweetId) {
        ResponseEntity<Boolean> responseEntity;
        try {
            log.debug("Validating tweet: \"" + tweetId + "\"");
            if (tweetPersistenceService.validateTweet(tweetId)) {
                responseEntity = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_MODIFIED);
            }
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for search hashtag more used", notes = "endpoint for search hashtag more used")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
                    @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @GetMapping(path = "api/search/hashtagMoreUsed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashtagMoreUsed>> searchHashtagMoreUsed() {
        ResponseEntity<List<HashtagMoreUsed>> responseEntity;
        try {
            log.debug("Searching more-used hashtags on DDBB");
            List<HashtagMoreUsed> HashtagMoreUsedList = tweetPersistenceService.searchHashtagMoreUsed();
            responseEntity = new ResponseEntity<>(HashtagMoreUsedList, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for searching hashtag more used by Stream API",
                  notes = "endpoint for searching hashtag more used by stream API")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
                    @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @GetMapping(path = "api/web/search/hashtagMoreUsed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HashtagMoreUsed>> searchHashtagMoreUsedFromStream() {
        ResponseEntity<List<HashtagMoreUsed>> responseEntity;
        try {
            log.debug("Searching more-used hashtags on Twitter");
            List<HashtagMoreUsed> HashtagMoreUsedList = tweetService.getMoreUsedHashtagOfTweets();
            responseEntity = new ResponseEntity<>(HashtagMoreUsedList, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return responseEntity;
    }
}
