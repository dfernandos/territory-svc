package com.mapeando.territory.service;

import com.mapeando.territory.entity.Coordinates;
import com.mapeando.territory.entity.Territory;
import com.mapeando.territory.repository.TerritoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TerritoryServiceTest {

    @Mock
    private TerritoryRepository repository;

    private AutoCloseable autoCloseable;

    private TerritoryService territoryService;

    private Territory territory;


    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        territoryService = new TerritoryService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void CreateTerrytorySuccessfully() {
        //Verify if the repository is called when I use the createTerritory Method
        //also checks if the save method is called with the correct argument
        // checks if the method save is being called one time
        territory = new Territory( "sds", "dsas", "asda", "sdad", "dasdas", "sdfsdf", "34345", null, "asdasd", 0.0, 0.0, "dsad");

        territoryService.createTerritory(territory);
        ArgumentCaptor<Territory> fairArgumentCaptor = ArgumentCaptor.forClass(Territory.class);
        verify(repository).save(fairArgumentCaptor.capture());

        Territory captureTerritory = fairArgumentCaptor.getValue();

        assertThat(captureTerritory).isEqualTo(territory);

        verify(repository, times(1)).save(territory);

    }

    @Test
    void deleteATerrytorySuccessfully() {
        territoryService.deleteTerritory("1");

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(repository).deleteById(argumentCaptor.capture());

        String capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument).isEqualTo("1");

        verify(repository, times(1)).deleteById(any());

    }

    @Test
    void getATerrytoryById() {
        territoryService.getTerritoryById("1");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(repository).findById(argumentCaptor.capture());

        String capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument).isEqualTo("1");
        verify(repository, times(1)).findById(any());

    }

    @Test
    void getAllTerritories() {
        territoryService.getAllTerrytories();
        verify(repository).findAll();
    }

    @Test
    void shouldReturnAListOfCoordinates() {
        Territory territory1 = new Territory( "sds", "dsas", "asda", "sdad", "dasdas", "sdfsdf", "34345", null, "asdasd", 2.0, 4.0, "dsad");
        Territory territory2 = new Territory( "sds", "dsas", "asda", "sdad", "dasdas", "sdfsdf", "34345", null, "asdasd", 4.0, 2.0, "dsad");

        List<Coordinates> expectedCoordinates = new ArrayList<>();
        expectedCoordinates.add(new Coordinates(territory1.getId(), territory1.getLatitude(), territory1.getLongitude(), territory1.getName()));
        expectedCoordinates.add(new Coordinates(territory2.getId(), territory2.getLatitude(), territory2.getLongitude(), territory2.getName()));

        when(repository.findAll()).thenReturn(Arrays.asList(territory1, territory2));

        List<Coordinates> territoryCoordinates = territoryService.getTerritoryCoordidates();

        assertEquals(2, territoryCoordinates.size());

        assertEquals(expectedCoordinates.get(0), territoryCoordinates.get(0));
        assertEquals(expectedCoordinates.get(1), territoryCoordinates.get(1));

        verify(repository, times(1)).findAll();

    }


}