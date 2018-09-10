//Simple Strategy used as we only have the one data center
CREATE KEYSPACE IF NOT EXISTS Hacker_News
WITH REPLICATION = {
  'class': 'SimpleStrategy',
  'replication_factor' : 1
};
