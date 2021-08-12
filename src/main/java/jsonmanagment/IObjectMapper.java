package jsonmanagment;

import java.io.File;
import java.util.List;

public interface IObjectMapper<T> {
    List<T> objectMapper(File file);
    File withJsonFile(String dataLocation);
}
