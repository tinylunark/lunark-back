package com.lunark.lunark.auth.model;

import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reservations.model.Reservation;
import com.lunark.lunark.reviews.model.Review;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@SQLDelete(sql
        = "UPDATE account "
        + "SET deleted = true "
        + "WHERE id = ?")
@Where(clause = "deleted = false")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String address;
    @Column
    private String phoneNumber;
    @Column
    private boolean verified;
    @Column
    private AccountRole role;
    @Column
    private boolean blocked;
    @Embedded
    private ProfileImage profileImage;

    @OneToMany
    private Collection<Review> reviews;
    @ManyToMany
    private Set<Property> favoriteProperties;
    @OneToMany(mappedBy = "guest")
    private Set<Reservation> reservations;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private boolean deleted = false;

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Account() {

    }

    public Account(Long id, String email, String password, String name, String surname, String address, String phoneNumber, boolean verified, AccountRole role, boolean notificationsEnabled, boolean blocked, Collection<Review> reviews, HashSet<Property> favoriteProperties) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.verified = verified;
        this.role = role;
        this.blocked = blocked;
        this.reviews = reviews;
        this.favoriteProperties = favoriteProperties;
    }

    public void verify() {
        verified = true;
    }

    public boolean canLogIn() {
        return verified && !blocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
        return grantedAuthorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return verified && !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean credentialsMatch(String email, String password) {
        return this.email.equals(email) && this.email.equals(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Property> getFavoriteProperties() {
        return favoriteProperties;
    }

    public void setFavoriteProperties(Set<Property> favoriteProperties) {
        this.favoriteProperties = favoriteProperties;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }
}
