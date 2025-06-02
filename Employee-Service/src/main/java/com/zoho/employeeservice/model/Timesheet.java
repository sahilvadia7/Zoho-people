package com.zoho.employeeservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID employeeId;

    private Date date;
    private Double hoursWorked;
    private Date createdAt;
    private Date updatedAt;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "timesheet_tasks")
    @Column(name = "task_details")
    private List<Task> tasks;

}