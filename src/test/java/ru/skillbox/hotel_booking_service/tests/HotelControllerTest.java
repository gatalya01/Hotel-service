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
import ru.skillbox.hotel_booking_service.service.HotelService;
import ru.skillbox.hotel_booking_service.web.controller.HotelController;
import ru.skillbox.hotel_booking_service.web.model.*;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HotelController.class)
class HotelControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private HotelService hotelService;

  @Test
  void findAll_ShouldReturnHotelList() throws Exception {
    HotelListResponse response = new HotelListResponse();
    given(hotelService.findAll(0, 10)).willReturn(response);

    mockMvc.perform(get("/api/v1/hotels?page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  void findById_ShouldReturnHotelResponse() throws Exception {
    HotelResponse response = new HotelResponse();
    given(hotelService.findById(1L)).willReturn(response);

    mockMvc.perform(get("/api/v1/hotels/1")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  void create_ShouldReturnCreatedResponse() throws Exception {
    UpsertHotelRequest request = new UpsertHotelRequest();
    request.setName("Grand Hotel");
    request.setAnnouncement("Luxury hotel in the city center");
    request.setCity("New York");
    request.setAddress("123 Main St, New York, NY 10001");
    request.setDistanceFromCityCenter(2.5);

    HotelResponse response = new HotelResponse();
    response.setId(1L);
    response.setName("Grand Hotel");
    response.setAnnouncement("Luxury hotel in the city center");
    response.setCity("New York");
    response.setAddress("123 Main St, New York, NY 10001");
    response.setDistanceFromCityCenter(2.5);

    given(hotelService.save(any(UpsertHotelRequest.class))).willReturn(response);

    mockMvc.perform(post("/api/v1/hotels")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                    {
                      "name": "Grand Hotel",
                      "announcement": "Luxury hotel in the city center",
                      "city": "New York",
                      "address": "123 Main St, New York, NY 10001",
                      "distanceFromCityCenter": 2.5
                    }
                    """))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("Grand Hotel"))
      .andExpect(jsonPath("$.announcement").value("Luxury hotel in the city center"))
      .andExpect(jsonPath("$.city").value("New York"))
      .andExpect(jsonPath("$.address").value("123 Main St, New York, NY 10001"))
      .andExpect(jsonPath("$.distanceFromCityCenter").value(2.5));
  }

  @Test
  void update_ShouldReturnUpdatedHotel() throws Exception {
    UpdateHotelRequest request = new UpdateHotelRequest();
    request.setName("Updated Grand Hotel");
    request.setAnnouncement("Recently renovated luxury hotel");
    request.setCity("Los Angeles");
    request.setAddress("456 Sunset Blvd, Los Angeles, CA 90028");
    request.setDistanceFromCityCenter(3.2);

    HotelResponse response = new HotelResponse();
    response.setId(1L);
    response.setName("Updated Grand Hotel");
    response.setAnnouncement("Recently renovated luxury hotel");
    response.setCity("Los Angeles");
    response.setAddress("456 Sunset Blvd, Los Angeles, CA 90028");
    response.setDistanceFromCityCenter(3.2);

    given(hotelService.update(eq(1L), any(UpdateHotelRequest.class))).willReturn(response);

    mockMvc.perform(put("/api/v1/hotels/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                    {
                      "name": "Updated Grand Hotel",
                      "announcement": "Recently renovated luxury hotel",
                      "city": "Los Angeles",
                      "address": "456 Sunset Blvd, Los Angeles, CA 90028",
                      "distanceFromCityCenter": 3.2
                    }
                    """))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value("Updated Grand Hotel"))
      .andExpect(jsonPath("$.announcement").value("Recently renovated luxury hotel"))
      .andExpect(jsonPath("$.city").value("Los Angeles"))
      .andExpect(jsonPath("$.address").value("456 Sunset Blvd, Los Angeles, CA 90028"))
      .andExpect(jsonPath("$.distanceFromCityCenter").value(3.2));
  }

  @Test
  void delete_ShouldReturnNoContent() throws Exception {
    doNothing().when(hotelService).deleteById(1L);

    mockMvc.perform(delete("/api/v1/hotels/1"))
      .andExpect(status().isNoContent());
  }

  @Test
  void addMark_ShouldReturnSuccessMessage() throws Exception {
    doNothing().when(hotelService).updateHotelRating(1L, 4.5);

    mockMvc.perform(post("/api/v1/hotels/1/mark?rating=4.5"))
      .andExpect(status().isOk())
      .andExpect(content().string("Mark is successfully added. Thank you!"));
  }

  @Test
  void filterBy_ShouldReturnFilteredHotels() throws Exception {
    HotelFilter filter = new HotelFilter();
    filter.setCity("Los Angeles");
    filter.setDistanceFromCityCenter(5.0);

    HotelListResponse response = new HotelListResponse();

    response.setTotalElements(2);
    response.setTotalPages(1);
    response.setCurrentPage(0);
    response.setPageSize(10);

    given(hotelService.filterBy(any(HotelFilter.class))).willReturn(response);

    mockMvc.perform(post("/api/v1/hotels/filter")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                {
                  "city": "Los Angeles",
                  "distanceFromCityCenter": 5.0
                }
                """))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.hotels").isArray())
      .andExpect(jsonPath("$.hotels.length()").value(2))
      .andExpect(jsonPath("$.hotels[0].id").value(1))
      .andExpect(jsonPath("$.hotels[0].name").value("Grand Hotel"))
      .andExpect(jsonPath("$.hotels[0].announcement").value("Luxury hotel"))
      .andExpect(jsonPath("$.hotels[0].city").value("Los Angeles"))
      .andExpect(jsonPath("$.hotels[0].address").value("123 Main St, Los Angeles, CA 90001"))
      .andExpect(jsonPath("$.hotels[0].distanceFromCityCenter").value(2.5))
      .andExpect(jsonPath("$.hotels[1].id").value(2))
      .andExpect(jsonPath("$.hotels[1].name").value("Sunset Inn"))
      .andExpect(jsonPath("$.hotels[1].announcement").value("Cozy boutique hotel"))
      .andExpect(jsonPath("$.hotels[1].city").value("Los Angeles"))
      .andExpect(jsonPath("$.hotels[1].address").value("456 Sunset Blvd, Los Angeles, CA 90028"))
      .andExpect(jsonPath("$.hotels[1].distanceFromCityCenter").value(3.2))
      .andExpect(jsonPath("$.totalElements").value(2))
      .andExpect(jsonPath("$.totalPages").value(1))
      .andExpect(jsonPath("$.currentPage").value(0))
      .andExpect(jsonPath("$.pageSize").value(10));
  }
}
