package by.amushinsky.school.userservice.user.teacher;

import by.amushinsky.school.userservice.user.Identity;
import by.amushinsky.school.userservice.user.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TeacherService implements UserService<TeacherDto> {

  @Override
  public TeacherDto get(Identity identity) throws NoSuchElementException {
    return new TeacherDto();
  }

  @Override
  public TeacherDto add(TeacherDto user) {
    return user;
  }

  @Override
  public TeacherDto update(TeacherDto user) throws NoSuchElementException {
    return user;
  }

}
