package com.md.basePlatform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.md.basePlatform.domain.Drone;
import com.md.basePlatform.domain.DroneForm;
import com.md.basePlatform.exception.BusinessException;
import com.md.basePlatform.repository.DroneRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private AiAttributeService aiAttributeService;

    @InjectMocks
    private DroneService droneService;

    @Test
    void should_createDrone_when_formValid() {
        when(droneRepository.countByUavCode(anyString())).thenReturn(0);
        DroneForm form = form();
        Drone result = droneService.create(form);
        assertEquals("UAV-001", result.getUavCode());
        verify(aiAttributeService, times(1)).enrich(any(Drone.class));
        verify(droneRepository, times(1)).insert(any(Drone.class));
    }

    @Test
    void should_throwException_when_getNotFound() {
        when(droneRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> droneService.get(100L));
    }

    @Test
    void should_returnList_when_queryAll() {
        when(droneRepository.findAll(null, null)).thenReturn(Collections.singletonList(new Drone()));
        assertEquals(1, droneService.list(null, null).size());
    }

    @Test
    void should_throw_when_duplicateUavCodeOnCreate() {
        when(droneRepository.countByUavCode("UAV-001")).thenReturn(1);
        assertThrows(BusinessException.class, () -> droneService.create(form()));
    }

    @Test
    void should_updateDrone_when_exists() {
        Drone existing = new Drone();
        existing.setId(1L);
        when(droneRepository.countByUavCodeExcludeId(anyString(), anyLong())).thenReturn(0);
        when(droneRepository.findById(1L)).thenReturn(Optional.of(existing));
        Drone updated = droneService.update(1L, form());
        assertEquals(1L, updated.getId());
        verify(droneRepository).update(any(Drone.class));
    }

    @Test
    void should_throw_when_duplicateUavCodeOnUpdate() {
        when(droneRepository.countByUavCodeExcludeId(anyString(), anyLong())).thenReturn(1);
        assertThrows(BusinessException.class, () -> droneService.update(1L, form()));
    }

    @Test
    void should_deleteDrone_when_exists() {
        when(droneRepository.deleteById(1L)).thenReturn(1);
        droneService.delete(1L);
        verify(droneRepository).deleteById(1L);
    }

    private DroneForm form() {
        DroneForm form = new DroneForm();
        form.setUavCode("UAV-001");
        form.setModel("M1");
        form.setManufacturer("DJI");
        form.setMaxPayload(12.0);
        form.setMaxAltitude(500);
        form.setMaxFlightTime(45);
        form.setMaxSpeed(12.5);
        form.setWingspan(30.0);
        form.setWeight(1.2);
        form.setStatus(1);
        return form;
    }
}
