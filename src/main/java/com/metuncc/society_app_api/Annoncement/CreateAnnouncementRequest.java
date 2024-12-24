package com.metuncc.society_app_api.Annoncement;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Data
public class CreateAnnouncementRequest {
    Long societyId;
    LocalDateTime date;
    String title;
    String content;
    String location;
    MultipartFile file;
}
