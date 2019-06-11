package com.kyriba.school.userservice.user.address;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Getter
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  protected Long id;

  @Embedded
  private Location location = new Location();

  @Embedded
  private PostOffice postOffice = new PostOffice();

  @Embedded
  private ElectronicCommunication electronicCommunication = new ElectronicCommunication();

  public void updateLocation(Location location) {
    this.location = location;
  }

  public void updatePostOffice(PostOffice postOffice) {
    this.postOffice = postOffice;
  }

  public void updateElectronicCommunication(ElectronicCommunication electronicCommunication) {
    this.electronicCommunication = electronicCommunication;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Location {

    @Column(name = "city")
    private String city = "N/A";

    @Column(name = "street")
    private String street = "N/A";

    @Column(name = "house")
    private String house = "N/A";

    @Column(name = "apartment")
    private String apartment = "N/A";

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class PostOffice {

    @Column(name = "zip_code")
    private String zipCode = "N/A";

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ElectronicCommunication {

    @Column(name = "phone_number")
    private String phoneNumber = "N/A";

    @Column(name = "email")
    private String email = "N/A";

  }

}
