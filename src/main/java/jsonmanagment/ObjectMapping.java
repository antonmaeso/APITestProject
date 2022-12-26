package jsonmanagment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import request.RequestBuilder;

import java.io.File;
import java.lang.reflect.Type;


public class ObjectMapping<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    Class<T> contentClass;

    public ObjectMapping(Class<T> contentClass) {
       this.contentClass = contentClass;

    }

    public T stringToObjectMapper(String stringJson){
        try {
            return objectMapper.readValue(stringJson, contentClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public T objectMapper(File file) {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        JavaType type = objectMapper.getTypeFactory().constructType(contentClass);
        try {
            return objectMapper.readValue(file, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
