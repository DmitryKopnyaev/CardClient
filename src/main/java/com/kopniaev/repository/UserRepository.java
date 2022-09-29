package com.kopniaev.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopniaev.model.User;
import com.kopniaev.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UserRepository implements ConnectServer{
    private ObjectMapper om = new ObjectMapper();
    private User user;

    public UserRepository(User user) throws IOException {
        try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME + "/user/reg", "POST", this.om.writeValueAsString(user))) {
            this.user = om.readValue(bufferedReader, User.class);
        }
    }

    public UserRepository(String login, String password) throws IOException {
        try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME
                        + "/user/login?login=" + URLEncoder.encode(login, StandardCharsets.UTF_8)
                        + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8),
                "POST", null)) {
            this.user = om.readValue(bufferedReader, User.class);
        }
    }

    public UserRepository(int id) throws IOException {
        try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME + "/user/" + id, "GET", null)) {
            this.user = om.readValue(bufferedReader, User.class);
        }
    }

    public User getUser() {
        return this.user;
    }
}
