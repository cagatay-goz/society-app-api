package com.metuncc.society_app_api.Annoncement;

import com.metuncc.society_app_api.Society.Society;
import com.metuncc.society_app_api.Society.SocietyService;
import com.metuncc.society_app_api.S3Service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final SocietyService societyService;
    private final S3Service s3Service; // Added S3 service

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               SocietyService societyService,
                               S3Service s3Service) { // Added to constructor
        this.announcementRepository = announcementRepository;
        this.societyService = societyService;
        this.s3Service = s3Service;
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public Announcement getOneAnnouncementById(long announcementId) {
        return announcementRepository.findById(announcementId).orElse(null);
    }

    public List<Announcement> getAnnouncementBySocietyId(long societyId) {
        return announcementRepository.findBySocietyId(societyId);
    }

    public Announcement createAnnouncement(CreateAnnouncementRequest announcementRequest) {
        // Check the related society
        Society society = societyService.getOneSociety(announcementRequest.getSocietyId());
        if (society == null) {
            return null; // Invalid society ID
        }

        // Create a new announcement
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setSociety(society);
        newAnnouncement.setTitle(announcementRequest.getTitle());
        newAnnouncement.setContent(announcementRequest.getContent());
        newAnnouncement.setLocation(announcementRequest.getLocation());
        newAnnouncement.setDate(announcementRequest.getDate()); // Date is provided by the user

        // File upload process
        MultipartFile file = announcementRequest.getFile();
        if (file != null && !file.isEmpty()) {
            String fileUrl = s3Service.uploadFile(file); // File is uploaded to S3
            newAnnouncement.setPosterUrl(fileUrl); // Set the URL
        } else {
            newAnnouncement.setPosterUrl(null); // If no file, leave as null
        }

        return announcementRepository.save(newAnnouncement);
    }
}
