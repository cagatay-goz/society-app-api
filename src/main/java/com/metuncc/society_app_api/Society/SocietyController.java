    package com.metuncc.society_app_api.Society;

    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

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
        public ResponseEntity<?> createSociety(@RequestBody Society society) {
            try {
                Society createdSociety = societyService.createSociety(society);
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
