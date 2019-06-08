package com.kyriba.school.userservice.user.pupil;

import com.kyriba.school.userservice.user.parent.ParentEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PupilFactory {

  public Pupil create(PupilEntity entity) {
    return new Pupil(entity);
  }

  public Pupil create(PupilInputModel pupilModel, List<ParentEntity> parents) {
    Pupil pupil = create(new PupilEntity());
    pupil.update(pupilModel, parents);
    return pupil;
  }

}
