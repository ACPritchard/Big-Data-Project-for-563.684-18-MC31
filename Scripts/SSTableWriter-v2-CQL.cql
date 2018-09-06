CREATE TABLE IF NOT EXISTS dummy_db.test_tbl (
  id int,
  byUser text,
  score int,
  date timestamp,
  title text,
  type text,
  url text,
  body text,
  parent int,
  deleted boolean,
  dead boolean,
  descendants int,
  ranking int,
  PRIMARY KEY (id, date)
) WITH CLUSTERING ORDER BY (date DESC);

SELECT id, date, byUser, score, title, type, parent, deleted, dead, descendants, ranking
FROM dummy_db.test_tbl
LIMIT 5;
