package com.kyriba.school.userservice.user.pupil;

import com.kyriba.school.userservice.user.NoSuchUserException;
import com.kyriba.school.userservice.user.UserService;
import com.kyriba.school.userservice.user.address.AddressRepository;
import com.kyriba.school.userservice.user.parent.ParentRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PupilService implements UserService<PupilOutputModel, PupilInputModel> {

  private final PupilRepository pupilRepository;

  private final PupilFactory pupilFactory;

  private final AddressRepository addressRepository;

  private final ParentRepository parentRepository;

  public PupilService(PupilRepository pupilRepository, PupilFactory pupilFactory,
      AddressRepository addressRepository, ParentRepository parentRepository) {
    this.pupilRepository = pupilRepository;
    this.pupilFactory = pupilFactory;
    this.addressRepository = addressRepository;
    this.parentRepository = parentRepository;
  }

  @Override
  public PupilOutputModel findById(long id) throws NoSuchUserException {
    return pupilRepository.findById(id)
        .map(pupilFactory::create)
        .map(Pupil::toOutputModel)
        .orElseThrow(() -> new NoSuchUserException(id));
  }

  @Override
  @Transactional
  public PupilOutputModel create(PupilInputModel pupilModel) {
    var parents = parentRepository.findByIdIn(pupilModel.getParentIds());
    var pupil = pupilFactory.create(pupilModel, parents);
    var entity = pupil.toEntity();
    addressRepository.save(entity.getAddress());
    pupilRepository.save(pupil.toEntity());
    return pupil.toOutputModel();
  }

  @Override
  @Transactional
  public PupilOutputModel update(long id, PupilInputModel pupilModel) throws NoSuchUserException {
    var pupil = pupilRepository.findById(id)
        .map(pupilFactory::create)
        .orElseThrow(() -> new NoSuchUserException(id));
    var parents = parentRepository.findByIdIn(pupilModel.getParentIds());
    pupil.update(pupilModel, parents);
    pupilRepository.save(pupil.toEntity());
    return pupil.toOutputModel();
  }

}
