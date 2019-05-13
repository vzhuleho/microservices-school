package by.amushinsky.school.userservice.user.parent;

import by.amushinsky.school.userservice.UserServiceApplication;
import by.amushinsky.school.userservice.user.Address;
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


@RunWith(SpringRunner.class)
@WebMvcTest(ParentController.class)
@AutoConfigureRestDocs
public class ParentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ParentService parentService;

  private ObjectMapper mapper = UserServiceApplication.objectMapper();

  @Test
  public void testGet() throws Exception
  {
    // given
    Mockito.when(parentService.get(Mockito.any())).thenReturn(parent());

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
    ParentDto parent = parent();
    Mockito.when(parentService.add(parent)).then(inv -> {
      ParentDto argument = inv.getArgument(0);
      argument.setId(13L);
      return argument;
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.post("/parents")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(parent)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("name").value("Alex"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  @Test
  public void testUpdate() throws Exception
  {
    // given
    Mockito.when(parentService.update(Mockito.any())).then(inv -> inv.getArgument(0));

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/parents/13")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(parent())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("address.city").value("Minsk"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  private ParentDto parent()
  {
    ParentDto parentDto = new ParentDto();
    parentDto.setId(12L);
    parentDto.setName("Alex");
    parentDto.setStatus(Status.ACTIVE);
    parentDto.setAddress(address());
    return parentDto;
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