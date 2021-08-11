package testng;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.testng.annotations.*;
import request.IRequest;
import request.Request;

import java.nio.file.Paths;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class BeforeTest {

    JSONObject json = null;

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestData", "testVariables"})
    public void beforeTest(String requestData, @Optional String testVariables) {
        List<IRequest> requests = mapRequests(requestData);
        System.out.println(new JSONObject(testVariables));
    }
    private List<IRequest> mapRequests(String requestData) {
        ObjectMapper mapper = new ObjectMapper();
        if(!requestData.startsWith("\\")) {
            requestData = "\\" + requestData.replace(".", "\\").replace("\\json", ".json");
        }
        try {
            return mapper.readValue(Paths.get(Paths.get("").toAbsolutePath().toString() + requestData).toFile(), new TypeReference<List<Request>>() {
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
