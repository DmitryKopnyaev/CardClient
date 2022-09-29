package com.kopniaev.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopniaev.model.Category;
import com.kopniaev.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ConnectServer {
    private ObjectMapper objectMapper = new ObjectMapper();
    private Category category;
    private List<Category> categories;

    public CategoryRepository(int id, String method) throws IOException {
        if (method.equals("GET"))
            try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME
                    + "/category/user/" + id, method, null)) {
                this.categories = objectMapper.readValue(bufferedReader, new TypeReference<ArrayList<Category>>() {
                });
            }
        else if (method.equals("DELETE")) {
            try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME
                    + "/category/" + id, method, null)) {
                this.category = objectMapper.readValue(bufferedReader, Category.class);
            }
        }
    }

    public CategoryRepository(int idUser, Category category, String method) throws IOException {
        if (method.equals("POST")) {
            try (BufferedReader bufferedReader = connect(Constants.SERVER_NAME
                    + "/category/" + idUser, method, this.objectMapper.writeValueAsString(category))) {
                this.category = objectMapper.readValue(bufferedReader, Category.class);
            }
        }
    }

    public Category getCategory() {
        return this.category;
    }

    public List<Category> getCategories() {
        return this.categories;
    }
}
