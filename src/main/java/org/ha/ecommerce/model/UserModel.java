package org.ha.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document("users")
public class UserModel {
    @MongoId
    private String id;
    private String email;
    private String country;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private Boolean isVerified;
    private String role;
    private Date lastLogin;
    private String verificationToken;
    private Date verificationTokenExpiresAt;
    private String resetPasswordToken;
    private Date resetPasswordExpiresAt;

    //  Constructor
    public UserModel() {
    }

    public UserModel(Date resetPasswordExpiresAt, String resetPasswordToken, Date verificationTokenExpiresAt, String verificationToken, Date lastLogin, String role, Boolean isVerified, String password, String userName, String lastName, String firstName, String country, String email, String id) {
        this.resetPasswordExpiresAt = resetPasswordExpiresAt;
        this.resetPasswordToken = resetPasswordToken;
        this.verificationTokenExpiresAt = verificationTokenExpiresAt;
        this.verificationToken = verificationToken;
        this.lastLogin = lastLogin;
        this.role = role;
        this.isVerified = isVerified;
        this.password = password;
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.country = country;
        this.email = email;
        this.id = id;
    }

    //    Getter
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public String getRole() {
        return role;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public Date getVerificationTokenExpiresAt() {
        return verificationTokenExpiresAt;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public Date getResetPasswordExpiresAt() {
        return resetPasswordExpiresAt;
    }

    //  Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public void setVerificationTokenExpiresAt(Date verificationTokenExpiresAt) {
        this.verificationTokenExpiresAt = verificationTokenExpiresAt;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public void setResetPasswordExpiresAt(Date resetPasswordExpiresAt) {
        this.resetPasswordExpiresAt = resetPasswordExpiresAt;
    }
}
