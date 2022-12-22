package jsonmanagment;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;


public class ObjectMapping<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    Class<T> contentClass;

    public ObjectMapping(Class<T> contentClass) {
       this.contentClass = contentClass;

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
