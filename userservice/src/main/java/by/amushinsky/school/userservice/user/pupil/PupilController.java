package by.amushinsky.school.userservice.user.pupil;

import by.amushinsky.school.userservice.user.Identity;
import by.amushinsky.school.userservice.user.UserController;
import by.amushinsky.school.userservice.user.parent.ParentDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pupils")
public class PupilController extends UserController<PupilDto, PupilService> {

  public PupilController(PupilService pupilService) {
    super(pupilService);
  }

  @GetMapping("/{id}/parents")
  public List<ParentDto> getParents(Identity identity) throws NoSuchElementException
  {
    return service.getParents(identity);
  }
}
