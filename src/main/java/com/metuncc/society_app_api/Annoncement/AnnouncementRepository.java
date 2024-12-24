package com.metuncc.society_app_api.Annoncement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
    List<Announcement> findBySocietyId(Long societyId);

}
