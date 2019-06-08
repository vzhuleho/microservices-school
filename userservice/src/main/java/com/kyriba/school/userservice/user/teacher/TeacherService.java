package com.kyriba.school.userservice.user.teacher;

import com.kyriba.school.userservice.user.NoSuchUserException;
import com.kyriba.school.userservice.user.UserService;
import com.kyriba.school.userservice.user.address.AddressRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TeacherService implements UserService<TeacherOutputModel, TeacherInputModel> {

  private final TeacherRepository teacherRepository;

  private final TeacherFactory teacherFactory;

  private final AddressRepository addressRepository;

  public TeacherService(TeacherRepository teacherRepository, TeacherFactory teacherFactory,
      AddressRepository addressRepository) {
    this.teacherRepository = teacherRepository;
    this.teacherFactory = teacherFactory;
    this.addressRepository = addressRepository;
  }

  @Override
  public TeacherOutputModel findById(long id) throws NoSuchUserException {
    return teacherRepository.findById(id)
        .map(teacherFactory::create)
        .map(Teacher::toOutputModel)
        .orElseThrow(() -> new NoSuchUserException(id));
  }

  @Override
  @Transactional
  public TeacherOutputModel create(TeacherInputModel teacherModel) {
    var teacher = teacherFactory.create(teacherModel);
    var entity = teacher.toEntity();
    addressRepository.save(entity.getAddress());
    teacherRepository.save(teacher.toEntity());
    return teacher.toOutputModel();
  }

  @Override
  @Transactional
  public TeacherOutputModel update(long id, TeacherInputModel teacherModel)
      throws NoSuchUserException {
    var teacher = teacherRepository.findById(id)
        .map(teacherFactory::create)
        .orElseThrow(() -> new NoSuchUserException(id));
    teacher.update(teacherModel);
    teacherRepository.save(teacher.toEntity());
    return teacher.toOutputModel();
  }

}
