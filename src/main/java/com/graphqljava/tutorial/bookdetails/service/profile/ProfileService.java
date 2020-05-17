package com.graphqljava.tutorial.bookdetails.service.profile;

import java.util.List;

import com.graphqljava.tutorial.bookdetails.db.profile.Profile;

public interface ProfileService {
    Profile save(Profile profile);

    Profile findProfileById(Long id);

    List<Profile> findAll();

    List<Profile> findProfileByEmail(String email);

    List<Profile> findProfileByName(String name);

    void delete(long id);
}