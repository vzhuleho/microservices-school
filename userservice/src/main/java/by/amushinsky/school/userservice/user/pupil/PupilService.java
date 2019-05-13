package by.amushinsky.school.userservice.user.pupil;

import by.amushinsky.school.userservice.user.Identity;
import by.amushinsky.school.userservice.user.UserService;
import by.amushinsky.school.userservice.user.parent.ParentDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PupilService implements UserService<PupilDto> {

  @Override
  public PupilDto get(Identity identity) throws NoSuchElementException {
    return new PupilDto();
  }

  @Override
  public PupilDto add(PupilDto user) {
    return user;
  }

  @Override
  public PupilDto update(PupilDto user) throws NoSuchElementException {
    return user;
  }

  public List<ParentDto> getParents(Identity identity) throws NoSuchElementException {
    return Collections.emptyList();
  }
}
