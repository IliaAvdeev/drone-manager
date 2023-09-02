CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA public;

CREATE TABLE drone
(
    id               uuid DEFAULT public.uuid_generate_v4(),
    serial_number    text,
    model            text,
    weight_limit     integer,
    battery_capacity integer,
    state            text,
    CONSTRAINT drone_pk PRIMARY KEY (id)
);

CREATE TABLE drone_check_battery_level_log
(
    id            uuid DEFAULT public.uuid_generate_v4(),
    drone_id      uuid NOT NULL,
    battery_level integer,
    CONSTRAINT drone_check_battery_level_log_pk PRIMARY KEY (id),
    CONSTRAINT fk_drone_check_battery_level_log_drone_id FOREIGN KEY (drone_id) REFERENCES drone (id) ON DELETE CASCADE
);

CREATE TABLE medication
(
    id     uuid DEFAULT public.uuid_generate_v4(),
    name   text,
    weight integer,
    code   text,
    image  text,
    CONSTRAINT medication_pk PRIMARY KEY (id)
);

INSERT INTO medication (id, name, weight, code, image)
VALUES ('c528e3d1-d627-414a-bc46-1d8371200b0d',
        'Ibuprofen', 100, 'C01EB16',
        'https://www.biovea.com/ru/images/products/xlrg/7188_z.jpg'),
       ('221dd5b3-6b59-4d9b-aeb1-f35b716bf911',
        'Chloropyramine', 50, 'D04AA09',
        'https://carefromnature.co.uk/image/cache/catalog/SOPHARMA/allergosan%20tbl-850x850.jpg'),
       ('c9e13d59-0a72-4c19-8461-13be00bf5163',
        'Penicillin', 70, 'J01C',
        'https://www.news-medical.net/image.axd?picture=2021%2F5%2Fshutterstock_1314478190-1.jpg'),

       ('4a1e9cf5-c882-4ffd-bb2b-46480849b08d',
        'Pancreatin', 130, 'J01C',
        'https://kulturist1.ru/upload/iblock/d4e/d4ed671cc5153aaeaee519002c049f01.jpg'),

       ('be211a87-4095-410d-b6f2-18da84053463',
        'Insulin', 150, '1A7F',
        'https://i0.wp.com/studyfinds.org/wp-content/uploads/2020/10/AdobeStock_300785232-scaled.jpeg');

CREATE TABLE drone_load_item
(
    id            uuid DEFAULT public.uuid_generate_v4(),
    drone_id      uuid    NOT NULL,
    medication_id uuid    NOT NULL,
    quantity      integer NOT NULL,
    CONSTRAINT drone_to_medication_pk PRIMARY KEY (id),
    CONSTRAINT fk_drone_load_item_drone_id FOREIGN KEY (drone_id) REFERENCES drone (id) ON DELETE CASCADE,
    CONSTRAINT fk_drone_load_item_medication_id FOREIGN KEY (medication_id) REFERENCES medication (id) ON DELETE CASCADE
);

CREATE TABLE drone_lifecycle_log
(
    id                   uuid DEFAULT public.uuid_generate_v4(),
    drone_id             uuid           NOT NULL,
    state_from           text,
    state_to             text           NOT NULL,
    transition_timestamp timestamptz(3) NOT NULL,
    CONSTRAINT drone_lifecycle_log_pk PRIMARY KEY (id),
    CONSTRAINT fk_drone_lifecycle_log_drone_id FOREIGN KEY (drone_id) REFERENCES drone (id) ON DELETE CASCADE
);