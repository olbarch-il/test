CREATE TABLE IF NOT EXISTS holidays (
                                        holiday_id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        country VARCHAR(255) NOT NULL,
                                        date DATE NOT NULL
);
CREATE INDEX IF NOT EXISTS holidays_date_idx ON holidays(date);

CREATE TABLE IF NOT EXISTS postcodes (
                                         postcode_id SERIAL PRIMARY KEY,
                                         code VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS timeslots (
                                         timeslot_id SERIAL PRIMARY KEY,
                                         start_time TIMESTAMP NOT NULL,
                                         end_time TIMESTAMP NOT NULL,
                                         CONSTRAINT unique_timeslots UNIQUE (start_time, end_time)
);



CREATE INDEX IF NOT EXISTS idx_timeslots_start_end_time ON timeslots (start_time, end_time);


CREATE TABLE IF NOT EXISTS timeslots_postcodes (
                                                   timeslot BIGINT NOT NULL,
                                                   postcode BIGINT NOT NULL,
                                                   PRIMARY KEY (timeslot, postcode),
                                                   CONSTRAINT fk_timeslots_postcodes_timeslots FOREIGN KEY (timeslot) REFERENCES timeslots (timeslot_id),
                                                   CONSTRAINT fk_timeslots_postcodes_postcodes FOREIGN KEY (postcode) REFERENCES postcodes (postcode_id)
);

CREATE TABLE IF NOT EXISTS deliveries (
                                          id SERIAL PRIMARY KEY,
                                          status VARCHAR(20),
                                          timeslot_id BIGINT REFERENCES timeslots(timeslot_id)
);
