package com.kyriba.school.userservice.configuration;

import static org.junit.Assert.*;

import com.kyriba.school.userservice.user.Status;
import com.kyriba.school.userservice.user.parent.ParentEntity;
import com.kyriba.school.userservice.user.parent.ParentInputModel;
import ma.glasnost.orika.MapperFacade;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class When_creating_parent_entity_from_parent_input_model_System_can {

  private static MapperFacade mapperFacade;

  @BeforeClass
  public static void setup() {
    mapperFacade = new OrikaConfiguration();
  }

  @Test
  public void map_userInfo_name_to_info_name() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getUserInfo().setName("Andrei");

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals("Andrei", parentEntity.getInfo().getName());
  }


  @Test
  public void map_userInfo_status_to_info_status() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getUserInfo().setStatus(Status.ACTIVE);

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals(Status.ACTIVE, parentEntity.getInfo().getStatus());
  }


  @Test
  public void map_addressInfo_locationInfo_city_to_address_location_city() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getAddressInfo().getLocationInfo().setCity("Brest");

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals("Brest", parentEntity.getAddress().getLocation().getCity());
  }

  @Test
  public void map_addressInfo_locationInfo_street_to_address_location_street() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getAddressInfo().getLocationInfo().setStreet("Moscow Str.");

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals("Moscow Str.", parentEntity.getAddress().getLocation().getStreet());
  }

  @Test
  public void map_addressInfo_locationInfo_house_to_address_location_house() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getAddressInfo().getLocationInfo().setHouse("13/2");

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals("13/2", parentEntity.getAddress().getLocation().getHouse());
  }

  @Test
  public void map_addressInfo_locationInfo_apartment_to_address_location_apartment() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getAddressInfo().getLocationInfo().setApartment("56");

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals("56", parentEntity.getAddress().getLocation().getApartment());
  }

  @Test
  public void map_addressInfo_postalInfo_apartment_to_address_postOffice_zipCode() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getAddressInfo().getPostalInfo().setZipCode("12345");

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals("12345", parentEntity.getAddress().getPostOffice().getZipCode());
  }

  @Test
  public void map_communicationInfo_postalInfo_phoneNumber_to_address_electronicCommunication_phoneNumber() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getAddressInfo().getCommunicationInfo().setPhoneNumber("+45000343434");

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals("+45000343434", parentEntity.getAddress().getElectronicCommunication().getPhoneNumber());
  }

  @Test
  public void map_communicationInfo_postalInfo_email_to_address_electronicCommunication_email() {
    // given
    ParentInputModel parentInputModel = new ParentInputModel();
    parentInputModel.getAddressInfo().getCommunicationInfo().setEmail("my@mail.it");

    // when
    ParentEntity parentEntity = mapperFacade.map(parentInputModel, ParentEntity.class);

    // then
    assertEquals("my@mail.it", parentEntity.getAddress().getElectronicCommunication().getEmail());
  }
}