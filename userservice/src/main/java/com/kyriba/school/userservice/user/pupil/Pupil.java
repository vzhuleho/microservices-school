package com.kyriba.school.userservice.user.pupil;

import com.kyriba.school.userservice.user.User;
import com.kyriba.school.userservice.user.parent.ParentEntity;
import java.util.List;
import java.util.stream.Collectors;

public class Pupil extends User<PupilEntity> {

  public Pupil(PupilEntity root) {
    super(root);
  }

  public PupilOutputModel toOutputModel() {
    var id = root.getId();
    var info = toOutputModel(root.getInfo());
    var address = toOutputModel(root.getAddress());
    var pupilInfo = root.getPupilInfo();
    var parentIds = root.getParents().map(ParentEntity::getId).collect(Collectors.toList());
    return new PupilOutputModel(id, info, address, pupilInfo, parentIds);
  }

  public void update(PupilInputModel pupilModel, List<ParentEntity> parents) {
    super.update(pupilModel);
    root.updateInfo(pupilModel.getPupilInfo());
    root.changeParents(parents);
  }

}
