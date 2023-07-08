package pl.jewusiak.luncher_api.utils;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String addressee;
    private String line1;
    private String line2;
    private String city;
    private String zip;
    private String country;
    private String phoneNumber;
    private String email;
}
