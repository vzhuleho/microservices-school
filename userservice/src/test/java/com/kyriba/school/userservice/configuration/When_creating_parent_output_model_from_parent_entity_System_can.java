package com.kyriba.school.userservice.configuration;

import static org.junit.Assert.assertEquals;

import com.kyriba.school.userservice.user.Status;
import com.kyriba.school.userservice.user.parent.ParentEntity;
import com.kyriba.school.userservice.user.parent.ParentInputModel;
import com.kyriba.school.userservice.user.parent.ParentOutputModel;
import ma.glasnost.orika.MapperFacade;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class When_creating_parent_output_model_from_parent_entity_System_can {

  private static MapperFacade mapperFacade;

  @BeforeClass
  public static void setup() {
    mapperFacade = new OrikaConfiguration();
  }

  @Test
  public void map_id_to_id() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.setId(13L);

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals(13L, parentOutputModel.getId());
  }

  @Test
  public void map_userInfo_name_to_info_name() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getInfo().setName("Andrei");

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals("Andrei", parentOutputModel.getUserInfo().getName());
  }


  @Test
  public void map_userInfo_status_to_info_status() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getInfo().setStatus(Status.ACTIVE);

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals(Status.ACTIVE, parentOutputModel.getUserInfo().getStatus());
  }


  @Test
  public void map_addressInfo_locationInfo_city_to_address_location_city() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getAddress().getLocation().setCity("Brest");

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals("Brest", parentOutputModel.getAddressInfo().getLocationInfo().getCity());
  }

  @Test
  public void map_addressInfo_locationInfo_street_to_address_location_street() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getAddress().getLocation().setStreet("Moscow Str.");

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals("Moscow Str.", parentOutputModel.getAddressInfo().getLocationInfo().getStreet());
  }

  @Test
  public void map_addressInfo_locationInfo_house_to_address_location_house() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getAddress().getLocation().setHouse("13/2");

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals("13/2", parentOutputModel.getAddressInfo().getLocationInfo().getHouse());
  }

  @Test
  public void map_addressInfo_locationInfo_apartment_to_address_location_apartment() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getAddress().getLocation().setApartment("56");

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals("56", parentOutputModel.getAddressInfo().getLocationInfo().getApartment());
  }

  @Test
  public void map_addressInfo_postalInfo_apartment_to_address_postOffice_zipCode() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getAddress().getPostOffice().setZipCode("12345");

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals("12345", parentOutputModel.getAddressInfo().getPostalInfo().getZipCode());
  }

  @Test
  public void map_communicationInfo_postalInfo_phoneNumber_to_address_electronicCommunication_phoneNumber() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getAddress().getElectronicCommunication().setPhoneNumber("+45000343434");

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals("+45000343434", parentOutputModel.getAddressInfo().getCommunicationInfo().getPhoneNumber());
  }

  @Test
  public void map_communicationInfo_postalInfo_email_to_address_electronicCommunication_email() {
    // given
    ParentEntity parentEntity = new ParentEntity();
    parentEntity.getAddress().getElectronicCommunication().setEmail("my@mail.it");

    // when
    ParentOutputModel parentOutputModel = mapperFacade.map(parentEntity, ParentOutputModel.class);

    // then
    assertEquals("my@mail.it", parentOutputModel.getAddressInfo().getCommunicationInfo().getEmail());
  }
}