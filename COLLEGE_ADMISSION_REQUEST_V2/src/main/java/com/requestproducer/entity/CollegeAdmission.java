package com.requestproducer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COLLEGE_ADMISSION_REQUEST", schema = "COLLEGE_ADMIN")
@SequenceGenerator(name = "college_admission_seq", sequenceName = "COLLEGE_ADMISSION_SEQ", allocationSize = 1)
public class CollegeAdmission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "college_admission_seq")
    @Column(name = "REQUEST_ID")
    private Long requestId;
    @Column(name = "FIRST_NAME")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can only contain letters and spaces")
    private String firstName;
    @Column(name = "LAST_NAME")
    @NotBlank(message = "Second name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can only contain letters and spaces")
    private String lastName;
    @Column(name = "DATE_OF_BIRTH")
    @Past(message = "Date must be in the past")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dateOfBirth;
    @Column(name = "EMAIL")
    @Email(message = "Email is invalid")
    private String email;
    @Column(name = "HALL_TICKET_NUMBER")
    @NotNull(message = "Hall Ticket Number is missing")
    private String hallTicketNumber;
    @Column(name = "INTERMEDIATE_MARKS")
    @NotNull(message = "Marks cannot be null")
    @Min(value = 600, message = "Admission cannot be processed, Marks must be at least 600")
    @Max(value = 994, message = "You are lying, Marks must be at most 994")
    private Integer intermediateMarks;
    @Column(name = "INTERESTED_STREAM")
    @NotNull(message = "Stream cannot be null")
    private String interestedStream;
    @Column(name = "STATUS")
    private String status = "YET TO PROCESS";
}
