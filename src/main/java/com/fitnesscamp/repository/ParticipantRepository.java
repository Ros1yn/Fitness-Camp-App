package com.fitnesscamp.repository;

import com.fitnesscamp.entity.Participant;
import com.fitnesscamp.entity.Participant.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findByCampId(Long campId);

    List<Participant> findByRegistrationStatus(RegistrationStatus status);

    List<Participant> findByEmailIgnoreCase(String email);

    @Query("SELECT p FROM Participant p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(p.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Participant> searchParticipants(String searchTerm);

    @Query("SELECT COUNT(p) FROM Participant p WHERE p.camp.id = :campId")
    Long countByCampId(Long campId);

    @Query("SELECT COUNT(p) FROM Participant p WHERE p.registrationStatus = :status")
    Long countByStatus(RegistrationStatus status);

    List<Participant> findByCampIdAndRegistrationStatus(Long campId, RegistrationStatus status);

    @Query("SELECT p FROM Participant p JOIN FETCH p.camp ORDER BY p.registrationDate DESC")
    List<Participant> findAllWithCamp();
}
