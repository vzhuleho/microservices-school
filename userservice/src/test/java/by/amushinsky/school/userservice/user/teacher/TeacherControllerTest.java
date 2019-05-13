package by.amushinsky.school.userservice.user.teacher;

import by.amushinsky.school.userservice.UserServiceApplication;
import by.amushinsky.school.userservice.user.Address;
import by.amushinsky.school.userservice.user.Money;
import by.amushinsky.school.userservice.user.Status;
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

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author M-AMU
 * @since 2019-05-13
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TeacherController.class)
@AutoConfigureRestDocs
public class TeacherControllerTest
{
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TeacherService teacherService;

  private ObjectMapper mapper = UserServiceApplication.objectMapper();

  @Test
  public void testGet() throws Exception
  {
    // given
    Mockito.when(teacherService.get(Mockito.any())).thenReturn(teacher());

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
    TeacherDto teacher = teacher();
    Mockito.when(teacherService.add(teacher)).then(inv -> {
      TeacherDto argument = inv.getArgument(0);
      argument.setId(13L);
      return argument;
    });

    // when & then
    System.out.println(mapper.writeValueAsString(teacher));
    mockMvc.perform(MockMvcRequestBuilders.post("/teachers")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(teacher)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("bonus.value").value(12000))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  @Test
  public void testUpdate() throws Exception
  {
    // given
    Mockito.when(teacherService.update(Mockito.any())).then(inv -> inv.getArgument(0));

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/teachers/13")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(teacher())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("bonus.currency").value("USD"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  private TeacherDto teacher()
  {
    TeacherDto teacherDto = new TeacherDto();
    teacherDto.setId(12L);
    teacherDto.setName("Vlad");
    teacherDto.setStatus(Status.ACTIVE);
    teacherDto.setBonus(new Money(new BigDecimal(12000), Currency.getInstance("USD")));
    teacherDto.setAddress(address());
    return teacherDto;
  }

  private Address address()
  {
    Address address = new Address();
    address.setCity("Minsk");
    address.setStreet("Shirokaja");
    address.setEmail("alex84789049@gmail.com");
    return address;
  }
}