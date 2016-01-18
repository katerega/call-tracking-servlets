CREATE TABLE lead_sources (
  id SERIAL UNIQUE NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  incoming_number_national      VARCHAR(50) NOT NULL,
  incoming_number_international      VARCHAR(50) NOT NULL,
  forwarding_number      VARCHAR(50) NOT NULL,
);

CREATE TABLE leads (
  id SERIAL         UNIQUE NOT NULL PRIMARY KEY,
  phone_number      VARCHAR(50) NOT NULL,
  city              VARCHAR(50) NOT NULL,
  state             VARCHAR(50) NOT NULL,
  lead_source_id    INT4          NOT NULL,
  FOREIGN KEY (lead_source_id) REFERENCES lead_sources (id)
);

