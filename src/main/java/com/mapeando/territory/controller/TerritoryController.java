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
    public Territory getTerritoryById(@PathVariable String id){
        return territoryService.getTerritoryById(id);
    }

    @GetMapping("/territory/all")
    public List<Territory> getTerritoryById(){
        return territoryService.getAllTerrytories();
    }

    @PutMapping("/territory/update")
    public Territory updateTerritory(@RequestBody Territory territory){
        return territoryService.updateTerritory(territory);
    }

    @DeleteMapping("/territory/{id}")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public HttpStatus deleteTerritoryById(@PathVariable String id){
        return territoryService.deleteTerritory(id);
    }
}
