package com.kyriba.school.userservice.user.pupil;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.address.AddressInfo;
import java.util.Collections;
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
@WebMvcTest(PupilController.class)
public class PupilControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PupilService pupilService;

  @Test
  public void testGet() throws Exception
  {
    // given
    Mockito.when(pupilService.findById(Mockito.anyLong())).thenReturn(pupilOut());

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.get("/pupils/12"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(12));
  }


  @Test
  public void testAdd() throws Exception
  {
    // given
    Mockito.when(pupilService.create(Mockito.any())).then(inv -> {
      PupilInputModel argument = inv.getArgument(0);
      return new PupilOutputModel(13L, argument.getUserInfo(), argument.getAddressInfo(),
          argument.getPupilInfo(), argument.getParentIds());
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.post("/pupils")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\n"
            + "  \"userInfo\": {\n"
            + "    \"name\": \"Pavel\",\n"
            + "    \"status\": \"ACTIVE\"\n"
            + "  },\n"
            + "  \"addressInfo\": {\n"
            + "    \"locationInfo\": {\n"
            + "      \"city\": \"Kletsk\",\n"
            + "      \"street\": \"N/A\",\n"
            + "      \"house\": \"N/A\",\n"
            + "      \"apartment\": \"123\"\n"
            + "    },\n"
            + "    \"postalInfo\": {\n"
            + "      \"zipCode\": \"12432423\"\n"
            + "    },\n"
            + "    \"communicationInfo\": {\n"
            + "      \"phoneNumber\": \"+34654654645\",\n"
            + "      \"email\": \"N/A\"\n"
            + "    }\n"
            + "  },\n"
            + "  \"pupilInfo\": {\n"
            + "    \"birthDate\": \"1994-12-02\",\n"
            + "    \"grade\": 11\n"
            + "  },\n"
            + "  \"parentIds\": [\n"
            + "    1, 3\n"
            + "  ]\n"
            + "}"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("parentIds[0]").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("pupilInfo.birthDate").value("1994-12-02"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  @Test
  public void testUpdate() throws Exception
  {
    // given
    Mockito.when(pupilService.update(Mockito.anyLong(), Mockito.any())).then(inv -> {
      long id = inv.getArgument(0);
      PupilInputModel in = inv.getArgument(1);
      return new PupilOutputModel(id, in.getUserInfo(), in.getAddressInfo(), in.getPupilInfo(),
          in.getParentIds());
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/pupils/13")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content("{\n"
            + "  \"userInfo\": {\n"
            + "    \"name\": \"Pavel\",\n"
            + "    \"status\": \"ACTIVE\"\n"
            + "  },\n"
            + "  \"addressInfo\": {\n"
            + "    \"locationInfo\": {\n"
            + "      \"city\": \"Kletsk\",\n"
            + "      \"street\": \"N/A\",\n"
            + "      \"house\": \"N/A\",\n"
            + "      \"apartment\": \"123\"\n"
            + "    },\n"
            + "    \"postalInfo\": {\n"
            + "      \"zipCode\": \"12432423\"\n"
            + "    },\n"
            + "    \"communicationInfo\": {\n"
            + "      \"phoneNumber\": \"+34654654645\",\n"
            + "      \"email\": \"N/A\"\n"
            + "    }\n"
            + "  },\n"
            + "  \"pupilInfo\": {\n"
            + "    \"birthDate\": \"1994-12-02\",\n"
            + "    \"grade\": 11\n"
            + "  },\n"
            + "  \"parentIds\": [\n"
            + "    1, 3\n"
            + "  ]\n"
            + "}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("addressInfo.communicationInfo.phoneNumber")
            .value("+34654654645"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13L));
  }

  private PupilOutputModel pupilOut()
  {
    return new PupilOutputModel(12, new UserInfo(), new AddressInfo(), new PupilInfo(),
        Collections.emptyList());
  }

}