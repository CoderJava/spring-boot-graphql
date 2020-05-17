package com.graphqljava.tutorial.bookdetails.graphql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.graphqljava.tutorial.bookdetails.db.profile.CreateProfileInput;
import com.graphqljava.tutorial.bookdetails.db.profile.EditProfileInput;
import com.graphqljava.tutorial.bookdetails.db.profile.Profile;
import com.graphqljava.tutorial.bookdetails.service.profile.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;

@Component
public class GraphQLDataFetchers {
    @Autowired
    private ProfileService profileService;

    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1", "name", "Harry Potter and the Philosopher's Stone", "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2", "name", "Moby Dick", "pageCount", "635", "authorId", "author-2"),
            ImmutableMap.of("id", "book-3", "name", "Interview with the vampire", "pageCount", "371", "authorId",
                    "author-3"));

    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1", "firstName", "Joanne", "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2", "firstName", "Herman", "lastName", "Melville"),
            ImmutableMap.of("id", "author-3", "firstName", "Anne", "lastName", "Rice"));

    public DataFetcher<Map<String, String>> getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return books.stream().filter(book -> book.get("id").equals(bookId)).findFirst().orElse(null);
        };
    }

    public DataFetcher<Map<String, String>> getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors.stream().filter(author -> author.get("id").equals(authorId)).findFirst().orElse(null);
        };
    }

    public DataFetcher<Profile> getProfileById() {
        return dataFetchingEnvironment -> {
            Long profileId = dataFetchingEnvironment.getArgument("id");
            return profileService.findProfileById(profileId);
        };
    }

    public DataFetcher<List<Profile>> getProfileByEmailDataFetcher() {
        return dataFetchingEnviroment -> {
            String profileEmail = dataFetchingEnviroment.getArgument("email");
            return profileService.findProfileByEmail(profileEmail);
        };
    }

    public DataFetcher<List<Profile>> getProfileByNameDataFetcher() {
        return dataFetchingEnvironment -> {
            String argName = dataFetchingEnvironment.getArgument("name");
            return profileService.findProfileByName(argName);
        };
    }

    public DataFetcher<List<Profile>> getAllProfileDataFetcher() {
        return dataFetchingEnvironment -> {
            return profileService.findAll();
        };
    }

    public DataFetcher<Profile> createProfileDataFetcher() {
        return dataFetchingEnviroment -> {
            Map<String, Object> arg = dataFetchingEnviroment.getArgument("input");
            ObjectMapper mapper = new ObjectMapper();
            CreateProfileInput createProfileInput = mapper.convertValue(arg, CreateProfileInput.class);
            Profile profile = new Profile(0, createProfileInput.getName(), createProfileInput.getEmail(),
                    createProfileInput.getAge());
            return profileService.save(profile);
        };
    }

    public DataFetcher<Map<String, Object>> updateProfileDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, Object> arg = dataFetchingEnvironment.getArgument("input");
            ObjectMapper mapper = new ObjectMapper();
            EditProfileInput editProfileInput = mapper.convertValue(arg, EditProfileInput.class);
            HashMap<String, Object> mapResponse = new HashMap<>();
            if (profileService.findProfileById(editProfileInput.getId()) == null) {
                mapResponse.put("status", false);
                mapResponse.put("message", "Data not found");
                return mapResponse;
            }
            Profile profileEdit = new Profile(editProfileInput.getId(), editProfileInput.getName(),
                    editProfileInput.getEmail(), editProfileInput.getAge());
            profileService.save(profileEdit);
            mapResponse.put("status", true);
            mapResponse.put("message", "Data successfully updated");
            return mapResponse;
        };
    }

    public DataFetcher<Map<String, Object>> deleteProfileDataFetcher() {
        return dataFetchingEnvironment -> {
            int inputId = dataFetchingEnvironment.getArgument("input");
            Profile profile = profileService.findProfileById(Long.valueOf(String.valueOf(inputId)));
            HashMap<String, Object> mapResponse = new HashMap<>();
            if (profile == null) {
                mapResponse.put("status", false);
                mapResponse.put("message", "Data not found");
                return mapResponse;
            } else {
                profileService.delete(inputId);
                mapResponse.put("status", true);
                mapResponse.put("message", "Data successfully deleted");
                return mapResponse;
            }
        };
    }
}