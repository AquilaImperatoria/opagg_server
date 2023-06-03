package com.mirea.opagg;

import com.mirea.opagg.controller.PlaceController;
import com.mirea.opagg.model.Place;
import com.mirea.opagg.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceController.class)
public class PlaceControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PlaceRepository placeRepository;

    Place testplace = new Place("Реберная №1", "$$ - $$$ Американская Барбекю Европейская", "3.7", "Театральная площадь, 5/2, Москва 125009 Россия", "37.620896 55.757136", "tel:+74993916278","официант, ребро, обслуживание, персонал, мидия");

    @Test
    public void getAllPlaces_success() throws Exception {
        List<Place> records = new ArrayList<>(Arrays.asList(testplace));

        Mockito.when(placeRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/places")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Реберная №1")));
    }

    @Test
    public void createPlace_success() throws Exception {
        Place place = new Place("Реберная №1", "$$ - $$$ Американская Барбекю Европейская", "3.7", "Театральная площадь, 5/2, Москва 125009 Россия", "37.620896 55.757136", "tel:+74993916278","официант, ребро, обслуживание, персонал, мидия");

        mockMvc.perform(post("/places/{request}", "реберная1москва"))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Реберная №1")));
    }
}
