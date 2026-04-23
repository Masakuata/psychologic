package com.xatal.psychologic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xatal.psychologic.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
