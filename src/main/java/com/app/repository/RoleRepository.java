package com.app.repository;

import com.app.entity.AppRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends CrudRepository<AppRole,Long> {

}
