package model;

import at.ac.tuwien.big.we14.lab2.api.User;
import play.data.validation.Constraints;

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
    @Constraints.Required
    @Constraints.MinLength(4)
    @Constraints.MaxLength(8)
    private String name;

    @Constraints.Required
    @Constraints.MinLength(4)
    @Constraints.MaxLength(8)
    private String password;

    @Transient
    @Constraints.Required
    @Constraints.MinLength(4)
    @Constraints.MaxLength(8)
    private String passwordConfirm;

    private String firstname;

    private String lastname;

    private Date birthday;

    private Gender gender;


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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
