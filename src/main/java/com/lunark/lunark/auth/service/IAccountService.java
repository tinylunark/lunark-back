package com.lunark.lunark.auth.service;

import com.lunark.lunark.auth.model.Account;
import com.lunark.lunark.properties.model.Property;
import com.lunark.lunark.reservations.model.Reservation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Collection<Account> findAll();
    Account create(Account account);
    Optional<Account> find(Long id);
    Optional<Account> find(String username, String password);
    Optional<Account> find(String email);
    Account update(Account account);
    boolean delete(Long id);
    boolean updatePassword(Long id, String oldPassword, String newPassword);
    void addToFavorites(Long id, Property property);
    Double getAverageGrade(Long id);
    Collection<Property> getFavoriteProperties(Long accountId);
    void removeFromFavorites(Long id, Property property);
    void saveProfileImage(Long accountId, MultipartFile file) throws IOException;
    void saveAndFlush(Account account);
    public void cancelAllReservations(List<Reservation> reservationList);
}
