package com.app.repository;

import com.app.entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<AppUser,Long> {

    AppUser findByUserName(String name);
}
