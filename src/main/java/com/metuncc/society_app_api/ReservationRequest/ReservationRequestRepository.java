package com.metuncc.society_app_api.ReservationRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {

    // Retrieve all reservation requests for a specific admin
    List<ReservationRequest> findByAdminsId(Long adminId);

    List<ReservationRequest> findByPresidentId(Long id);
}
