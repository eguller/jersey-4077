package com.test.jersey;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.Random;
import java.util.logging.Logger;


public class TestDateController {
    Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final Random RANDOM_GENERATOR = new Random();

    /**
     * Run test 10 times, since problem is intermittent (hashcode dependent),
     * it may fail/succeed with a single run.
     * @throws IOException
     */
    @Test
    public void test_dateController() throws IOException {
        for(int i = 0; i < 10; i++) {
            HttpServer server = start();
            Response response = RestAssured.given().log().all().when().queryParam("todayDate", "2019-03-12").get("date/test");
            server.shutdownNow();
            Assert.assertEquals(200, response.getStatusCode());
            LOGGER.info(i +". iteration was successfull.");
        }
    }

    private static HttpServer start() throws IOException {
        int randomPort = randomPortGenerator();
        String url = "http://localhost:" + randomPort;
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(url), new Application());
        RestAssured.reset();
        RestAssured.baseURI = url;
        server.start();
        return server;
    }

    private static int randomPortGenerator(){
        return RANDOM_GENERATOR.nextInt(100) + 50000;
    }
}
