package com.shiningstar.twitter.domain.repository;

import com.shiningstar.twitter.domain.entity.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {

    Place findById(String id);
}
