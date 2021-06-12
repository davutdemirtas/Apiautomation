package Api_Automation;

import org.junit.jupiter.api.*;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import org.junit.jupiter.api.DisplayName;



@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("End to End CRUD Happy Path")
public class Api_Automation_NorthwesternMutual {

    private static int newID ;

    @BeforeAll
    public static void setUp(){
        baseURI = "https://jsonplaceholder.typicode.com";

    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName( "1. Testing POST / Endpoint")
    @Test
    public void postData(){
        String newData="{\n" +
                "   \n" +
                "  \"userId\": 10,\n" +
                "  \"title\": \"Apple\",\n" +
                "  \"body\": \"New york is the new World \"\n" +
                "\n" +
                "   }\n";


        newID=
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(newData).
                when()
                .post("/posts").
                then()
                .log().all()
                .assertThat()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .extract().jsonPath().getInt("userId")
        ;
        System.out.println("newID="+newID);
    }



    @DisplayName("2. Testing GET / Endpoint")
    @Test
    public void getUserId(){
        given()
                .log().all()
                .contentType(ContentType.JSON).
                pathParam("userId", newID).
                when()
                .get("/users/{userId}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is(200) )
                .contentType(ContentType.JSON)
        ;


    }
    @DisplayName( "3. Testing PUT / Endpoint")
    @Test
    public void putUpdateData(){

                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .pathParam("userId", newID)
                        .body("{\n" +
                                "   \n" +
                                "  \"title\": \"New York\",\n" +
                                "  \"body\": \"New York is the big Apple \"\n" +
                                "\n" +
                                "   }\n").
                        when()
                        .put("/posts/{userId}").
                        then()
                        .log().all()
                        .assertThat()
                        .statusCode( is(200) )
                        .contentType(ContentType.JSON)
                ;

    }

    @DisplayName( "4. Testing DELETE / Endpoint")
    @Test
    public void deleteData(){

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("userId", newID).
                when()
                .delete("/posts/{userId}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is(200) )
                .contentType(ContentType.JSON)
        ;

    }





}
