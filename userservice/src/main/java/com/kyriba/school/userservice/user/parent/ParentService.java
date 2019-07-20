package com.kyriba.school.userservice.user.parent;

import com.kyriba.school.userservice.user.NoSuchUserException;
import com.kyriba.school.userservice.user.UserService;
import com.kyriba.school.userservice.user.address.AddressRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ParentService implements UserService<ParentOutputModel, ParentInputModel> {

  private final ParentRepository parentRepository;

  private final ParentMapper parentMapper;

  private final AddressRepository addressRepository;

  public ParentService(ParentRepository parentRepository,
      ParentMapper parentMapper, AddressRepository addressRepository) {
    this.parentRepository = parentRepository;
    this.parentMapper = parentMapper;
    this.addressRepository = addressRepository;
  }

  @Override
  public ParentOutputModel findById(long id) throws NoSuchUserException {
    return parentRepository.findById(id)
        .map(parentMapper::convert)
        .orElseThrow(() -> new NoSuchUserException(id));
  }

  @Override
  @Transactional
  public ParentOutputModel create(ParentInputModel parentModel) {
    var parent = parentMapper.convert(parentModel);
    addressRepository.save(parent.getAddress());
    parentRepository.save(parent);
    return parentMapper.convert(parent);
  }

  @Override
  @Transactional
  public ParentOutputModel update(long id, ParentInputModel parentModel)
      throws NoSuchUserException {
    var parent = parentRepository.findById(id)
        .orElseThrow(() -> new NoSuchUserException(id));
    parentMapper.update(parent, parentModel);
    return parentMapper.convert(parent);
  }

}
