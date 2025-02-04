package ru.skillbox.hotel_booking_service.tests;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.hotel_booking_service.service.RoomService;
import ru.skillbox.hotel_booking_service.web.controller.RoomController;
import ru.skillbox.hotel_booking_service.web.model.*;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
class RoomControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RoomService roomService;

  @Test
  void findById_ShouldReturnRoomResponse() throws Exception {
    RoomResponse response = new RoomResponse();
    given(roomService.findById(1L)).willReturn(response);

    mockMvc.perform(get("/api/v1/rooms/1")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  void create_ShouldReturnCreatedResponse() throws Exception {
    UpsertRoomRequest request = new UpsertRoomRequest();
    request.setName("Deluxe Room");
    request.setDescription("A spacious deluxe room with a great view.");
    request.setRoomNumber("101");
    request.setPrice(new BigDecimal("150.00"));
    request.setMaxOccupancy(2);
    request.setUnavailableDates(List.of());

    RoomResponse response = new RoomResponse();
    response.setId(1L);
    response.setName("Deluxe Room");
    response.setDescription("A spacious deluxe room with a great view.");
    response.setRoomNumber("101");
    response.setPrice(new BigDecimal("150.00"));
    response.setMaxOccupancy(2);

    given(roomService.save(any(UpsertRoomRequest.class), eq(1L))).willReturn(response);

    mockMvc.perform(post("/api/v1/rooms?hotelId=1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                    {
                      "name": "Deluxe Room",
                      "description": "A spacious deluxe room with a great view.",
                      "roomNumber": "101",
                      "price": 150.00,
                      "maxOccupancy": 2,
                      "unavailableDates": []
                    }
                    """))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("Deluxe Room"))
      .andExpect(jsonPath("$.description").value("A spacious deluxe room with a great view."))
      .andExpect(jsonPath("$.roomNumber").value("101"))
      .andExpect(jsonPath("$.price").value(150.00))
      .andExpect(jsonPath("$.maxOccupancy").value(2));
  }

  @Test
  void update_ShouldReturnUpdatedRoom() throws Exception {
    UpdateRoomRequest request = new UpdateRoomRequest();
    request.setName("Updated Room");
    request.setDescription("An updated description for the room.");
    request.setPrice(new BigDecimal("180.00"));
    request.setMaxOccupancy(3);

    RoomResponse response = new RoomResponse();
    response.setId(1L);
    response.setName("Updated Room");
    response.setDescription("An updated description for the room.");
    response.setPrice(new BigDecimal("180.00"));
    response.setMaxOccupancy(3);

    given(roomService.update(eq(1L), any(UpdateRoomRequest.class))).willReturn(response);

    mockMvc.perform(put("/api/v1/rooms/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                    {
                      "name": "Updated Room",
                      "description": "An updated description for the room.",
                      "price": 180.00,
                      "maxOccupancy": 3
                    }
                    """))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("Updated Room"))
      .andExpect(jsonPath("$.description").value("An updated description for the room."))
      .andExpect(jsonPath("$.price").value(180.00))
      .andExpect(jsonPath("$.maxOccupancy").value(3));
  }


  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    doNothing().when(roomService).deleteById(1L);

    mockMvc.perform(delete("/api/v1/rooms/1"))
      .andExpect(status().isNoContent());
  }

  @Test
  void filterBy_ShouldReturnFilteredRooms() throws Exception {

    mockMvc.perform(post("/api/v1/rooms/filter")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                    {
                      "minPrice": 50.00,
                      "maxPrice": 300.00,
                      "maxOccupancy": 2
                    }
                    """))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.rooms").isArray())
      .andExpect(jsonPath("$.rooms.length()").value(2))
      .andExpect(jsonPath("$.rooms[0].id").value(1))
      .andExpect(jsonPath("$.rooms[0].name").value("Deluxe Room"))
      .andExpect(jsonPath("$.rooms[1].id").value(2))
      .andExpect(jsonPath("$.rooms[1].name").value("Suite"))
      .andExpect(jsonPath("$.totalElements").value(2))
      .andExpect(jsonPath("$.totalPages").value(1))
      .andExpect(jsonPath("$.currentPage").value(0))
      .andExpect(jsonPath("$.pageSize").value(10));
  }
}
