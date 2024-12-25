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

    public void deleteAnnouncement(long announcementId) {
        // Fetch the announcement by ID
        Announcement announcement = announcementRepository.findById(announcementId).orElse(null);
        if (announcement == null) {
            throw new IllegalArgumentException("Announcement not found with ID: " + announcementId);
        }

        // Check if the announcement has a poster URL
        String posterUrl = announcement.getPosterUrl();
        if (posterUrl != null && !posterUrl.isEmpty()) {
            // Delete the file from S3
            s3Service.deleteFile(posterUrl);
        }

        // Delete the announcement from the database
        announcementRepository.deleteById(announcementId);
    }

    public Announcement editAnnouncement(long announcementId, UpdateAnnouncementRequest updateRequest) {
        // Find the existing announcement by ID
        Announcement existingAnnouncement = announcementRepository.findById(announcementId).orElse(null);
        if (existingAnnouncement == null) {
            throw new IllegalArgumentException("Announcement not found with ID: " + announcementId);
        }

        // Update the fields of the announcement
        if (updateRequest.getTitle() != null) {
            existingAnnouncement.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getContent() != null) {
            existingAnnouncement.setContent(updateRequest.getContent());
        }
        if (updateRequest.getLocation() != null) {
            existingAnnouncement.setLocation(updateRequest.getLocation());
        }
        if (updateRequest.getDate() != null) {
            existingAnnouncement.setDate(updateRequest.getDate());
        }

        // Handle file update
        MultipartFile newFile = updateRequest.getFile();
        if (newFile != null && !newFile.isEmpty()) {
            // Delete the old file from S3
            String oldPosterUrl = existingAnnouncement.getPosterUrl();
            if (oldPosterUrl != null && !oldPosterUrl.isEmpty()) {
                s3Service.deleteFile(oldPosterUrl);
            }

            // Upload the new file to S3
            String newFileUrl = s3Service.uploadFile(newFile);
            existingAnnouncement.setPosterUrl(newFileUrl);
        }

        // Save the updated announcement to the database
        return announcementRepository.save(existingAnnouncement);
    }



}
