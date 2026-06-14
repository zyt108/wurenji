package com.md.basePlatform.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.md.basePlatform.common.ApiResponse;
import com.md.basePlatform.domain.Drone;
import com.md.basePlatform.domain.DroneForm;
import com.md.basePlatform.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DroneControllerPostMvcTest {

    @Mock
    private DroneService droneService;

    private DroneController controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new DroneController(droneService);
    }

    private DroneForm filledForm() {
        DroneForm f = new DroneForm();
        f.setUavCode("UAV-2026-001");
        f.setModel("M2");
        f.setManufacturer("DJI");
        f.setMaxPayload(8.0);
        f.setMaxAltitude(500);
        f.setMaxFlightTime(30);
        f.setMaxSpeed(12.5);
        f.setWingspan(30.0);
        f.setWeight(1.2);
        f.setStatus(1);
        return f;
    }

    @Test
    void should_returnDrone_when_createDrone() {
        when(droneService.create(any())).thenReturn(new Drone());
        DroneForm form = filledForm();
        ApiResponse<Drone> response = controller.create(form);
        assertNotNull(response);
        verify(droneService).create(any());
    }

    @Test
    void should_returnDrone_when_updateDrone() {
        when(droneService.update(any(Long.class), any())).thenReturn(new Drone());
        DroneForm form = filledForm();
        form.setId(1L);
        ApiResponse<Drone> response = controller.update(1L, form);
        assertNotNull(response);
        verify(droneService).update(any(Long.class), any());
    }
}
