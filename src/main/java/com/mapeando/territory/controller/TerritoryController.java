package com.mapeando.territory.controller;

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
    public List<Territory> getTerritoryById(){
        return territoryService.getAllTerrytories();
    }

    @PutMapping(value = "/territory/update/{territoryId}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateTerritory(
            @PathVariable String territoryId,
            @ModelAttribute Territory territory,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        // Primeiro, busque o território existente no banco de dados com base no ID fornecido
        Optional<Territory> optionalTerritory = territoryService.getTerritoryById(territoryId);

        if (optionalTerritory.isPresent()) {

            Territory existingTerritory = optionalTerritory.get();

            try {
                // Trate a imagem (converta para Base64 ou salve em disco, se preferir)
                if (file != null) {
                    // Trate a imagem (converta para Base64 ou salve em disco, se preferir)
                    byte[] imageData = file.getBytes();
                    territory.setMainImage(imageData);
                    existingTerritory.setMainImage(territory.getMainImage());
                } else {
                    // Caso o file seja null, não altere o valor do mainImage no existingTerritory
                    existingTerritory.setMainImage(existingTerritory.getMainImage());
                }

                // Atualize apenas as propriedades do território que foram modificadas
                existingTerritory.setName(territory.getName());
                existingTerritory.setBriefDescription(territory.getBriefDescription());
                existingTerritory.setHistory(territory.getHistory());
                existingTerritory.setCartografia(territory.getCartografia());
                existingTerritory.setReligion(territory.getReligion());
                existingTerritory.setExtra_content(territory.getExtra_content());
                existingTerritory.setMap(territory.getMap());

                // Salve o território atualizado
                Territory savedTerritory = territoryService.updateTerritory(existingTerritory);
                return new ResponseEntity<>(savedTerritory, HttpStatus.CREATED);
            } catch (IOException e) {
                String errorMessage = "Erro ao processar a imagem: " + e.getMessage();
                ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Caso o território com o ID fornecido não seja encontrado, retorne uma resposta de erro
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

//    @GetMapping("/territory/all")
//    public ResponseEntity<?> getAllTerritories() {
//
//        try {
//            List<Territory> territories = territoryService.getAllTerrytories();
//            TerritoryResponse territoryResponse = new TerritoryResponse(territories);
//
//            return ResponseEntity.ok(territoryResponse);
//        } catch (Exception e) {
//            String errorMessage = "Erro ao retornar territórios " + e.getMessage();
//            ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
}
