package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.NoSuchUserException;
import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.address.AddressInfo;
import javax.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(ParentController.class)
public class ParentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ParentService parentService;

  @Test
  public void testGet() throws Exception
  {
    // given
    Mockito.when(parentService.findById(Mockito.anyLong())).thenReturn(parentOut());

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.get("/parents/12"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(12));
  }


  @Test
  public void testAdd() throws Exception
  {
    // given
    Mockito.when(parentService.create(Mockito.any())).then(inv -> {
      ParentInputModel argument = inv.getArgument(0);
      return new ParentOutputModel(13L, argument.getUserInfo(), argument.getAddressInfo());
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.post("/parents")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\n"
            + "  \"id\": 1,\n"
            + "  \"userInfo\": {\n"
            + "    \"name\": \"Alex\",\n"
            + "    \"status\": \"ACTIVE\"\n"
            + "  },\n"
            + "  \"addressInfo\": {\n"
            + "    \"locationInfo\": {\n"
            + "      \"city\": \"Kletsk\",\n"
            + "      \"street\": \"N/A\",\n"
            + "      \"house\": \"N/A\",\n"
            + "      \"apartment\": \"N/A\"\n"
            + "    },\n"
            + "    \"postalInfo\": {\n"
            + "      \"zipCode\": \"N/A\"\n"
            + "    },\n"
            + "    \"communicationInfo\": {\n"
            + "      \"phoneNumber\": \"N/A\",\n"
            + "      \"email\": \"N/A\"\n"
            + "    }\n"
            + "  }\n"
            + "}"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("userInfo.name").value("Alex"))
        .andExpect(MockMvcResultMatchers.jsonPath("addressInfo.locationInfo.city")
            .value("Kletsk"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  @Test
  public void testAdd_validation_fails() throws Exception
  {
    // given
    ValidationException exception = new ValidationException("My message");
    Mockito.when(parentService.create(Mockito.any())).thenThrow(exception);

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.post("/parents")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\n"
            + "  \"id\": 1,\n"
            + "  \"userInfo\": {\n"
            + "    \"name\": \"Alex\",\n"
            + "    \"status\": \"ACTIVE\"\n"
            + "  },\n"
            + "  \"addressInfo\": {\n"
            + "    \"locationInfo\": {\n"
            + "      \"city\": \"Kletsk\",\n"
            + "      \"street\": \"N/A\",\n"
            + "      \"house\": \"N/A\",\n"
            + "      \"apartment\": \"N/A\"\n"
            + "    },\n"
            + "    \"postalInfo\": {\n"
            + "      \"zipCode\": \"N/A\"\n"
            + "    },\n"
            + "    \"communicationInfo\": {\n"
            + "      \"phoneNumber\": \"N/A\",\n"
            + "      \"email\": \"N/A\"\n"
            + "    }\n"
            + "  }\n"
            + "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("code").value("USER_NOT_VALID"))
        .andExpect(MockMvcResultMatchers.jsonPath("message").value("My message"));
  }

  @Test
  public void testUpdate_noSuchUser() throws Exception
  {
    // given
    NoSuchUserException exception = new NoSuchUserException(13);
    Mockito.when(parentService.update(Mockito.anyLong(), Mockito.any())).thenThrow(exception);

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/parents/13")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\n"
            + "  \"userInfo\": {\n"
            + "    \"name\": \"Smith\",\n"
            + "    \"status\": \"INACTIVE\"\n"
            + "  },\n"
            + "  \"addressInfo\": {\n"
            + "    \"locationInfo\": {\n"
            + "      \"city\": \"Minsk\",\n"
            + "      \"street\": \"N/A\",\n"
            + "      \"house\": \"N/A\",\n"
            + "      \"apartment\": \"N/A\"\n"
            + "    },\n"
            + "    \"postalInfo\": {\n"
            + "      \"zipCode\": \"435345\"\n"
            + "    },\n"
            + "    \"communicationInfo\": {\n"
            + "      \"phoneNumber\": \"N/A\",\n"
            + "      \"email\": \"N/A\"\n"
            + "    }\n"
            + "  }\n"
            + "}"))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("code").value("USER_NOT_FOUND"))
        .andExpect(MockMvcResultMatchers.jsonPath("message").value("User with id 13 doesn't exist"));
  }

  @Test
  public void testUpdate() throws Exception
  {
    // given
    Mockito.when(parentService.update(Mockito.anyLong(), Mockito.any())).then(inv -> {
      long id = inv.getArgument(0);
      ParentInputModel in = inv.getArgument(1);
      return new ParentOutputModel(id, in.getUserInfo(), in.getAddressInfo());
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/parents/13")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\n"
            + "  \"userInfo\": {\n"
            + "    \"name\": \"Smith\",\n"
            + "    \"status\": \"INACTIVE\"\n"
            + "  },\n"
            + "  \"addressInfo\": {\n"
            + "    \"locationInfo\": {\n"
            + "      \"city\": \"Minsk\",\n"
            + "      \"street\": \"N/A\",\n"
            + "      \"house\": \"N/A\",\n"
            + "      \"apartment\": \"N/A\"\n"
            + "    },\n"
            + "    \"postalInfo\": {\n"
            + "      \"zipCode\": \"435345\"\n"
            + "    },\n"
            + "    \"communicationInfo\": {\n"
            + "      \"phoneNumber\": \"N/A\",\n"
            + "      \"email\": \"N/A\"\n"
            + "    }\n"
            + "  }\n"
            + "}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("addressInfo.postalInfo.zipCode")
            .value("435345"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13L));
  }

  private ParentOutputModel parentOut()
  {
    return new ParentOutputModel(12, new UserInfo(), new AddressInfo());
  }

}