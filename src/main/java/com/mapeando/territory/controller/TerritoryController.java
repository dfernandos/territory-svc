package com.mapeando.territory.controller;

import com.mapeando.territory.entity.Territory;
import com.mapeando.territory.service.TerritoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/territory-svc")
public class TerritoryController {

    @Autowired
    TerritoryService territoryService;

    @PostMapping("territory/create")
    public Territory createTerritory(@RequestBody Territory territory){
        return territoryService.createStudent(territory);
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
