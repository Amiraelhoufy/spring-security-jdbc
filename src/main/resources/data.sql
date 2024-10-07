INSERT INTO users (username, password, enabled)
VALUES ('blah', '$2a$10$ndFk0XaCqG.Bq/sbsT8J8OSQ5eN35UpFsCBr/O2B/BzIej.hPjsYO', true);

INSERT INTO authorities (username, authority) VALUES ('blah', 'ROLE_USER');

INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$vl9cp5X59liEqRz0VbXfjuE4awAq4PszarRfb4U1jpaE36Bch4jBu', true);

INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
