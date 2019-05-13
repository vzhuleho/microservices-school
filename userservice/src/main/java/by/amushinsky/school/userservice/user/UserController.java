package by.amushinsky.school.userservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

public abstract class UserController<T extends UserDto, S extends UserService<T>> {

  protected final S service;

  protected UserController(S service) {
    this.service = service;
  }

  @GetMapping("/{id}")
  public T get(Identity identity) throws NoSuchElementException {
    return service.get(identity);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public T add(@RequestBody T user) {
    return service.add(user);
  }

  @PutMapping(value = "/{id}")
  public T update(Identity identity, @RequestBody T user) throws NoSuchElementException {
    user.setId(identity.getId());
    return service.update(user);
  }
}
