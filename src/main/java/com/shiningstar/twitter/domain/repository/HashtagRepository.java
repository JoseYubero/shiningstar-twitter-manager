package com.shiningstar.twitter.domain.repository;

import com.shiningstar.twitter.domain.entity.Hashtag;
import com.shiningstar.twitter.domain.model.HashtagMoreUsed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

    @Query("select new com.shiningstar.twitter.domain.model.HashtagMoreUsed(h.text as text, "
            + "count(h.text) as count) "
            + "from Hashtag h "
            + "group by h.text")
    List<HashtagMoreUsed> findHashtagMoreUsed();
}
