package jsonmanagment;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;

public class ObjectMapping<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public T objectMapper(File file, Class<T> contentClass) {
        JavaType type = objectMapper.getTypeFactory().constructType(contentClass);
        try {
            return objectMapper.readValue(file, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public File withJsonFile(String dataLocation) {
        return Paths.get( formatPathToJsonFile(dataLocation)).toFile();
    }

    @NotNull
    private String formatPathToJsonFile(String dataLocation) {
        if(!dataLocation.startsWith("C:")) {
            String[] dataLocationList = dataLocation.split("\\.");
            String lastItem = dataLocationList[dataLocationList.length - 1];
            return  Paths.get("").toAbsolutePath()+ "\\" + dataLocation.replace(".", "\\").replace("\\" + lastItem, "." + lastItem);
        }
        return dataLocation;
    }
}
