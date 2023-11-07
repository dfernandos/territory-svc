package com.mapeando.territory.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mapeando.territory.config.FirebaseAuthentication;
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

    @Autowired
    FirebaseAuthentication firebaseAuthentication;

    @PostMapping("/territory/create")
    public ResponseEntity<?> createTerritory(@RequestHeader("Authorization") String idToken, @RequestParam("file") MultipartFile file,
                                             @ModelAttribute Territory territory) {
        try {

            String idTokenParsed = idToken.substring(7);
            FirebaseToken decodedToken = firebaseAuthentication.validateFirebaseToken(idTokenParsed);

            byte[] imageData = file.getBytes();
            territory.setMainImage(imageData);

            Territory savedTerritory = territoryService.createTerritory(territory);
            return new ResponseEntity<>(savedTerritory, HttpStatus.CREATED);
        } catch (IOException e) {
            String errorMessage = "Erro ao processar a imagem: " + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Token inválido ou expirado. Faça o login novamente.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/territory/{id}")
     public ResponseEntity<?> getTerritoryById(@PathVariable String id){
        try{

            Optional<Territory> territoryDB = territoryService.getTerritoryById(id);
            return new ResponseEntity<>(territoryDB.get(), HttpStatus.OK);
        }catch (Exception ex){
            String errorMessage = "Território não encontrado: " + ex.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
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
            @RequestHeader("Authorization") String idToken,
            @ModelAttribute Territory territory,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Optional<Territory> optionalTerritory = territoryService.getTerritoryById(territoryId);

        if (optionalTerritory.isPresent()) {

            Territory existingTerritory = optionalTerritory.get();

            try {
                String idTokenParsed = idToken.substring(7);
                FirebaseToken decodedToken = firebaseAuthentication.validateFirebaseToken(idTokenParsed);

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
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Token inválido ou expirado. Faça o login novamente.", HttpStatus.UNAUTHORIZED);
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
    public HttpStatus deleteTerritoryById(@PathVariable String id, @RequestHeader("Authorization") String idToken){

        try{
            String idTokenParsed = idToken.substring(7);
            FirebaseToken decodedToken = firebaseAuthentication.validateFirebaseToken(idTokenParsed);
            return territoryService.deleteTerritory(id);
        }catch (Exception ex){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
