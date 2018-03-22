package ua.estate.rialto.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

import static ua.estate.rialto.util.json.JacksonObjectMapper.getMapper;
import static ua.estate.rialto.util.json.JacksonObjectMapper.getPrettyWriter;

/**
 * Утилитный класс для работы с json
 *
 * @author kostia
 */
public class JsonUtil {

    // получить объект из json. Пример передачи typeReference: new TypeReference<List<Agent>>(){}
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return getMapper().readValue(json, typeReference);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    // создать json из объекта
    public static <T> String toJson(T obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    // создать json из нескольких объектов, массива или массивов
    public static <T> String toJsonArray(T... array) {
        return toJson(array);
    }

    // создать отформатированный json из объекта
    public static <T> String toPrettyJson(T obj){
        try {
            return getPrettyWriter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    // создать отформатированный json из нескольких объектов, массива или массивов
    public static <T> String toPrettyJsonArray(T... array) {
        return toPrettyJson(array);
    }
}
