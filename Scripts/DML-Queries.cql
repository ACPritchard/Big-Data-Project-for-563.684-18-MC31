SELECT story
FROM hacker_news.storiesbyrank
WHERE storyID = 4497789;

SELECT author, count(storyID)
FROM hacker_news.storiesbyauthor
GROUP BY author
LIMIT 250;

SELECT author, sum(score)
FROM hacker_news.storiesbyauthor
GROUP BY author
LIMIT 2500;

SELECT author, storyID, title
FROM hacker_news.storiesbyauthor
WHERE author = 'majorapps'
LIMIT 100;

INSERT INTO hacker_news.storiesbyrank (storyID, score, title, author, story)
VALUES (20000001, 100, 'a title', 'andrew', 'little bo peep');
