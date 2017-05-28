package org.ckr.msdemo.adminservice.repository;

import org.ckr.msdemo.adminservice.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, String> {

    public User findByUserName(String userName);

}