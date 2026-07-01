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

# 1. User

Stores all system users.

| Column | Type | Description |
|---------|------|-------------|
| id | Long | Primary Key |
| fullName | String | User Full Name |
| email | String | Unique Email |
| mobile | String | Mobile Number |
| password | String | Encrypted Password |
| role | Enum | ADMIN, RECEPTIONIST, DOCTOR, INTERN |
| status | Enum | ACTIVE, INACTIVE |
| createdAt | Timestamp | Audit |
| updatedAt | Timestamp | Audit |

---

# 2. Doctor

Doctor specific information.

| Column | Type |
|---------|------|
| id | Long |
| userId | FK(User) |
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
| id | Long |
| patientCode | String |
| firstName | String |
| middleName | String |
| lastName | String |
| dob | Date |
| gender | Enum |
| bloodGroup | Enum |
| mobile | String |
| email | String |
| occupation | String |
| address | String |
| emergencyContactName | String |
| emergencyContactNumber | String |
| allergies | Text |
| status | Enum |

---

# 4. Appointment

> **Note:** Appointment currently represents both booking and clinic visit.
> In V2 this may split into Appointment + Visit.

| Column | Type |
|---------|------|
| id | Long |
| patientId | FK |
| doctorId | FK |
| appointmentDate | LocalDate |
| appointmentTime | LocalTime |
| tokenNumber | Integer |
| chiefComplaint | String |
| status | Enum |
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
| id | Long |
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
| id | Long |
| consultationId | FK |
| doctorId | FK |
| patientId | FK |
| notes | Text |

---

# 7. Prescription Item

| Column | Type |
|---------|------|
| id | Long |
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
| id | Long |
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
| id | Long |
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
| id | Long |
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
| id | Long |
| consultationId | FK |
| summary | Text |
| modelVersion | String |
| generatedAt | Timestamp |

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