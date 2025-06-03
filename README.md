# 🧑‍💼 Zoho People Clone - HR Management System

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Microservices-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql)
![Eureka](https://img.shields.io/badge/Eureka-Service%20Discovery-orange?style=for-the-badge&logo=spring)
<!--![JWT](https://img.shields.io/badge/JWT-Authentication-yellow?style=for-the-badge&logo=jsonwebtokens)-->
<!--![Docker](https://img.shields.io/badge/Docker-Containerized-lightblue?style=for-the-badge&logo=docker)-->

A fully modularized HR management platform inspired by **Zoho People**, built using Spring Boot Microservices, PostgreSQL, and API Gateway architecture.

---

## 📦 Modules (Microservices)

| Microservice         | Port  | Responsibility |
|----------------------|-------|----------------|
| 🛡️ Auth Service       | 8081  | User login, registration|
| 🏢 Organization Service | 8082  | Manage organizations, licenses |
| 👨‍💼 Employee Service   | 8083  | Employees, Onboarding, Leave, Shift, Timesheet |
| 💰 Payroll Service     | 8084  | Performance, Payroll, Expenses |
| 🌐 API Gateway        | 8080  | Centralized routing |
| 🔍 Eureka Server      | 8761  | Service discovery |

---

## 📐 Architecture

```bash
React (Frontend)
     |
     ▼
[🔐 API Gateway - 8080]
     ├── /auth/**               → Auth Service (8081)
     ├── /api/organization/**   → Organization Service (8082)
     ├── /api/license/**        → Organization Service (8082)
     ├── /api/employees/**      → Employee Service (8083)
     ├── /api/onboarding/**     → Employee Service (8083)
     ├── /api/leave/**          → Employee Service (8083)
     ├── /api/shift/**          → Employee Service (8083)
     ├── /api/timesheet/**      → Employee Service (8083)
     ├── /api/performance/**    → Payroll Service (8084)
     ├── /api/expense/**        → Payroll Service (8084)
     └── /api/payroll/**        → Payroll Service (8084)

```

-----

## 📜 API Documentation & Database Schema

All API endpoints are exposed through the API Gateway. The base URL for all requests is `http://localhost:8080`.

\<br\>

### 🛡️ Auth Service

*Handles user identity, access control, and security. It manages a central `users` table.*

**Base Path**: `/auth`

| Endpoint | Method | Description |
|---|---|---|
| `/employee/register` | `POST` | Registers a new user account for an employee. |
| `/employee/login` | `POST` | Authenticates a user and returns a JWT access token. |
| `/validate-role/{userId}` | `GET` | Retrieves the role (e.g., `ADMIN`, `EMPLOYEE`) for a given user. |
| `/fetchUser/{id}` | `GET` | Fetches public details of a user by their unique ID. |
| `/promote-role` | `PUT` | Elevates a user's role (e.g., from `EMPLOYEE` to `HR_MANAGER`). |
| `/organization/validate-access/{userId}/{orgId}`| `GET` | Confirms if a user is authorized to access a specific organization. |

-----

**Database Schema (`auth_db`)**

**Table: `users`**

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** Unique identifier for the user. |
| `email` | `VARCHAR(255)` | User's email address. **Must be unique and not null.** |
| `password` | `VARCHAR(255)` | Hashed password for the user. **Not null.** |
| `role` | `VARCHAR(50)` | Role of the user (e.g., `SUPER_ADMIN`, `ADMIN`, `EMPLOYEE`). **Not null.** |
| `organization_id` | `UUID` | **Foreign Key** referencing `organizations.id`. The organization the user belongs to. |
| `created_at` | `TIMESTAMP` | Timestamp of when the user account was created. |
| `updated_at` | `TIMESTAMP` | Timestamp of the last update to the user account. |

\<br\>

### 🏢 Organization Service

*Manages company-level data, approvals, and software licensing.*

**Base Paths**: `/api/organization`, `/api/license`

| Endpoint | Method | Description |
|---|---|---|
| `/api/organization` | `POST` | Creates a new organization profile with initial admin users. |
| `/api/organization/{id}` | `GET` | Retrieves the details of a specific organization by its UUID. |
| `/api/organization/approve/{orgId}` | `PUT` | Approves a newly registered organization (requires Super Admin privileges). |
| `/api/license` | `POST` | Generates and assigns a new software license to an organization. |

-----

**Database Schema (`organization_db`)**

**Table: `organizations`**

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** Unique identifier for the organization. |
| `name` | `VARCHAR(255)` | The official name of the organization. **Not null.** |
| `address` | `TEXT` | Physical address of the organization. |
| `status` | `VARCHAR(50)` | Approval status (e.g., `PENDING_APPROVAL`, `APPROVED`). **Not null.** |
| `created_at` | `TIMESTAMP` | Timestamp of when the organization was registered. |

**Table: `licenses`**

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** Unique identifier for the license. |
| `organization_id` | `UUID` | **Foreign Key** referencing `organizations.id`. **Not null.** |
| `license_key` | `VARCHAR(255)` | The unique license key string. **Unique, not null.** |
| `issue_date` | `TIMESTAMP` | The date the license was issued. |
| `expiry_date` | `TIMESTAMP` | The date the license expires. |
| `is_active` | `BOOLEAN` | A flag indicating if the license is currently active. |

\<br\>

### 👨‍💼 Employee Service

*Manages the core employee lifecycle from hiring to daily activities.*

-----

**Database Schema (`employee_db`)**

**Table: `employees`** (for `EmployeeController`)

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** Unique identifier for the employee. |
| `user_id` | `UUID` | User account ID from the Auth Service. **Unique, not null.** |
| `organization_id` | `UUID` | Organization ID from the Organization Service. **Not null.** |
| `first_name` | `VARCHAR(100)` | Employee's first name. **Not null.** |
| `last_name` | `VARCHAR(100)` | Employee's last name. **Not null.** |
| `job_title` | `VARCHAR(100)` | Employee's official job title. |
| `hire_date` | `DATE` | The date the employee was hired. |

**Table: `onboarding_tasks`** (for `OnboardingController`)

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** |
| `employee_id` | `UUID` | **Foreign Key** referencing `employees.id`. **Not null.** |
| `task_description`| `VARCHAR(255)` | Description of the onboarding task (e.g., "Sign employment contract"). |
| `status` | `VARCHAR(50)` | Status of the task (`PENDING`, `COMPLETED`). |
| `due_date` | `DATE` | The target completion date for the task. |

**Table: `leave_requests`** (for `LeaveController`)

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** |
| `employee_id` | `UUID` | **Foreign Key** referencing `employees.id`. **Not null.** |
| `leave_type` | `VARCHAR(50)` | Type of leave (e.g., `ANNUAL`, `SICK`, `UNPAID`). |
| `start_date` | `DATE` | The first day of leave. **Not null.** |
| `end_date` | `DATE` | The last day of leave. **Not null.** |
| `reason` | `TEXT` | Reason for the leave request. |
| `status` | `VARCHAR(50)` | Status of the request (`PENDING`, `APPROVED`, `REJECTED`). |

**Table: `shifts` & `employee_shifts`** (for `ShiftController`)

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** Unique identifier for the shift pattern. |
| `shift_name` | `VARCHAR(100)` | Name of the shift (e.g., "Morning Shift"). |
| `start_time` | `TIME` | The start time of the shift. |
| `end_time` | `TIME` | The end time of the shift. |
| `employee_id` | `UUID` | **In `employee_shifts` table.** Foreign Key to `employees.id`. |
| `shift_id` | `UUID` | **In `employee_shifts` table.** Foreign Key to `shifts.id`. |

**Table: `timesheets`** (for `TimesheetController`)

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** |
| `employee_id` | `UUID` | **Foreign Key** referencing `employees.id`. **Not null.** |
| `log_date` | `DATE` | The date for which the hours are logged. **Not null.** |
| `hours_worked` | `DECIMAL(4, 2)`| Number of hours worked on that day. |
| `status` | `VARCHAR(50)` | Status of the timesheet entry (`SUBMITTED`, `APPROVED`). |

\<br\>

### 💰 Payroll Service

*Manages compensation, expenses, and performance reviews.*

-----

**Database Schema (`payroll_db`)**

**Table: `expenses`** (for `ExpenseController`)

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** |
| `employee_id` | `UUID` | The ID of the employee who submitted the claim. **Not null.** |
| `amount` | `DECIMAL(10, 2)` | The amount of the expense claim. **Not null.** |
| `currency` | `VARCHAR(3)` | Currency code (e.g., USD, INR). |
| `description` | `TEXT` | A brief description of the expense. |
| `status` | `VARCHAR(50)` | Approval status (`PENDING`, `APPROVED`, `REJECTED`). |
| `approved_by` | `UUID` | The ID of the HR/manager who approved it. |
| `transaction_date`| `DATE` | The date the expense was incurred. |

**Table: `performance_reviews`** (for `PerformanceController`)

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** |
| `employee_id` | `UUID` | The ID of the employee being reviewed. **Not null.** |
| `reviewer_id` | `UUID` | The ID of the HR/manager conducting the review. **Not null.** |
| `rating` | `INTEGER` | A numeric rating, e.g., 1 to 5. |
| `comments` | `TEXT` | Detailed feedback and comments. |
| `review_date` | `DATE` | The date the performance review took place. |

**Table: `payrolls`** (for `PayrollController`)

| Column Name | Data Type | Description/Constraints |
|---|---|---|
| `id` | `UUID` | **Primary Key.** |
| `employee_id` | `UUID` | The ID of the employee. **Not null.** |
| `pay_period_start`| `DATE` | The start date of the pay cycle. |
| `pay_period_end` | `DATE` | The end date of the pay cycle. |
| `base_salary` | `DECIMAL(12, 2)`| The employee's gross base salary for the period. |
| `bonuses` | `DECIMAL(12, 2)`| Any bonuses included in the payroll. |
| `deductions` | `DECIMAL(12, 2)`| Total deductions (e.g., taxes, insurance). |
| `net_pay` | `DECIMAL(12, 2)`| The final take-home pay (`base + bonuses - deductions`). |
| `pay_date` | `DATE` | The date the salary was disbursed. |

![Untitled-3](https://github.com/user-attachments/assets/0cd0ad73-9ad1-4319-b36f-eb2a22148514)
