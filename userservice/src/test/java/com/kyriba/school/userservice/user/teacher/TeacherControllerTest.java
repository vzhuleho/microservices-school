package com.kyriba.school.userservice.user.teacher;

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
@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TeacherService teacherService;

  @Test
  public void testGet() throws Exception
  {
    // given
    Mockito.when(teacherService.findById(Mockito.anyLong())).thenReturn(pupilOut());

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.get("/teachers/12"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(12));
  }


  @Test
  public void testAdd() throws Exception
  {
    // given
    Mockito.when(teacherService.create(Mockito.any())).then(inv -> {
      TeacherInputModel argument = inv.getArgument(0);
      return new TeacherOutputModel(13L, argument.getUserInfo(), argument.getAddressInfo(),
          argument.getTeacherInfo());
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.post("/teachers")
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
            + "  \"teacherInfo\": {\n"
            + "    \"passportNumber\": \"AB43242342343\",\n"
            + "    \"salary\": 1100.12,\n"
            + "    \"bonus\": 500\n"
            + "  }\n"
            + "}"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("teacherInfo.passportNumber")
            .value("AB43242342343"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  @Test
  public void testUpdate() throws Exception
  {
    // given
    Mockito.when(teacherService.update(Mockito.anyLong(), Mockito.any())).then(inv -> {
      long id = inv.getArgument(0);
      TeacherInputModel in = inv.getArgument(1);
      return new TeacherOutputModel(id, in.getUserInfo(), in.getAddressInfo(), in.getTeacherInfo());
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/teachers/13")
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
            + "  \"teacherInfo\": {\n"
            + "    \"passportNumber\": \"AB43242342343\",\n"
            + "    \"salary\": 1100.12,\n"
            + "    \"bonus\": 500\n"
            + "  }\n"
            + "}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("teacherInfo.salary").value(1100.12))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13L));
  }

  private TeacherOutputModel pupilOut()
  {
    return new TeacherOutputModel(12, new UserInfo(), new AddressInfo(), new TeacherInfo());
  }

}