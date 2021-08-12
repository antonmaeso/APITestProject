package testng.assertions.status;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import testng.BeforeTest;


public class OK200 {


    @Test(groups = {"integration"})
    public void givenAService_whenCallServiceWithValidData_then200ResponseCodeIsReceived() {
        // Given
        int responseStatus = BeforeTest.getResponse().getCode();
        // Then
        MatcherAssert.assertThat(responseStatus, Matchers.equalTo(HttpStatus.SC_OK));
    }
}
