package com.fitnesscamp.config;

import com.fitnesscamp.entity.Camp;
import com.fitnesscamp.entity.Participant;
import com.fitnesscamp.entity.User;
import com.fitnesscamp.repository.CampRepository;
import com.fitnesscamp.repository.ParticipantRepository;
import com.fitnesscamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CampRepository campRepository;
    private final ParticipantRepository participantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default admin user
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Administrator")
                    .role(User.Role.ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
        }

        // Create sample camps if none exist
        if (campRepository.count() == 0) {
            Camp camp1 = Camp.builder()
                    .name("Summer HIIT Bootcamp")
                    .description("High-intensity interval training camp designed to boost your metabolism and build endurance. Perfect for intermediate to advanced fitness enthusiasts.")
                    .location("Central Park, New York")
                    .startDate(LocalDate.now().plusDays(30))
                    .endDate(LocalDate.now().plusDays(37))
                    .capacity(25)
                    .price(299.99)
                    .isActive(true)
                    .build();
            campRepository.save(camp1);

            Camp camp2 = Camp.builder()
                    .name("Yoga & Mindfulness Retreat")
                    .description("A week-long retreat focusing on yoga, meditation, and mindfulness practices. Suitable for all skill levels.")
                    .location("Mountain View Resort, Colorado")
                    .startDate(LocalDate.now().plusDays(45))
                    .endDate(LocalDate.now().plusDays(52))
                    .capacity(20)
                    .price(449.99)
                    .isActive(true)
                    .build();
            campRepository.save(camp2);

            Camp camp3 = Camp.builder()
                    .name("Strength Training Fundamentals")
                    .description("Learn proper weightlifting techniques and build a solid foundation for strength training. Ideal for beginners.")
                    .location("Elite Fitness Center, Los Angeles")
                    .startDate(LocalDate.now().plusDays(60))
                    .endDate(LocalDate.now().plusDays(67))
                    .capacity(15)
                    .price(349.99)
                    .isActive(true)
                    .build();
            campRepository.save(camp3);

            Camp camp4 = Camp.builder()
                    .name("Marathon Preparation Camp")
                    .description("Intensive running camp designed to prepare you for marathon competitions. Includes nutrition guidance and injury prevention.")
                    .location("Lakefront Trail, Chicago")
                    .startDate(LocalDate.now().plusDays(75))
                    .endDate(LocalDate.now().plusDays(89))
                    .capacity(30)
                    .price(399.99)
                    .isActive(true)
                    .build();
            campRepository.save(camp4);

            // Create sample participants
            Participant p1 = Participant.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@email.com")
                    .phone("+1-555-0101")
                    .camp(camp1)
                    .registrationStatus(Participant.RegistrationStatus.CONFIRMED)
                    .registrationDate(LocalDateTime.now().minusDays(5))
                    .build();
            participantRepository.save(p1);

            Participant p2 = Participant.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .email("jane.smith@email.com")
                    .phone("+1-555-0102")
                    .camp(camp1)
                    .registrationStatus(Participant.RegistrationStatus.PENDING)
                    .registrationDate(LocalDateTime.now().minusDays(3))
                    .build();
            participantRepository.save(p2);

            Participant p3 = Participant.builder()
                    .firstName("Michael")
                    .lastName("Johnson")
                    .email("michael.j@email.com")
                    .phone("+1-555-0103")
                    .camp(camp2)
                    .registrationStatus(Participant.RegistrationStatus.CONFIRMED)
                    .registrationDate(LocalDateTime.now().minusDays(7))
                    .build();
            participantRepository.save(p3);

            Participant p4 = Participant.builder()
                    .firstName("Emily")
                    .lastName("Brown")
                    .email("emily.brown@email.com")
                    .phone("+1-555-0104")
                    .camp(camp3)
                    .registrationStatus(Participant.RegistrationStatus.PENDING)
                    .registrationDate(LocalDateTime.now().minusDays(1))
                    .build();
            participantRepository.save(p4);
        }
    }
}
