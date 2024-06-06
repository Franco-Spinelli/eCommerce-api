package com.mateocuevas.ecommerceapi.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mateocuevas.ecommerceapi.entity.Category;

import java.io.IOException;

public class CategoryDeserializer extends JsonDeserializer<Category> {
    @Override
    public Category deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String categoryName = p.getValueAsString();
        Category category = new Category();
        category.setName(categoryName);
        return category;
    }
}
