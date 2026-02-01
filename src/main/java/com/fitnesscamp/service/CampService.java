package com.fitnesscamp.service;

import com.fitnesscamp.entity.Camp;
import com.fitnesscamp.repository.CampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CampService {

    private final CampRepository campRepository;

    public List<Camp> getAllCamps() {
        return campRepository.findAll();
    }

    public List<Camp> getActiveCamps() {
        return campRepository.findByIsActiveTrue();
    }

    public List<Camp> getUpcomingCamps() {
        return campRepository.findUpcomingCamps(LocalDate.now());
    }

    public Optional<Camp> getCampById(Long id) {
        return campRepository.findById(id);
    }

    public Camp getCampWithParticipants(Long id) {
        return campRepository.findByIdWithParticipants(id);
    }

    public Camp saveCamp(Camp camp) {
        return campRepository.save(camp);
    }

    public void deleteCamp(Long id) {
        campRepository.deleteById(id);
    }

    public List<Camp> searchCamps(String name) {
        return campRepository.findByNameContainingIgnoreCase(name);
    }

    public long getTotalCampCount() {
        return campRepository.count();
    }

    public long getActiveCampCount() {
        return campRepository.findByIsActiveTrue().size();
    }
}
