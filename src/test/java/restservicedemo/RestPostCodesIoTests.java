package restservicedemo;

import com.httprestdemo.restservicesdemo.dto.PostcodeUkDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Created by dgliga on 26.02.2018.
 */
public class RestPostCodesIoTests extends BaseRestAbstract {

    private SoftAssert softAssert = new SoftAssert();
    private String ukPostCodeRegex = "^(GIR ?0AA|[A-PR-UWYZ]([0-9]{1,2}|([A-HK-Y][0-9]([0-9ABEHMNPRV-Y])?)|[0-9]" +
            "[A-HJKPS-UW]) ?[0-9][ABD-HJLNP-UW-Z]{2})$";

    @Test(description = "This test verifies that REST service exposed by postcodesio to get random uk postcode works " +
            "accordingly.")
    public void checkGetRandomUkPostCodeTest() {
        ResponseEntity<PostcodeUkDto> randomUkPostcode = serviceImpl.getRandomUkPostCode();

        //check that server responded with 200 ok
        softAssert.assertTrue(randomUkPostcode.getStatusCode().equals(HttpStatus.OK), "Service did not respond with " +
                "HttpStatus 200 OK.");

        //check that post code retrieved matches uk post code pattern
        softAssert.assertTrue(randomUkPostcode.getBody().getResult().getPostcode().matches(ukPostCodeRegex), "Postcode " +
                "retrieved by server did not match regex for valid UK post code.");
        softAssert.assertAll();
        //you can add database verifications and all types of verifications suited to your test and business logic
    }

}
