package com.kyriba.school.userservice.user.principal;

import com.kyriba.school.userservice.user.NoSuchUserException;
import com.kyriba.school.userservice.user.UserService;
import com.kyriba.school.userservice.user.address.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class PrincipalService implements UserService<PrincipalOutputModel, PrincipalInputModel> {

  private final PrincipalRepository principalRepository;

  private final PrincipalFactory principalFactory;

  private final AddressRepository addressRepository;

  public PrincipalService(PrincipalRepository principalRepository,
      PrincipalFactory principalFactory, AddressRepository addressRepository) {
    this.principalRepository = principalRepository;
    this.principalFactory = principalFactory;
    this.addressRepository = addressRepository;
  }

  @Override
  public PrincipalOutputModel findById(long id) throws NoSuchUserException {
    return principalRepository.findById(id)
        .map(principalFactory::create)
        .map(Principal::toOutputModel)
        .orElseThrow(() -> new NoSuchUserException(id));
  }

  @Override
  public PrincipalOutputModel create(PrincipalInputModel principalModel) {
    var principal = principalFactory.create(principalModel);
    var entity = principal.toEntity();
    addressRepository.save(entity.getAddress());
    principalRepository.save(entity);
    return principal.toOutputModel();
  }

  @Override
  public PrincipalOutputModel update(long id, PrincipalInputModel principalModel)
      throws NoSuchUserException {
    var principal = principalRepository.findById(id)
        .map(principalFactory::create)
        .orElseThrow(() -> new NoSuchUserException(id));
    principal.update(principalModel);
    principalRepository.save(principal.toEntity());
    return principal.toOutputModel();
  }

}
