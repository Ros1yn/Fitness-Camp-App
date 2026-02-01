package com.fitnesscamp.service;

import com.fitnesscamp.entity.Participant;
import com.fitnesscamp.entity.Participant.RegistrationStatus;
import com.fitnesscamp.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public List<Participant> getAllParticipants() {
        return participantRepository.findAllWithCamp();
    }

    public Optional<Participant> getParticipantById(Long id) {
        return participantRepository.findById(id);
    }

    public List<Participant> getParticipantsByCamp(Long campId) {
        return participantRepository.findByCampId(campId);
    }

    public List<Participant> getParticipantsByStatus(RegistrationStatus status) {
        return participantRepository.findByRegistrationStatus(status);
    }

    public Participant saveParticipant(Participant participant) {
        if (participant.getRegistrationDate() == null) {
            participant.setRegistrationDate(LocalDateTime.now());
        }
        return participantRepository.save(participant);
    }

    public Participant registerParticipant(Participant participant) {
        participant.setRegistrationDate(LocalDateTime.now());
        participant.setRegistrationStatus(RegistrationStatus.PENDING);
        return participantRepository.save(participant);
    }

    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }

    public Participant updateStatus(Long id, RegistrationStatus status) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id));
        participant.setRegistrationStatus(status);
        return participantRepository.save(participant);
    }

    public List<Participant> searchParticipants(String searchTerm) {
        return participantRepository.searchParticipants(searchTerm);
    }

    public long getTotalParticipantCount() {
        return participantRepository.count();
    }

    public long getPendingCount() {
        return participantRepository.countByStatus(RegistrationStatus.PENDING);
    }

    public long getConfirmedCount() {
        return participantRepository.countByStatus(RegistrationStatus.CONFIRMED);
    }

    public long getParticipantCountByCamp(Long campId) {
        return participantRepository.countByCampId(campId);
    }
}
