package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CartOfferTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:1080";
    }

    @Test
    public void testAddFlatXAmountOffer() {
        String requestBody = "{"
                + "\"restaurant_id\":1,"
                + "\"offer_type\":\"FLATX\","
                + "\"offer_value\":10,"
                + "\"customer_segment\":[\"p1\"]"
                + "}";

        Response response = given().header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/offer");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("success"));
    }

    @Test
    public void testApplyFlatXAmountOffer() {
        String requestBody = "{"
                + "\"cart_value\":200,"
                + "\"user_id\":1,"
                + "\"restaurant_id\":1"
                + "}";

        Response response = given().header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/cart/apply_offer");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("190"));
    }

    @Test
    public void testApplyFlatXPercentOffer() {
        String requestBody = "{"
                + "\"cart_value\":400,"
                + "\"user_id\":2,"
                + "\"restaurant_id\":2"
                + "}";

        Response response = given().header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/cart/apply_offer");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("360"));
    }

    @Test
    public void testAddFlatXPerP3AmountOffer() {
        String requestBody = "{"
                + "\"restaurant_id\":3,"
                + "\"offer_type\":\"FLATX%\","
                + "\"offer_value\":20,"
                + "\"customer_segment\":[\"p3\"]"
                + "}";

        Response response = given().header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/offer");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("success"));
    }

    @Test
    public void testApplyFlatXPercentOfferForP3() {
        String requestBody = "{"
                + "\"cart_value\":300,"
                + "\"user_id\":3,"
                + "\"restaurant_id\":3"
                + "}";

        Response response = given().header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/cart/apply_offer");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("240"));
    }

    @Test
    public void testAddFlatXPerP2AmountOffer() {
        String requestBody = "{"
                + "\"restaurant_id\":2,"
                + "\"offer_type\":\"FLATX%\","
                + "\"offer_value\":10,"
                + "\"customer_segment\":[\"p2\"]"
                + "}";

        Response response = given().header("Content-Type", "application/json")
                .body(requestBody)
                .post("/api/v1/offer");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("success"));
    }
}
