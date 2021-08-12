package testng.assertions.status;

import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import testng.BeforeTest;

public class InternalServerError500 {

    @Test(groups = {"integration"})
    public void givenServiceCall_whenBadRequestIsGenerated_then500ResponseCodeIsReturned() {
        // Given
        int responseStatus = BeforeTest.getResponse().getCode();
        // Then
        MatcherAssert.assertThat(responseStatus, Matchers.equalTo(HttpStatus.SC_INTERNAL_SERVER_ERROR));
    }
}
