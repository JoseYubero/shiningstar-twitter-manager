package com.shiningstar.twitter.domain.repository;

import com.shiningstar.twitter.domain.entity.Hashtag;
import com.shiningstar.twitter.domain.entity.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

    Place findById(BigInteger id);
}
