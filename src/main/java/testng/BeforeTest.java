package testng;
import okwrapper.OKAPIExecutor;
import org.json.JSONObject;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertEquals;

public class BeforeTest {

    JSONObject json = null;

    @org.testng.annotations.BeforeTest(alwaysRun = true)
    @Parameters({"requestData", "testVariables"})
    public void beforeTest(String requestData, @Optional String testVariables) {
        System.out.println(requestData);
        System.out.println(testVariables);
    }

    @Test
    public void test() {
        assertEquals(null, json);
    }
}
