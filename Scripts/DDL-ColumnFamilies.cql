//This gets articles nad thier authors, and allows sorting by rank
//For top n(25?) ranked stories
CREATE TABLE IF NOT EXISTS hacker_news.storiesbyrank (
  storyID int,
  score int,
  title text,
  author text,
  story text,
  PRIMARY KEY (storyID, score)
) WITH CLUSTERING ORDER BY (score DESC);

//Similar to above, but designed to get summury statistics for authros stories in regards to rank
CREATE TABLE IF NOT EXISTS hacker_news.storiesbyauthor (
  author text,
  storyID int,
  score int,
  title text,
  story text,
  PRIMARY KEY (author, storyID)
) WITH CLUSTERING ORDER BY (storyID DESC);


CREATE TABLE IF NOT EXISTS hacker_news.comments_stories (
  author text,
  unique_id int,
  comment_time timestamp,
  comment_story text,
  type text,
  ranking int,
  PRIMARY KEY (unique_id, author)
);
