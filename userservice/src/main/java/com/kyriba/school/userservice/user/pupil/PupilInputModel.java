package com.kyriba.school.userservice.user.pupil;

import com.kyriba.school.userservice.user.UserInfo;
import com.kyriba.school.userservice.user.UserInputModel;
import com.kyriba.school.userservice.user.address.AddressInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PupilInputModel extends UserInputModel {

  private PupilInfo pupilInfo = new PupilInfo();

  private List<Long> parentIds = Collections.emptyList();

  public PupilInputModel(long id, UserInfo userInfo, AddressInfo addressInfo, PupilInfo pupilInfo,
      List<Long> parentIds) {
    super(userInfo, addressInfo);
    this.pupilInfo = pupilInfo;
    this.parentIds = new ArrayList<>(parentIds);
  }

}
