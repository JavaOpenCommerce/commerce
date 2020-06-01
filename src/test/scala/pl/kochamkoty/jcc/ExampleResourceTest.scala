package pl.kochamkoty.jcc

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class ExampleResourceTest {

    @Test
    def testHelloEndpoint() = {
        given()
          .`when`().get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("hello"))
    }

}