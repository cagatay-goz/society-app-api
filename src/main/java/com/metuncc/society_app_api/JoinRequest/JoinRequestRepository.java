package com.metuncc.society_app_api.JoinRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

    List<JoinRequest> findBySocietyIdAndStatus(Long societyId, String status);

    Optional<JoinRequest> findByUserIdAndSocietyIdAndStatus(Long userId, Long societyId, String pending);
}

