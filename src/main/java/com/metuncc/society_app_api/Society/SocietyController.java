    package com.metuncc.society_app_api.Society;

    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/societies")
    public class SocietyController {
        private final SocietyService societyService;

        public SocietyController(SocietyService societyService) {
            this.societyService = societyService;
        }

        @GetMapping
        public List<Society> getAllSocieties() {
            return societyService.getAllSocieties();
        }

        @GetMapping("/{societyId}")
        public Society getOneSociety(@PathVariable long societyId) {
            return societyService.getOneSociety(societyId);
        }

        @PostMapping("/add")
        public ResponseEntity<?> createSociety(@RequestBody Map<String, String> requestData) {
            try {
                String name = requestData.get("name");
                String description = requestData.get("description");
                String presidentEmail = requestData.get("presidentEmail");

                if (name == null || description == null || presidentEmail == null) {
                    return ResponseEntity.badRequest().body("Missing required fields: name, description, or presidentEmail.");
                }

                Society createdSociety = societyService.createSociety(name, description, presidentEmail);
                return ResponseEntity.ok(createdSociety);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error creating society: " + e.getMessage());
            }
        }

        @GetMapping("/user/{email}")
        public List<Society> getSocietiesByUserId(@PathVariable String email) {
            return societyService.getSocietiesByUserId(email);
        }

    }
