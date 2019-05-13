package by.amushinsky.school.userservice.user;

import java.util.NoSuchElementException;

public interface UserService<T extends UserDto> {

  T get(Identity identity) throws NoSuchElementException;

  T add(T user);

  T update(T user) throws NoSuchElementException;

}
