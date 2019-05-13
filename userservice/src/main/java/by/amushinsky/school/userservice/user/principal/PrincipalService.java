package by.amushinsky.school.userservice.user.principal;

import by.amushinsky.school.userservice.user.Identity;
import by.amushinsky.school.userservice.user.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PrincipalService implements UserService<PrincipalDto> {

  @Override
  public PrincipalDto get(Identity identity) throws NoSuchElementException {
    return new PrincipalDto();
  }

  @Override
  public PrincipalDto add(PrincipalDto user) {
    return user;
  }

  @Override
  public PrincipalDto update(PrincipalDto user) throws NoSuchElementException {
    return user;
  }

}
