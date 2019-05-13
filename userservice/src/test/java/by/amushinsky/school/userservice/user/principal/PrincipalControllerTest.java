package by.amushinsky.school.userservice.user.principal;

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

/**
 * @author M-AMU
 * @since 2019-05-13
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PrincipalController.class)
@AutoConfigureRestDocs
public class PrincipalControllerTest
{
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PrincipalService principalService;

  private ObjectMapper mapper = UserServiceApplication.objectMapper();


  @Test
  public void testGet() throws Exception
  {
    // given
    Mockito.when(principalService.get(Mockito.any())).thenReturn(principal());

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
    PrincipalDto principal = principal();
    Mockito.when(principalService.add(principal)).then(inv -> {
      PrincipalDto argument = inv.getArgument(0);
      argument.setId(13L);
      return argument;
    });

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.post("/principals")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(principal)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("name").value("Max"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  @Test
  public void testUpdate() throws Exception
  {
    // given
    Mockito.when(principalService.update(Mockito.any())).then(inv -> inv.getArgument(0));

    // when & then
    mockMvc.perform(MockMvcRequestBuilders.put("/principals/13")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(mapper.writeValueAsString(principal())))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        .andExpect(MockMvcResultMatchers.jsonPath("address.street").value("Kirova"))
        .andExpect(MockMvcResultMatchers.jsonPath("id").value(13));
  }

  private PrincipalDto principal()
  {
    PrincipalDto principalDto = new PrincipalDto();
    principalDto.setId(12L);
    principalDto.setName("Max");
    principalDto.setStatus(Status.INACTIVE);
    principalDto.setAddress(address());
    return principalDto;
  }

  private Address address()
  {
    Address address = new Address();
    address.setCity("Brest");
    address.setStreet("Kirova");
    address.setEmail("max84789049@gmail.com");
    return address;
  }
}