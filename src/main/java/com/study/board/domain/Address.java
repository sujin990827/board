package com.study.board.domain;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    } //public으로 하면 사람들이 많이 호출할 수 있기 때문에 protected로 변경

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    } // 변경이 되면 안된다. Setter 제공 안함.
}
