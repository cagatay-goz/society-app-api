    package com.metuncc.society_app_api.Society;

    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

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

        @GetMapping("/user/{email}")
        public List<Society> getSocietiesByUserId(@PathVariable String email) {
            return societyService.getSocietiesByUserId(email);
        }

    }
