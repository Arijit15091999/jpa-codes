package entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private Long pinCode;
    private String city;
    private String state;

    public Address() {
    }

    public Address(Long pinCode, String city, String state) {
        this.pinCode = pinCode;
        this.city = city;
        this.state = state;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "pinCode=" + pinCode +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
