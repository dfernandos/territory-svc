package com.mapeando.territory.controller;

import com.mapeando.territory.entity.Coordinates;
import com.mapeando.territory.entity.Territory;
import com.mapeando.territory.service.TerritoryService;
import com.mapeando.territory.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/territory-svc")
public class TerritoryController {

    @Autowired
    TerritoryService territoryService;

    @PostMapping("/territory/create")
    public ResponseEntity<?> createTerritory(@RequestParam("file") MultipartFile file,
                                             @ModelAttribute Territory territory) {
        try {
            // Trate a imagem (converta para Base64 ou salve em disco, se preferir)
            byte[] imageData = file.getBytes();
            territory.setMainImage(imageData);

            // Salve o restante dos dados do território
            Territory savedTerritory = territoryService.createTerritory(territory);
            return new ResponseEntity<>(savedTerritory, HttpStatus.CREATED);
        } catch (IOException e) {
            String errorMessage = "Erro ao processar a imagem: " + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/territory/{id}")
    public Optional<Territory> getTerritoryById(@PathVariable String id){
        return territoryService.getTerritoryById(id);
    }

    @GetMapping("/territory/all")
    public List<Territory> getAllTerritories(){
        return territoryService.getAllTerrytories();
    }

    @GetMapping("/territory/coordinates")
    public List<Coordinates> getCoordinates(){
        return territoryService.getTerritoryCoordidates();
    }

    @PutMapping(value = "/territory/update/{territoryId}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateTerritory(
            @PathVariable String territoryId,
            @ModelAttribute Territory territory,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Optional<Territory> optionalTerritory = territoryService.getTerritoryById(territoryId);

        if (optionalTerritory.isPresent()) {

            Territory existingTerritory = optionalTerritory.get();

            try {
                if (file != null) {
                    byte[] imageData = file.getBytes();
                    territory.setMainImage(imageData);
                    existingTerritory.setMainImage(territory.getMainImage());
                } else {
                    existingTerritory.setMainImage(existingTerritory.getMainImage());
                }

                existingTerritory.setName(territory.getName());
                existingTerritory.setBriefDescription(territory.getBriefDescription());
                existingTerritory.setHistory(territory.getHistory());
                existingTerritory.setCartografia(territory.getCartografia());
                existingTerritory.setReligion(territory.getReligion());
                existingTerritory.setExtra_content(territory.getExtra_content());
                existingTerritory.setReference(territory.getReference());
                existingTerritory.setLatitude(territory.getLatitude());
                existingTerritory.setLongitude(territory.getLongitude());
                existingTerritory.setScratchEmbeb(territory.getScratchEmbeb());

                Territory savedTerritory = territoryService.updateTerritory(existingTerritory);
                return new ResponseEntity<>(savedTerritory, HttpStatus.CREATED);
            } catch (IOException e) {
                String errorMessage = "Erro ao processar a imagem: " + e.getMessage();
                ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            String errorMessage = "Território não encontrado com o ID: " + territoryId;
            ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/territory/update/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptionsRequest() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/territory/{id}")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public HttpStatus deleteTerritoryById(@PathVariable String id){
        return territoryService.deleteTerritory(id);
    }

}
