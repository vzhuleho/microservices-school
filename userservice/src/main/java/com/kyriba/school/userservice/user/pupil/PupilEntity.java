package com.kyriba.school.userservice.user.pupil;

import com.kyriba.school.userservice.user.UserEntity;
import com.kyriba.school.userservice.user.parent.ParentEntity;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@DiscriminatorValue("ppl")
@Table(name = "pupil")
public class PupilEntity extends UserEntity {

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date")),
      @AttributeOverride(name = "grade", column = @Column(name = "grade")),
  })
  @Getter
  private PupilInfo pupilInfo = new PupilInfo();


  @ManyToMany
  @JoinTable(name = "family_relationship",
      joinColumns = @JoinColumn(name = "pupil_id"),
      inverseJoinColumns = @JoinColumn(name = "parent_id"))
  private List<ParentEntity> parents = Collections.emptyList();

  public Stream<ParentEntity> getParents() {
    return parents.stream();
  }

  public void updateInfo(PupilInfo pupilInfo) {
    this.pupilInfo = pupilInfo;
  }

  public void changeParents(List<ParentEntity> parents) {
    this.parents = parents;
  }

}
