package model;

import at.ac.tuwien.big.we14.lab2.api.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by willi on 5/7/14.
 */

@Entity
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(
                name="Users.findByUsername",
                query="SELECT u" +
                        " FROM Users u" +
                        " WHERE u.name = :name"
        ),
})
public class Users implements User {

    @Id
    private String name;


    private String password;

    private String firstname;

    private String lastname;

    private Date birthday;

    private Gender female;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getFemale() {
        return female;
    }

    public void setFemale(Gender female) {
        this.female = female;
    }
}
