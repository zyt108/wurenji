package com.md.basePlatform.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.md.basePlatform.domain.Drone;
import com.md.basePlatform.service.DroneService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DroneController.class)
class DroneControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DroneService droneService;

    @Test
    void should_returnJsonList_when_callApi() throws Exception {
        when(droneService.list("D", null)).thenReturn(Collections.singletonList(new Drone()));
        mockMvc.perform(get("/drones/api").param("keyword", "D"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void should_returnDrone_when_getById() throws Exception {
        Drone drone = new Drone();
        drone.setId(1L);
        drone.setUavCode("UAV-1");
        when(droneService.get(1L)).thenReturn(drone);
        mockMvc.perform(get("/drones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.uavCode").value("UAV-1"));
    }

    @Test
    void should_deleteDrone_when_callDelete() throws Exception {
        mockMvc.perform(delete("/drones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(droneService).delete(1L);
    }
}
