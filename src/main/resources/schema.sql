DROP TABLE IF EXISTS RENTAL_AGREEMENT;
DROP TABLE IF EXISTS TOOL;
DROP TABLE IF EXISTS TOOL_TYPE;
DROP TABLE IF EXISTS TOOL_RENTAL;
DROP TABLE IF EXISTS CLERK;
DROP TABLE IF EXISTS CUSTOMER;
DROP TABLE IF EXISTS DISCOUNT;

CREATE TABLE CLERK
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE CUSTOMER
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE RENTAL_AGREEMENT
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id         BIGINT    NOT NULL,
    checkout_date       TIMESTAMP NOT NULL,
    pre_discount_charge DOUBLE,
    chargeable_days     BIGINT,
    discount_percent    BIGINT,
    discount_amount     DOUBLE,
    final_charge        DOUBLE
);

CREATE TABLE DISCOUNT
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    clerk_id            BIGINT NOT NULL,
    rental_agreement_id BIGINT NOT NULL,
    discount_percent    BIGINT NOT NULL
);

CREATE TABLE TOOL_RENTAL
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    tool_id             BIGINT NOT NULL,
    rental_agreement_id BIGINT NOT NULL,
    rental_days         BIGINT NOT NULL,
    due_date            TIMESTAMP
);

CREATE TABLE TOOL
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    tool_type_id BIGINT      NOT NULL,
    tool_code    VARCHAR(50) NOT NULL UNIQUE,
    tool_brand   VARCHAR(50) NOT NULL
);

CREATE TABLE TOOL_TYPE
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    tool_name      VARCHAR(255) NOT NULL,
    daily_charge   DOUBLE       NOT NULL,
    weekly_charge  BOOLEAN      NOT NULL,
    weekend_charge BOOLEAN      NOT NULL,
    holiday_charge BOOLEAN      NOT NULL
);