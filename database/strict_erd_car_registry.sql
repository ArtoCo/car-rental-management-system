CREATE TABLE IF NOT EXISTS car_registry (
    plate_number varchar(45) NOT NULL,
    PRIMARY KEY (plate_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO car_registry (plate_number)
SELECT CARS_PLATENUMBER FROM cars_info
WHERE CARS_PLATENUMBER IS NOT NULL AND TRIM(CARS_PLATENUMBER) <> ''
UNION
SELECT CARS_PLATENUMBER FROM cars_deleted
WHERE CARS_PLATENUMBER IS NOT NULL AND TRIM(CARS_PLATENUMBER) <> ''
UNION
SELECT CARS_PLATENUMBER FROM car_added
WHERE CARS_PLATENUMBER IS NOT NULL AND TRIM(CARS_PLATENUMBER) <> ''
UNION
SELECT plate_number FROM customer
WHERE plate_number IS NOT NULL AND TRIM(plate_number) <> '';

DROP TRIGGER IF EXISTS trg_cars_info_register_plate_bi;
DROP TRIGGER IF EXISTS trg_cars_info_register_plate_bu;
DROP TRIGGER IF EXISTS trg_cars_deleted_register_plate_bi;
DROP TRIGGER IF EXISTS trg_cars_deleted_register_plate_bu;
DROP TRIGGER IF EXISTS trg_car_added_register_plate_bi;
DROP TRIGGER IF EXISTS trg_car_added_register_plate_bu;

DELIMITER $$

CREATE TRIGGER trg_cars_info_register_plate_bi
BEFORE INSERT ON cars_info
FOR EACH ROW
BEGIN
    IF NEW.CARS_PLATENUMBER IS NOT NULL AND TRIM(NEW.CARS_PLATENUMBER) <> '' THEN
        INSERT IGNORE INTO car_registry (plate_number) VALUES (NEW.CARS_PLATENUMBER);
    END IF;
END$$

CREATE TRIGGER trg_cars_info_register_plate_bu
BEFORE UPDATE ON cars_info
FOR EACH ROW
BEGIN
    IF NEW.CARS_PLATENUMBER IS NOT NULL AND TRIM(NEW.CARS_PLATENUMBER) <> '' THEN
        INSERT IGNORE INTO car_registry (plate_number) VALUES (NEW.CARS_PLATENUMBER);
    END IF;
END$$

CREATE TRIGGER trg_cars_deleted_register_plate_bi
BEFORE INSERT ON cars_deleted
FOR EACH ROW
BEGIN
    IF NEW.CARS_PLATENUMBER IS NOT NULL AND TRIM(NEW.CARS_PLATENUMBER) <> '' THEN
        INSERT IGNORE INTO car_registry (plate_number) VALUES (NEW.CARS_PLATENUMBER);
    END IF;
END$$

CREATE TRIGGER trg_cars_deleted_register_plate_bu
BEFORE UPDATE ON cars_deleted
FOR EACH ROW
BEGIN
    IF NEW.CARS_PLATENUMBER IS NOT NULL AND TRIM(NEW.CARS_PLATENUMBER) <> '' THEN
        INSERT IGNORE INTO car_registry (plate_number) VALUES (NEW.CARS_PLATENUMBER);
    END IF;
END$$

CREATE TRIGGER trg_car_added_register_plate_bi
BEFORE INSERT ON car_added
FOR EACH ROW
BEGIN
    IF NEW.CARS_PLATENUMBER IS NOT NULL AND TRIM(NEW.CARS_PLATENUMBER) <> '' THEN
        INSERT IGNORE INTO car_registry (plate_number) VALUES (NEW.CARS_PLATENUMBER);
    END IF;
END$$

CREATE TRIGGER trg_car_added_register_plate_bu
BEFORE UPDATE ON car_added
FOR EACH ROW
BEGIN
    IF NEW.CARS_PLATENUMBER IS NOT NULL AND TRIM(NEW.CARS_PLATENUMBER) <> '' THEN
        INSERT IGNORE INTO car_registry (plate_number) VALUES (NEW.CARS_PLATENUMBER);
    END IF;
END$$

DELIMITER ;

ALTER TABLE cars_info
    ADD INDEX idx_cars_info_registry_plate (CARS_PLATENUMBER);

ALTER TABLE cars_deleted
    ADD INDEX idx_cars_deleted_registry_plate (CARS_PLATENUMBER);

ALTER TABLE car_added
    ADD INDEX idx_car_added_registry_plate (CARS_PLATENUMBER);

ALTER TABLE customer
    ADD INDEX idx_customer_registry_plate (plate_number);

ALTER TABLE cars_info
    ADD CONSTRAINT fk_cars_info_registry_plate
    FOREIGN KEY (CARS_PLATENUMBER)
    REFERENCES car_registry (plate_number)
    ON UPDATE CASCADE
    ON DELETE RESTRICT;

ALTER TABLE cars_deleted
    ADD CONSTRAINT fk_cars_deleted_registry_plate
    FOREIGN KEY (CARS_PLATENUMBER)
    REFERENCES car_registry (plate_number)
    ON UPDATE CASCADE
    ON DELETE RESTRICT;

ALTER TABLE car_added
    ADD CONSTRAINT fk_car_added_registry_plate
    FOREIGN KEY (CARS_PLATENUMBER)
    REFERENCES car_registry (plate_number)
    ON UPDATE CASCADE
    ON DELETE RESTRICT;

ALTER TABLE customer
    ADD CONSTRAINT fk_customer_registry_plate
    FOREIGN KEY (plate_number)
    REFERENCES car_registry (plate_number)
    ON UPDATE CASCADE
    ON DELETE RESTRICT;
