package com.metuncc.society_app_api.Annoncement;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }


    public Announcement getOneAnnouncementById(long announcementId) {
        return announcementRepository.findById(announcementId).orElse(null);
    }
}
