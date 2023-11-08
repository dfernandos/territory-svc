package com.mapeando.territory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mapeando.territory.config.FirebaseAuthentication;
import com.mapeando.territory.config.FirebaseConfig;
import com.mapeando.territory.entity.Coordinates;
import com.mapeando.territory.entity.Territory;
import com.mapeando.territory.repository.TerritoryRepository;
import com.mapeando.territory.service.TerritoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TerritoryController.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({FirebaseAuthentication.class})
class TerritoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TerritoryService territoryService;

    @Mock
    private TerritoryRepository territoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FirebaseAuthentication firebaseAuthentication;

    private Territory territory;

    List<Territory> territoryList;

    @BeforeEach
    void setUp() throws FirebaseAuthException {

//        FirebaseToken mockFirebaseToken = PowerMockito.mock(FirebaseToken.class);
//
//// Mock the behavior of a specific method from the FirebaseToken class
//        when(mockFirebaseToken.getUid()).thenReturn("mockedValue");
//
//        PowerMockito.mockStatic(FirebaseAuthentication.class);
//        PowerMockito.when(firebaseAuthentication.validateFirebaseToken(anyString())).thenReturn(mockFirebaseToken);

        territory = new Territory( "id", "name", "description", "history", "cartography", "religion", "content", null, "reference", 0.0, 0.0, "site");
        territoryList = Arrays.asList(
                new Territory( "id", "name", "description", "history", "cartography", "religion", "content", null, "reference", 0.0, 0.0, "site"),
                new Territory( "id", "name", "description", "history", "cartography", "religion", "content", null, "reference", 0.0, 0.0, "site"));
    }

    @Test
    public void testCreateTerritorySuccess() throws Exception {
        // Mock FirebaseToken
        FirebaseToken mockFirebaseToken = mock(FirebaseToken.class);
        when(firebaseAuthentication.validateFirebaseToken(Mockito.anyString())).thenReturn(mockFirebaseToken);


        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());

        Territory territory = new Territory();
        territory.setName("Sample Territory");

        when(territoryService.createTerritory(any(Territory.class))).thenReturn(territory);

        ResultActions result = mockMvc.perform(fileUpload("/api/territory-svc/territory/create")
                        .file(file)
                        .param("name", objectMapper.writeValueAsString(territory))
                        .header("Authorization", "Bearer myToken"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldNotAddATerritoryAndReturn400Error() throws Exception {

        // mock image file
        byte[] imageData = "image test".getBytes();
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
        MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", MediaType.IMAGE_JPEG_VALUE, imageData);

        when(territoryService.getTerritoryById("1")).thenReturn(Optional.of(territory));
        when(territoryService.updateTerritory(any(Territory.class))).thenReturn(territory);

        ResultActions result = mockMvc.perform(put("/api/territory-svc/territory/update/{id}", "1")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(objectMapper.writeValueAsString(territory)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllTerritoriesCoordinates() throws Exception {

        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates("1", 40.0, 50.0, "Territory 1"));
        coordinatesList.add(new Coordinates("2", 35.0, 45.0, "Territory 2"));

        when(territoryService.getTerritoryCoordidates()).thenReturn(coordinatesList);

        ResultActions resultActions = mockMvc.perform(get("/api/territory-svc/territory/coordinates"))
                .andExpect(status().isOk());
    }


}
