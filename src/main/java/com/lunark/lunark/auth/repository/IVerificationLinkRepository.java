package com.lunark.lunark.auth.repository;

import com.lunark.lunark.auth.model.VerificationLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationLinkRepository extends JpaRepository<VerificationLink, Long> {
}
