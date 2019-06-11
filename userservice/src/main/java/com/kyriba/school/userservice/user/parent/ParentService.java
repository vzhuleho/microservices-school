package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.NoSuchUserException;
import com.kyriba.school.userservice.user.UserService;
import com.kyriba.school.userservice.user.address.AddressRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ParentService implements UserService<ParentOutputModel, ParentInputModel> {

  private final ParentRepository parentRepository;

  private final ParentFactory parentFactory;

  private final AddressRepository addressRepository;

  public ParentService(ParentRepository parentRepository, ParentFactory parentFactory,
      AddressRepository addressRepository) {
    this.parentRepository = parentRepository;
    this.parentFactory = parentFactory;
    this.addressRepository = addressRepository;
  }

  @Override
  public ParentOutputModel findById(long id) throws NoSuchUserException {
    return parentRepository.findById(id)
        .map(parentFactory::create)
        .map(Parent::toOutputModel)
        .orElseThrow(() -> new NoSuchUserException(id));
  }

  @Override
  @Transactional
  public ParentOutputModel create(ParentInputModel parentModel) {
    var parent = parentFactory.create(parentModel);
    var entity = parent.toEntity();
    addressRepository.save(entity.getAddress());
    parentRepository.save(entity);
    return parent.toOutputModel();
  }

  @Override
  @Transactional
  public ParentOutputModel update(long id, ParentInputModel parentModel)
      throws NoSuchUserException {
    var parent = parentRepository.findById(id)
        .map(parentFactory::create)
        .orElseThrow(() -> new NoSuchUserException(id));
    parent.update(parentModel);
    parentRepository.save(parent.toEntity());
    return parent.toOutputModel();
  }

}
