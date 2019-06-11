package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.UserEntity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("prn")
@Table(name = "parent")
public class ParentEntity extends UserEntity {

}