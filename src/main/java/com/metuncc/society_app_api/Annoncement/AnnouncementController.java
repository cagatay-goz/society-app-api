package com.metuncc.society_app_api.Annoncement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }
    @GetMapping
    public List<Announcement> getAllAnnouncements() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping("/{announcementId}")
    public Announcement getAnnouncementById(@PathVariable long announcementId) {
        return announcementService.getOneAnnouncementById(announcementId);
    }

    @GetMapping("/society/{societyId}")
    public List<Announcement> getAnnouncementBySocietyId(@PathVariable long societyId) {
        return announcementService.getAnnouncementBySocietyId(societyId);
    }

    @PostMapping(consumes = "multipart/form-data")
    public Announcement createAnnouncement(@ModelAttribute CreateAnnouncementRequest announcementRequest) {
        return announcementService.createAnnouncement(announcementRequest);
    }

    @DeleteMapping("/{announcementId}")
    public void deleteAnnouncement(@PathVariable long announcementId) {
        announcementService.deleteAnnouncement(announcementId);
    }

    @PutMapping("/{announcementId}")
    public Announcement updateAnnouncement(
            @PathVariable long announcementId,
            @ModelAttribute UpdateAnnouncementRequest updateRequest) {
        // Call the service method to update the announcement
        return announcementService.editAnnouncement(announcementId, updateRequest);
    }



}
