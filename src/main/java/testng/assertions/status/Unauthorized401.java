package testng.assertions.status;

import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import testng.BeforeTest;

public class Unauthorized401 {

    @Test()
    public void givenAServiceIsCalled_whenCallServiceCallIsMissingAuthorisationData_then401ResponseCodeIsReceived() {
        // Given
        int responseStatus = BeforeTest.getResponse().getCode();
        // Then
        MatcherAssert.assertThat(responseStatus, Matchers.equalTo(HttpStatus.SC_UNAUTHORIZED));
    }
}
