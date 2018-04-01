DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS flats;
DROP TABLE IF EXISTS estates;
DROP TABLE IF EXISTS agents;

DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS ad_seq;
DROP SEQUENCE IF EXISTS agent_seq;

CREATE SEQUENCE user_seq START 100;
CREATE SEQUENCE ad_seq START 100;
CREATE SEQUENCE agent_seq START 100;

-- таблица с пользователями приложения
CREATE TABLE users (
  id               INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  name             VARCHAR                  NOT NULL,
  email            VARCHAR                  NOT NULL,
  password         VARCHAR                  NOT NULL,
  registered       TIMESTAMP DEFAULT now()  NOT NULL,
  enabled          BOOL DEFAULT TRUE        NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

-- таблица с ролями пользователей приложения
CREATE TABLE user_roles (
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- таблица с собственниками объектов объявлений
CREATE TABLE agents (
  id          INTEGER PRIMARY KEY DEFAULT nextval('agent_seq'),
  name        VARCHAR,
  surname     VARCHAR,
  patronymic  VARCHAR,
  registered  TIMESTAMP DEFAULT now()  NOT NULL,
  cheater     BOOL DEFAULT FALSE       NOT NULL,
  phone       VARCHAR                  NOT NULL,
  add_phone   VARCHAR,
  CONSTRAINT agent_phone_idx UNIQUE (phone)
);
CREATE UNIQUE INDEX agents_unique_phone_idx ON agents (phone);
CREATE INDEX agents_add_phone_idx ON agents (add_phone);

-- таблица с объявлениями (квартиры)
CREATE TABLE flats (
  id              INTEGER   PRIMARY KEY DEFAULT nextval('ad_seq'),
  agent_id        INTEGER            NOT NULL,
  outside_id      VARCHAR,
  ad_type         VARCHAR            NOT NULL,
  appointment     VARCHAR,
  active          BOOL DEFAULT TRUE  NOT NULL,
  city            VARCHAR,
  district        VARCHAR,
  street          VARCHAR,
  address         VARCHAR,
  count_room      INTEGER,
  floor           INTEGER,
  count_floor     INTEGER,
  area            NUMERIC,
  live_area       NUMERIC,
  kitchen_area    NUMERIC,
  measure_of_area VARCHAR,
  material        VARCHAR,
  price           NUMERIC,
  currency        VARCHAR,
  agent_is_owner  BOOL,
  balcony         BOOL,
  furniture       BOOL,
  appliances      BOOL,
  creation_dt     TIMESTAMP DEFAULT now()  NOT NULL,
  change_dt       TIMESTAMP DEFAULT now()  NOT NULL,
  description     TEXT,
  FOREIGN KEY (agent_id) REFERENCES agents (id) ON DELETE CASCADE
);
CREATE INDEX flats_agent_idx ON flats (agent_id);
CREATE UNIQUE INDEX flats_unique_outside_id_idx ON flats (outside_id);

-- таблица с объявлениями (недвижимость)
CREATE TABLE estates (
  id                    INTEGER   PRIMARY KEY DEFAULT nextval('ad_seq'),
  agent_id              INTEGER            NOT NULL,
  outside_id            VARCHAR,
  ad_type               VARCHAR            NOT NULL,
  appointment           VARCHAR,
  active                BOOL DEFAULT TRUE  NOT NULL,
  city                  VARCHAR,
  district              VARCHAR,
  street                VARCHAR,
  address               VARCHAR,
  count_floor           INTEGER,
  floor                 INTEGER,
  area                  NUMERIC,
  measure_of_area       VARCHAR,
  build_area            NUMERIC,
  measure_of_build_area VARCHAR,
  material              VARCHAR,
  price                 NUMERIC,
  currency              VARCHAR,
  agent_is_owner        BOOL,
  gas                   BOOL,
  water                 BOOL,
  creation_dt           TIMESTAMP DEFAULT now()  NOT NULL,
  change_dt             TIMESTAMP DEFAULT now()  NOT NULL,
  description           TEXT,
  FOREIGN KEY (agent_id) REFERENCES agents (id) ON DELETE CASCADE
);
CREATE INDEX estates_agent_idx ON estates (agent_id);
CREATE UNIQUE INDEX estates_unique_outside_id_idx ON estates (outside_id);