package dk.sdu.sem4.pro.webpage.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import dk.sdu.sem4.pro.commondata.data.Batch;
import dk.sdu.sem4.pro.commondata.data.Recipe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;


class ProductionControllerTest {
    /*
    private ProductionController productionController;

    @BeforeEach
    void setUp() {
        productionController = new ProductionController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void startProduction_Success() {
        ResponseEntity<String> response = productionController.startProduction();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Production started successfully", response.getBody());
    }

    @Test
    void startProduction_Failure() {
        ResponseEntity<String> response = productionController.startProduction();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to start production", response.getBody());
    }

    @Test
    void addBatch_Success() {
        Batch batch = new Batch();

        ResponseEntity<Integer> response = productionController.addBatch(batch);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    void addBatch_InvalidRequest() {
        Batch batch = new Batch();
        ResponseEntity<Integer> response = productionController.addBatch(batch);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllRecipes_Success() {
        List<Recipe> recipes = Collections.singletonList(new Recipe());

        ResponseEntity<List<Recipe>> response = productionController.getAllRecipes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recipes, response.getBody());
    }

    @Test
    void getAllRecipes_NoContent() {
        ResponseEntity<List<Recipe>> response = productionController.getAllRecipes();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
    */
}