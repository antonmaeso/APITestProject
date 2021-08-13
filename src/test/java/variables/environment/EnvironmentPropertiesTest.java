package variables.environment;

import jsonmanagment.ObjectMapping;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentPropertiesTest {

    @Test
    public void loadEnvironmentPropertiesJSON(){
        ObjectMapping<Map> objectMapper = new ObjectMapping<>();
        File file = objectMapper.withJsonFile("src/main/resources/environment/test_env.json");
        Map envProperties =  objectMapper.objectMapper(file, Map.class);

        assertEquals(envProperties.get("hostname"), "test");
    }










































































































}