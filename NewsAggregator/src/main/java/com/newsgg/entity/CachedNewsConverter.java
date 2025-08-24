package com.newsgg.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import reactor.core.publisher.Mono;

import java.util.List;

@Converter
public class CachedNewsConverter implements AttributeConverter<List<NewsAPI.Article>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<NewsAPI.Article> articles) {
        try {
            return objectMapper.writeValueAsString(articles);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert articles to JSON", e);
        }
    }

    @Override
    public List<NewsAPI.Article> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<NewsAPI.Article>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to articles", e);
        }
    }
}
