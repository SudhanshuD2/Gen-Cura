INSERT INTO user_roles
(active, created_at, updated_at, role)
VALUES
(1, NOW(), NOW(), 'ROLE_SUPER_ADMIN'),
(1, NOW(), NOW(), 'ROLE_ADMIN'),
(1, NOW(), NOW(), 'ROLE_DOCTOR'),
(1, NOW(), NOW(), 'ROLE_ASSISTANT_DOCTOR'),
(1, NOW(), NOW(), 'ROLE_RECEPTIONIST');