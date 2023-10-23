package com.mapeando.territory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapeando.territory.entity.Coordinates;
import com.mapeando.territory.entity.Territory;
import com.mapeando.territory.repository.TerritoryRepository;
import com.mapeando.territory.service.TerritoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TerritoryController.class)
public class TerritoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TerritoryService territoryService;

    @Mock
    private TerritoryRepository territoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Territory territory;

    List<Territory> territoryList;

    @BeforeEach
    void setUp(){
        territory = new Territory( "sds", "dsas", "asda", "sdad", "dasdas", "sdfsdf", "34345", null, "asdasd", 0.0, 0.0, "dsad");
        territoryList = Arrays.asList(
                new Territory( "sds", "dsas", "asda", "sdad", "dasdas", "sdfsdf", "34345", null, "asdasd", 0.0, 0.0, "dsad"),
                new Territory( "sds", "dsas", "asda", "sdad", "dasdas", "sdfsdf", "34345", null, "asdasd", 0.0, 0.0, "dsad"));
    }

    @Test
    void shouldAddATerritoryAndReturn201() throws Exception {

        // mock image file
        byte[] imageData = "Imagem de teste".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE, imageData);

        when(territoryService.createTerritory(any(Territory.class))).thenReturn(territory);


        ResultActions result = mockMvc.perform(fileUpload("/api/territory-svc/territory/create")
                        .file(file)
                        .param("name", objectMapper.writeValueAsString(territory)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotAddATerritoryAndReturn400Error() throws Exception {

        // mock image file
        byte[] imageData = "Imagem de teste".getBytes();
        MockMultipartFile file = new MockMultipartFile("NotCorrectName", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE, imageData);

        when(territoryService.createTerritory(any(Territory.class))).thenReturn(territory);


        ResultActions result = mockMvc.perform(fileUpload("/api/territory-svc/territory/create")
                        .file(file)
                        .param("name", objectMapper.writeValueAsString(territory)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAllTerritories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/territory-svc/territory/all"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTerritory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/territory-svc/territory/{id}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void GetTerritoryByIdAndReturn200() throws Exception {
        when(territoryService.getTerritoryById(any())).thenReturn(Optional.ofNullable(territory));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/territory-svc/territory/64e6896839f06428575b"))
                .andExpect(status().isOk());
    }

    @Test
    void GetTerritoryByIdShouldReturnNotFound() throws Exception {
        when(territoryService.getTerritoryById(any())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/territory-svc/territory/64e6896839f06428575b"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testUpdateTerritory() throws Exception {

        byte[] imageData = "Imagem de teste".getBytes();
        // Crie um arquivo MockMultipartFile de exemplo
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE, imageData);

        // Configure o comportamento do mock do TerritoryService, se necessário
        when(territoryService.getTerritoryById("1")).thenReturn(Optional.of(territory));
        when(territoryService.updateTerritory(any(Territory.class))).thenReturn(territory);

        // Realize a solicitação PUT simulada com MockMvc
        ResultActions result = mockMvc.perform(put("/api/territory-svc/territory/update/{id}", "1")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(objectMapper.writeValueAsString(territory)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllTerritoriesCoordinates() throws Exception {

        // Crie uma lista de coordenadas simulada para retornar no mock do serviço
        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates("1", 40.0, 50.0, "Territory 1"));
        coordinatesList.add(new Coordinates("2", 35.0, 45.0, "Territory 2"));

        // Configure o comportamento do mock do TerritoryService para retornar a lista simulada
        when(territoryService.getTerritoryCoordidates()).thenReturn(coordinatesList);

        // Realize a solicitação GET simulada com MockMvc
        ResultActions resultActions = mockMvc.perform(get("/api/territory-svc/territory/coordinates"))
                .andExpect(status().isOk());
    }


}
