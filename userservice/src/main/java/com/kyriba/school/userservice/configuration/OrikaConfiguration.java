package com.kyriba.school.userservice.configuration;

import com.kyriba.school.userservice.user.parent.ParentEntity;
import com.kyriba.school.userservice.user.parent.ParentInputModel;
import com.kyriba.school.userservice.user.parent.ParentOutputModel;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfiguration extends ConfigurableMapper {

  @Override
  protected void configure(MapperFactory factory) {
    mapUserFields(factory.classMap(ParentInputModel.class, ParentEntity.class))
        .register();
    mapUserFields(factory.classMap(ParentOutputModel.class, ParentEntity.class))
        .byDefault()
        .register();
  }

  public static <A, B> ClassMapBuilder<A, B> mapUserFields(ClassMapBuilder<A, B> builder) {
    return builder
        .field("userInfo.name", "info.name")
        .field("userInfo.status", "info.status")
        .field("addressInfo.locationInfo.city", "address.location.city")
        .field("addressInfo.locationInfo.street", "address.location.street")
        .field("addressInfo.locationInfo.house", "address.location.house")
        .field("addressInfo.locationInfo.apartment", "address.location.apartment")
        .field("addressInfo.postalInfo.zipCode", "address.postOffice.zipCode")
        .field("addressInfo.communicationInfo.phoneNumber", "address.electronicCommunication.phoneNumber")
        .field("addressInfo.communicationInfo.email", "address.electronicCommunication.email");
  }
}
