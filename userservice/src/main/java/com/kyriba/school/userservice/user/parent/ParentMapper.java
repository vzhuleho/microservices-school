package com.kyriba.school.userservice.user.parent;

import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

@Service
public class ParentMapper {

  private final MapperFacade mapperFacade;

  public ParentMapper(MapperFacade mapperFacade) {
    this.mapperFacade = mapperFacade;
  }

  public ParentEntity convert(ParentInputModel parentModel) {
    return mapperFacade.map(parentModel, ParentEntity.class);
  }

  public ParentOutputModel convert(ParentEntity entity) {
    return mapperFacade.map(entity, ParentOutputModel.class);
  }

  public void update(ParentEntity entity, ParentInputModel parentModel) {
    mapperFacade.map(parentModel, entity);
  }
}
