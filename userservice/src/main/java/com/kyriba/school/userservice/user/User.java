package com.kyriba.school.userservice.user;

import com.kyriba.school.userservice.user.address.AddressEntity;
import com.kyriba.school.userservice.user.address.AddressInfo;
import com.kyriba.school.userservice.user.address.CommunicationInfo;
import com.kyriba.school.userservice.user.address.LocationInfo;
import com.kyriba.school.userservice.user.address.PostalInfo;

public class User<E extends UserEntity> {

  protected final E root;

  public User(E root) {
    this.root = root;
  }

  public E toEntity() {
    return root;
  }

  protected UserInfo toOutputModel(UserEntity.Info info) {
    var name = info.getName();
    var status = info.getStatus();
    return new UserInfo(name, status);
  }

  protected AddressInfo toOutputModel(AddressEntity address) {
    var locationInfo = toOutputModel(address.getLocation());
    var postalInfo = toOutputModel(address.getPostOffice());
    var communicationInfo = toOutputModel(address.getElectronicCommunication());
    return new AddressInfo(locationInfo, postalInfo, communicationInfo);
  }

  private LocationInfo toOutputModel(AddressEntity.Location location) {
    var city = location.getCity();
    var street = location.getStreet();
    var house = location.getHouse();
    var apartment = location.getApartment();
    return new LocationInfo(city, street, house, apartment);
  }

  private PostalInfo toOutputModel(AddressEntity.PostOffice postOffice) {
    var zip = postOffice.getZipCode();
    return new PostalInfo(zip);
  }

  private CommunicationInfo toOutputModel(AddressEntity.ElectronicCommunication communication) {
    var phone = communication.getPhoneNumber();
    var email = communication.getEmail();
    return new CommunicationInfo(phone, email);
  }

  protected void update(UserInputModel inputModel) {
    var userInfo = fromInputModel(inputModel.getUserInfo());
    root.updateInfo(userInfo);
    var address = root.getAddress();
    var addressInfo = inputModel.getAddressInfo();
    var location = fromInputModel(addressInfo.getLocationInfo());
    var postOffice = fromInputModel(addressInfo.getPostalInfo());
    var communication = fromInputModel(addressInfo.getCommunicationInfo());
    address.updateLocation(location);
    address.updatePostOffice(postOffice);
    address.updateElectronicCommunication(communication);
  }

  private UserEntity.Info fromInputModel(UserInfo userInfo) {
    var name = userInfo.getName();
    var status = userInfo.getStatus();
    return new UserEntity.Info(name, status);
  }

  private AddressEntity.Location fromInputModel(LocationInfo locationInfo) {
    var city = locationInfo.getCity();
    var street = locationInfo.getStreet();
    var house = locationInfo.getHouse();
    var apartment = locationInfo.getApartment();
    return new AddressEntity.Location(city, street, house, apartment);
  }

  private AddressEntity.PostOffice fromInputModel(PostalInfo postalInfo) {
    var zip = postalInfo.getZipCode();
    return new AddressEntity.PostOffice(zip);
  }

  private AddressEntity.ElectronicCommunication fromInputModel(CommunicationInfo communication) {
    var phone = communication.getPhoneNumber();
    var email = communication.getEmail();
    return new AddressEntity.ElectronicCommunication(phone, email);
  }

}
