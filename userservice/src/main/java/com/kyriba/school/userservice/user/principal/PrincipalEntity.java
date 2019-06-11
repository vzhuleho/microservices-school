package com.kyriba.school.userservice.user.principal;

import com.kyriba.school.userservice.user.UserEntity;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("prn")
@Table(name = "principal")
public class PrincipalEntity extends UserEntity {

}
