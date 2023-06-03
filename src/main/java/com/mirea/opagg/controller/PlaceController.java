package com.mirea.opagg.controller;

import com.mirea.opagg.PlaceCreator;
import com.mirea.opagg.PlaceStub;
import com.mirea.opagg.exception.ResourceNotFoundException;
import com.mirea.opagg.model.Place;
import com.mirea.opagg.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.List;

@RestController
public class PlaceController {

    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping("/places")
    public List<Place> getPlaces() {
        return placeRepository.findAll();
    }


    @PostMapping("/places/{request}")
    public Place createPlace(@PathVariable String request) throws IOException, ParserConfigurationException, SAXException {


        PlaceStub stub = PlaceCreator.createPlaceFirstStep(request);
        if (placeRepository.findPlaceByName(stub.getName()).isEmpty())
        {
            Place place = PlaceCreator.createPlaceSecondStep(stub);
            return placeRepository.save(place);
        }
        else return placeRepository.findPlaceByName(stub.getName()).get(0);
    }

    @DeleteMapping("/places/{placeId}")
    public ResponseEntity<?> deletePlace(@PathVariable Long placeId) {
        return placeRepository.findById(placeId)
                .map(place -> {
                    placeRepository.delete(place);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Place not found with id " + placeId));
    }

}
