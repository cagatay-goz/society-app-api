package com.metuncc.society_app_api.Society;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SocietyRepository extends JpaRepository<Society, Long> {

    @Query("SELECT s FROM Society s JOIN s.users u WHERE u.id = :userId")
    List<Society> findByUserId(@Param("userId") Long userId);
}
