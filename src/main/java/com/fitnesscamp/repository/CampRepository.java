package com.fitnesscamp.repository;

import com.fitnesscamp.entity.Camp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampRepository extends JpaRepository<Camp, Long> {

    List<Camp> findByIsActiveTrue();

    List<Camp> findByIsActiveTrueAndStartDateAfter(LocalDate date);

    @Query("SELECT c FROM Camp c WHERE c.isActive = true AND c.startDate > :date ORDER BY c.startDate ASC")
    List<Camp> findUpcomingCamps(LocalDate date);

    @Query("SELECT c FROM Camp c LEFT JOIN FETCH c.participants WHERE c.id = :id")
    Camp findByIdWithParticipants(Long id);

    List<Camp> findByNameContainingIgnoreCase(String name);
}
