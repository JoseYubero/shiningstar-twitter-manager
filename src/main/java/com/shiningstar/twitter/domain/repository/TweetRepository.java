package com.shiningstar.twitter.domain.repository;

import com.shiningstar.twitter.domain.entity.Tweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {

    Tweet findById(String id);
    List<Tweet> findAllByTextContainsAndUserNameContainsAndPlaceNameContains(String text, String userName, String placeName);
    List<Tweet> findAllByTextContainsAndUserNameContainsAndPlaceNameContainsAndValidated(String text, String userName, String placeName, boolean validated);
    List<Tweet> findAllByUserNameIsAndValidatedIsTrue(String userName);
    List<Tweet> findAllByTextContains(String text);
    List<Tweet> findAll();
}
