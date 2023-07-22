package com.mapeando.territory.service;

import com.mapeando.territory.entity.Territory;
import com.mapeando.territory.repository.TerritoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class TerritoryService {

    @Autowired
    TerritoryRepository territoryRepository;
    public Territory createStudent(Territory territory) {
        byte[] imagemBytes = territory.getMainImage().getBytes();
        String imagemBase64 = Base64.getEncoder().encodeToString(imagemBytes);
        territory.setMainImage(imagemBase64);

        return territoryRepository.save(territory);
    }

    public Territory getTerritoryById(String id) {
        return territoryRepository.findById(id).get();
    }

    public List<Territory> getAllTerrytories() {
        return territoryRepository.findAll();
    }

    public Territory updateTerritory(Territory territory) {
        return territoryRepository.save(territory);
    }

    public HttpStatus deleteTerritory(String id) {
        try{
            territoryRepository.deleteById(id);
            return HttpStatus.OK;
        }catch (Exception ex){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }
}
