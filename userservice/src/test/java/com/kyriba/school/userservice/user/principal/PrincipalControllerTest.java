package com.kyriba.school.userservice.user.principal;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.address.AddressInfo;
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
@WebMvcTest(PrincipalController.class)
public class PrincipalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PrincipalService principalService;

  @Test
  public void testGet() throws Exception
  {
    // given
    Mockito.when(principalService.findById(Mockito.anyLong())).thenReturn(principalOut());

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.get("/principals/12"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(12));
  }


  @Test
  public void testAdd() throws Exception
  {
    // given
    Mockito.when(principalService.create(Mockito.any())).then(inv -> {
      PrincipalInputModel argument = inv.getArgument(0);
      return new PrincipalOutputModel(13L, argument.getUserInfo(), argument.getAddressInfo());
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.post("/principals")
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
  public void testUpdate() throws Exception
  {
    // given
    Mockito.when(principalService.update(Mockito.anyLong(), Mockito.any())).then(inv -> {
      long id = inv.getArgument(0);
      PrincipalInputModel in = inv.getArgument(1);
      return new PrincipalOutputModel(id, in.getUserInfo(), in.getAddressInfo());
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/principals/13")
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

  private PrincipalOutputModel principalOut()
  {
    return new PrincipalOutputModel(12, new UserInfo(), new AddressInfo());
  }

}