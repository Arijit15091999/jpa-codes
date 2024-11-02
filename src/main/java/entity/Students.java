package entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "student_details")
public class Students {
    @Id
    @Column(name = "student_id")
    @GeneratedValue
    private long id;

    private String name;

    private String email;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Address> addressList;

    public Students() {

    }

    public Students(String name, String email) {
        this.name = name;
        this.email = email;
        this.addressList = new ArrayList<>();
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public void setAddress(Address address) {
        this.addressList.add(address);
    }



    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String  toString() {
        return "Students{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
