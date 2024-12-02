package com.metuncc.society_app_api.Society;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocietyService {
    SocietyRepository societyRepository;

    public SocietyService(SocietyRepository societyRepository) {
        this.societyRepository = societyRepository;
    }

    public List<Society> getAllSocieties() {
        return societyRepository.findAll();
    }

    public Society getOneSociety(long societyId) {
        return societyRepository.findById(societyId).orElse(null);
    }

}
