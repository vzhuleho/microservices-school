package by.amushinsky.school.userservice.user.pupil;

import by.amushinsky.school.userservice.UserServiceApplication;
import by.amushinsky.school.userservice.user.Address;
import by.amushinsky.school.userservice.user.Status;
import by.amushinsky.school.userservice.user.parent.ParentDto;
import by.amushinsky.school.userservice.user.principal.PrincipalDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author M-AMU
 * @since 2019-05-13
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PupilController.class)
@AutoConfigureRestDocs
public class PupilControllerTest
{
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PupilService pupilService;

  private ObjectMapper mapper = UserServiceApplication.objectMapper();

  private LocalDate localDate = LocalDate.of(2012, 12, 12);

  @Test
  public void testGet() throws Exception
  {
    // given
    Mockito.when(pupilService.get(Mockito.any())).thenReturn(pupil());

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.get("/pupils/12"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(12));
  }

  @Test
  public void testGetParents() throws Exception
  {
    // given
    Mockito.when(pupilService.getParents(Mockito.any())).thenReturn(Arrays.asList(parent(), parent()));

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.get("/pupils/12/parents"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
  }

  @Test
  public void testAdd() throws Exception
  {
    // given
    PupilDto pupil = pupil();
    Mockito.when(pupilService.add(pupil)).then(inv -> {
      PupilDto argument = inv.getArgument(0);
      argument.setId(13L);
      return argument;
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.post("/pupils")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(pupil)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("birthDate").value("2012-12-12"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  @Test
  public void testUpdate() throws Exception
  {
    // given
    Mockito.when(pupilService.update(Mockito.any())).then(inv -> inv.getArgument(0));

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/pupils/13")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(pupil())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("grade").value(10))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  private PupilDto pupil()
  {
    PupilDto pupilDto = new PupilDto();
    pupilDto.setId(12L);
    pupilDto.setStatus(Status.INACTIVE);
    pupilDto.setAddress(address());
    pupilDto.setBirthDate(localDate);
    pupilDto.setGrade(10);
    return pupilDto;
  }

  private ParentDto parent()
  {
    ParentDto parentDto = new ParentDto();
    parentDto.setId(1L);
    parentDto.setName("Alex");
    parentDto.setStatus(Status.ACTIVE);
    parentDto.setAddress(address());
    return parentDto;
  }

  private Address address()
  {
    Address address = new Address();
    address.setCity("Grodno");
    return address;
  }

}