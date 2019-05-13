package by.amushinsky.school.userservice.user.parent;

import by.amushinsky.school.userservice.user.Identity;
import by.amushinsky.school.userservice.user.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ParentService implements UserService<ParentDto> {

  @Override
  public ParentDto get(Identity identity) throws NoSuchElementException {
    return new ParentDto();
  }

  @Override
  public ParentDto add(ParentDto user) {
    return user;
  }

  @Override
  public ParentDto update(ParentDto user) throws NoSuchElementException {
    return user;
  }

}
