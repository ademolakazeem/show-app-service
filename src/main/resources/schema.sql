--DROP TABLE IF EXISTS actors cascade;
--DROP TABLE IF EXISTS episodes cascade;
--DROP TABLE IF EXISTS genres cascade;
--DROP TABLE IF EXISTS seasons cascade;
--DROP TABLE IF EXISTS shows cascade;
--DROP TABLE IF EXISTS users cascade;
--DROP TABLE IF EXISTS shows_casts cascade;
--DROP TABLE IF EXISTS shows_genres cascade;

CREATE TABLE IF NOT EXISTS users(
  id SERIAL PRIMARY KEY NOT NULL,
   username VARCHAR NOT NULL UNIQUE,
   is_admin BOOLEAN DEFAULT false,
   created TIMESTAMP DEFAULT NOW(),
  updated TIMESTAMP DEFAULT NOW() CHECK (created<=updated)

);

CREATE TABLE IF NOT EXISTS shows(
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR NOT NULL UNIQUE,
  description VARCHAR,
  start_date DATE,
  end_date DATE,
  country VARCHAR,
  runtime VARCHAR,
  network VARCHAR,
  image_path VARCHAR,
  status VARCHAR DEFAULT 'private'
);

CREATE TABLE IF NOT EXISTS casts(
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR NOT NULL UNIQUE,
  created TIMESTAMP DEFAULT NOW(),
  updated TIMESTAMP DEFAULT NOW() CHECK (created<=updated)
);

CREATE TABLE IF NOT EXISTS genres(
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR NOT NULL UNIQUE,
  created TIMESTAMP DEFAULT NOW(),
  updated TIMESTAMP DEFAULT NOW() CHECK (created<=updated)
);


CREATE TABLE IF NOT EXISTS shows_casts(
  show_id INTEGER NOT NULL,
  cast_id INTEGER NOT NULL,
  PRIMARY KEY(show_id, cast_id),
  FOREIGN KEY(show_id) REFERENCES shows(id),
  FOREIGN KEY(cast_id) REFERENCES casts(id)

);
CREATE TABLE IF NOT EXISTS shows_genres(
  show_id INTEGER NOT NULL,
  genre_id INTEGER NOT NULL,
  PRIMARY KEY(show_id, genre_id),
  FOREIGN KEY(show_id) REFERENCES shows(id),
  FOREIGN KEY(genre_id) REFERENCES genres(id)
);


CREATE TABLE IF NOT EXISTS seasons(
  id SERIAL PRIMARY KEY NOT NULL,
  season_number INTEGER NOT NULL UNIQUE,
  first_aired DATE NOT NULL,
  last_aired DATE NOT NULL CHECK (first_aired<=last_aired),
  show_id INTEGER NOT NULL,
  FOREIGN KEY(show_id) REFERENCES shows(id)
);

CREATE TABLE IF NOT EXISTS episodes(
  id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR NOT NULL UNIQUE,
  aired_date DATE NOT NULL,
  season_id INTEGER NOT NULL,
  FOREIGN KEY(season_id) REFERENCES seasons(id)
);



