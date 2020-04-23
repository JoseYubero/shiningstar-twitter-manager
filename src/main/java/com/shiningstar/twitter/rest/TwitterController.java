package com.shiningstar.twitter.rest;

import com.shiningstar.twitter.domain.model.Tweet;
import com.shiningstar.twitter.service.TweetPersistenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import java.util.List;

@Api(value = "TwitterController")
@RequestMapping("/")
@RestController
public class TwitterController {

    private static final int HTTP_OK = 200;
    private static final int HTTP_SERVICE_UNAVAILABLE = 503;

    private TweetPersistenceService tweetPersistenceService;

    public TwitterController(final TweetPersistenceService tweetPersistenceService) {
        this.tweetPersistenceService = tweetPersistenceService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "endpoint for load status", notes = "endpoint for load tweet")
    @ApiResponses({ @ApiResponse(code = HTTP_OK, message = "Successfully response"),
                    @ApiResponse(code = HTTP_SERVICE_UNAVAILABLE, message = "Not Available, return empty ResponseEntity") })
    @PostMapping(path = "/load", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> loadTweet(@RequestBody final Tweet tweet) {
        ResponseEntity<Boolean> responseEntity = null;
        try {
            System.out.println(tweet.toString());
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
    @ApiOperation(value = "endpoint for search status", notes = "endpoint for search tweet")
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
            List<Tweet> tweetList = tweetPersistenceService.searchUserTweetsValidated(userName);
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
    @PutMapping(path = "api/validate/{tweetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validateTweet(@PathVariable(value = "tweetId") final String tweetId) {
        ResponseEntity<Boolean> responseEntity;
        try {
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
}
