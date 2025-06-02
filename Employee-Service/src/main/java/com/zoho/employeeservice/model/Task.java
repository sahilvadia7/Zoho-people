package com.zoho.employeeservice.model;

import com.zoho.employeeservice.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Task {
    private String taskId;
    private String description;
    private TaskStatus status;
}
