package com.graphqljava.tutorial.bookdetails.dao.profile;

import com.graphqljava.tutorial.bookdetails.db.profile.Profile;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDao extends CrudRepository<Profile, Long> {
    Profile findByEmail(String email);
}