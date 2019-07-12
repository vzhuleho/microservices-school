package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.Status;
import com.kyriba.school.userservice.user.UserEntity.Info;
import com.kyriba.school.userservice.user.address.AddressEntity;
import com.kyriba.school.userservice.user.address.AddressRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ParentRepositoryTest {

  @Autowired
  AddressRepository addressRepository;

  @Autowired
  ParentRepository parentRepository;

  List<Long> allParentIds = new ArrayList<>();

  @Before
  public void setup() {
    AddressEntity address = addressRepository.save(new AddressEntity());
    parentRepository.saveAll(List.of(
        parent("John", address),
        parent("Max", address),
        parent("Smith", address)))
        .forEach(parent -> allParentIds.add(parent.getId()));
  }

  private ParentEntity parent(String name, AddressEntity address) {
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.updateInfo(new Info(name, Status.ACTIVE));
    parentEntity.setAddress(address);
    return parentEntity;
  }

  @Test
  public void findByIdIn() {
    // precondition
    Assert.assertEquals(3, allParentIds.size());

    // when
    List<ParentEntity> parents = parentRepository
        .findByIdIn(List.of(allParentIds.get(0), allParentIds.get(2)));

    // then
    Assert.assertEquals(2, parents.size());
  }

  @Test
  public void check1() {
    Assert.assertEquals(3, allParentIds.size());
  }

  @Test
  public void check2() {
    Assert.assertEquals(3, allParentIds.size());
  }
}