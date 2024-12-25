package com.metuncc.society_app_api.JoinRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

    Optional<JoinRequest> findByUserIdAndSocietyIdAndStatus(Long userId, Long societyId, String status);

}
