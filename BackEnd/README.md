# Backend
# 🏥 [GEN-CURA] Clinic Management System (V1) - Database Design

> **Version:** 1.0.0
>
> This document describes the database design for the Clinic Management System backend.
>
> The application is designed around the complete patient consultation workflow while keeping the architecture scalable for future Hospital Management System (HMS) modules.

---

# 📌 Core Workflow

```
Patient Calls
      │
      ▼
Reception Books Appointment
      │
      ▼
Patient Visits Clinic
      │
      ▼
Reception Check-In
      │
      ▼
Doctor Consultation
      │
      ▼
Prescription + Chargeable Services
      │
      ▼
Reception Generates Bill
      │
      ▼
Payment Collection
      │
      ▼
AI Summary Generated
```

---

# Entity Relationship Overview

```
User
 │
 ├───────────────┐
 │               │
Doctor        Reception(Admin)

Patient
   │
   ├──────────── Appointment
   │                  │
   │                  │ 1 : 1
   │                  ▼
   │             Consultation
   │                  │
   │         ┌────────┴────────┐
   │         │                 │
   │         ▼                 ▼
   │   Prescription       AI Summary
   │         │
   │         ▼
   │   Prescription Item
   │
   └───────────────────────────────

Appointment
      │
      ▼
Bill
      │
      ▼
Bill Item
      │
      ▼
Chargeable Service
```
---

# 0. BaseEntity

Mapped supper class used by all the entities.

| Column | Type | Description |
|---------|------|-------------|
| id | Long | Primary Key |
| isActive | boolean | true, false |
| createdAt | Timestamp | Audit |
| updatedAt | Timestamp | Audit |

---

# 1. User

Stores all system users.

| Column | Type | Description |
|---------|------|-------------|
| fullName | String | User Full Name |
| email | String | Unique Email |
| mobile | String | Mobile Number |
| password | String | Encrypted Password |
| role | Enum | ADMIN, RECEPTIONIST, DOCTOR, INTERN |

---

# 2. Doctor

Doctor specific information.

| Column | Type |
|---------|------|
| specialization | String |
| qualification | String |
| registrationNumber | String |
| consultationFee | Decimal |
| experienceYears | Integer |
| isAvailable | Boolean |

---

# 3. Patient

| Column | Type |
|---------|------|
| patientCode | String |
| fullName | String |
| dob | Date |
| gender | Enum |
| bloodGroup | Enum |
| mobile | String |
| email | String |
| address | String |
| emergencyContactName | String |
| emergencyContactNumber | String |
| allergies | Text |

---

# 4. Appointment

> **Note:** Appointment currently represents both booking and clinic visit.
> In V2 this may split into Appointment + Visit.

| Column | Type |
|---------|------|
| patientId | FK |
| doctorId | FK |
| appointmentSchedule | LocalDate |
| tokenNumber | Integer |
| chiefComplaint | String |
| appointmentStatus | Enum |
| bookedBy | FK(User) |
| remarks | String |

### Appointment Status

```
BOOKED
↓
CHECKED_IN
↓
IN_CONSULTATION
↓
CONSULTATION_COMPLETED
↓
PAYMENT_PENDING
↓
COMPLETED
```

Additional States

- CANCELLED

---

# 5. Consultation

Medical consultation record.

| Column | Type |
|---------|------|
| appointmentId | FK |
| patientId | FK |
| doctorId | FK |
| diagnosis | Text |
| clinicalNotes | Text |
| advice | Text |
| followUpDate | Date |

### Rules

- Immutable
- No Update API
- Only Create & Read

---

# 6. Prescription

| Column | Type |
|---------|------|
| consultationId | FK |
| doctorId | FK |
| patientId | FK |
| notes | Text |

---

# 7. Prescription Item

| Column | Type |
|---------|------|
| prescriptionId | FK |
| medicineName | String |
| dosage | String |
| frequency | String |
| duration | String |
| instructions | String |

---

# 8. Chargeable Service

Master list of services offered by clinic.

| Column | Type |
|---------|------|
| serviceCode | String |
| serviceName | String |
| category | Enum |
| price | Decimal |
| description | String |
| active | Boolean |

### Example Records

| Code | Service | Price |
|------|----------|------:|
| CONSULT | Consultation | 300 |
| INJ | Injection | 100 |
| ECG | ECG | 400 |
| SALINE | IV Saline | 250 |
| DRESS | Dressing | 150 |
| NEB | Nebulization | 200 |

---

# 9. Bill

One bill per appointment.

| Column | Type |
|---------|------|
| appointmentId | FK |
| patientId | FK |
| totalAmount | Decimal |
| discount | Decimal |
| finalAmount | Decimal |
| paidAmount | Decimal |
| paymentMode | Enum |
| status | Enum |
| generatedBy | FK(User) |

### Bill Status

```
DRAFT
↓
FINALIZED
↓
PAID
```

---

# 10. Bill Item

| Column | Type |
|---------|------|
| billId | FK |
| chargeableServiceId | FK |
| quantity | Integer |
| unitPrice | Decimal |
| totalPrice | Decimal |
| remarks | String |

---

# 11. AI Summary

Stores AI-generated consultation summary.

| Column | Type |
|---------|------|
| consultationId | FK |
| summary | Text |
| modelVersion | String |

---

# Enumerations

## UserRole

```
ADMIN
RECEPTIONIST
DOCTOR
INTERN
```

---

## UserStatus

```
ACTIVE
INACTIVE
```

---

## Gender

```
MALE
FEMALE
OTHER
NOT_SPECIFIED
```

---

## BloodGroup

```
A_POSITIVE
A_NEGATIVE
B_POSITIVE
B_NEGATIVE
AB_POSITIVE
AB_NEGATIVE
O_POSITIVE
O_NEGATIVE
```

---

## AppointmentStatus

```
BOOKED
CHECKED_IN
IN_CONSULTATION
CONSULTATION_COMPLETED
PAYMENT_PENDING
COMPLETED
CANCELLED
```

---

## PaymentMode

```
CASH
UPI
CARD
```

---

## BillStatus

```
DRAFT
FINALIZED
PAID
```

---

## ServiceCategory

```
CONSULTATION
PROCEDURE
DIAGNOSTIC
THERAPY
OTHER
```

---

# Design Principles

- Consultation records are immutable.
- Prescription records are immutable.
- AI Summary is generated after consultation.
- Chargeable services are configurable.
- Bills remain editable only in `DRAFT` state.
- Appointment follows a controlled state machine.
- Designed for future migration to:
  - Appointment → Visit
  - Pharmacy Module
  - Laboratory Module
  - Inventory Module
  - Insurance Module
  - Admission/IPD Module