package com.shiningstar.twitter.domain.repository;

import com.shiningstar.twitter.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findById(String id);
}
