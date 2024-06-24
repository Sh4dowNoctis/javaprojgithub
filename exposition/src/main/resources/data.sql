CREATE TABLE IF NOT EXISTS app_user (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS shuttle (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    status VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS revision (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        date DATE NOT NULL,
                                        shuttle_id BIGINT NOT NULL,
                                        FOREIGN KEY (shuttle_id) REFERENCES shuttle(id)
    );

CREATE TABLE IF NOT EXISTS flight (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      date_time TIMESTAMP NOT NULL,
                                      shuttle_id BIGINT NOT NULL,
                                      status VARCHAR(255) NOT NULL,
    FOREIGN KEY (shuttle_id) REFERENCES shuttle(id)
    );

CREATE TABLE IF NOT EXISTS flight_travelers (
                                                flight_id BIGINT NOT NULL,
                                                traveler_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flight(id)
    );

INSERT INTO shuttle (name, capacity, status) VALUES ('Navette 1', 5, 'OK');
INSERT INTO shuttle (name, capacity, status) VALUES ('Navette 2', 4, 'OK');
INSERT INTO shuttle (name, capacity, status) VALUES ('Navette 3', 3, 'OK');
INSERT INTO shuttle (name, capacity, status) VALUES ('Navette 4', 5, 'OBSOLETE');

INSERT INTO revision (date, shuttle_id) VALUES ('2024-06-01', 1);
INSERT INTO revision (date, shuttle_id) VALUES ('2024-06-15', 2);
INSERT INTO revision (date, shuttle_id) VALUES ('2024-07-01', 3);

INSERT INTO flight (date_time, shuttle_id, status) VALUES ('2024-07-10T10:00:00', 1, 'WAITING_FOR_GEARCHECK');
INSERT INTO flight (date_time, shuttle_id, status) VALUES ('2024-08-15T12:00:00', 2, 'OK');
INSERT INTO flight (date_time, shuttle_id, status) VALUES ('2024-09-20T14:00:00', 3, 'WAITING_FOR_GEARCHECK');
INSERT INTO flight (date_time, shuttle_id, status) VALUES ('2024-10-25T16:00:00', 1, 'WAITING_FOR_GEARCHECK');

INSERT INTO flight_travelers (flight_id, traveler_email) VALUES (1, 'romain@mail.com');
INSERT INTO flight_travelers (flight_id, traveler_email) VALUES (2, 'romain@mail.com');
INSERT INTO flight_travelers (flight_id, traveler_email) VALUES (3, 'romain@mail.com');