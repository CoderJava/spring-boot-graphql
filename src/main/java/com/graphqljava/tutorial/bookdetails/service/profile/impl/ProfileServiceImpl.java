package com.graphqljava.tutorial.bookdetails.service.profile.impl;

import java.util.ArrayList;
import java.util.List;

import com.graphqljava.tutorial.bookdetails.dao.profile.ProfileDao;
import com.graphqljava.tutorial.bookdetails.db.profile.Profile;
import com.graphqljava.tutorial.bookdetails.service.profile.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "profileService")
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao profileDao;

    @Override
    public Profile save(Profile profile) {
        return profileDao.save(profile);
    }

    @Override
    public Profile findProfileById(Long id) {
        return profileDao.findById(id).orElse(null);
    }

    @Override
    public List<Profile> findAll() {
        List<Profile> listProfile = new ArrayList<>();
        profileDao.findAll().iterator().forEachRemaining(listProfile::add);
        return listProfile;
    }

    @Override
    public List<Profile> findProfileByEmail(String email) {
        List<Profile> listProfile = new ArrayList<>();
        profileDao.findByEmail(email).iterator().forEachRemaining(listProfile::add);
        return listProfile;
    }

    @Override
    public List<Profile> findProfileByName(String name) {
        List<Profile> listProfile = new ArrayList<>();
        profileDao.findByName(name).iterator().forEachRemaining(listProfile::add);
        return listProfile;
    }

    @Override
    public void delete(long id) {
        profileDao.deleteById(id);
    }
}