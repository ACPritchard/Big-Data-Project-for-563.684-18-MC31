# This Python 3 environment comes with many helpful analytics libraries installed
# It is defined by the kaggle/python docker image: https://github.com/kaggle/docker-python
# For example, here's several helpful packages to load in 

import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)
from google.cloud import bigquery
from bq_helper import BigQueryHelper

# Input data files are available in the "../input/" directory.
# For example, running this (by clicking run or pressing Shift+Enter) will list the files in the input directory

# import os
# print(os.listdir("../input"))

# Any results you write to the current directory are saved as output.

#some queries to extract the data from the hacker news dataset
bq_assistant = BigQueryHelper("bigquery-public-data", "hacker_news")
#pandas_client = bigquery.Client()
#bq_assistant.list_tables()

#Gets the 'stories' table into a csv file
#QUERY1 = """
#    SELECT 
#        *
#    FROM 
#        `bigquery-public-data.hacker_news.stories`
#    """
#bq_assistant.estimate_query_size(QUERY1)
#df = bq_assistant.query_to_pandas_safe(QUERY)
#df.to_csv('stories_test.csv', index = False)


#Gets the 'comments' table into a csv file
QUERY2 = """
    SELECT 
        parent, deleted, dead, ranking
    FROM 
        `bigquery-public-data.hacker_news.comments`
    """
bq_assistant.estimate_query_size(QUERY2)
df = bq_assistant.query_to_pandas(QUERY2)
#queryResult = pandas_client.query(QUERY2)
#df = queryResult.to_dataframe()
df.to_csv('comments_test3.csv', index = False)


#Gets the 'full' table into a csv file
#QUERY3 = """
#    SELECT 
#        *
#    FROM 
#        `bigquery-public-data.hacker_news.full`
#    """
#bq_assistant.estimate_query_size(QUERY3)
#df = bq_assistant.query_to_pandas_safe(QUERY)
#df.to_csv('stories_test.csv', index = False)


#Gets the 'full_201510' table into a csv file

#QUERY4 = """
#    SELECT 
#        *
#    FROM 
#        `bigquery-public-data.hacker_news.full_201510`
#    """
#bq_assistant.estimate_query_size(QUERY4)
#df = bq_assistant.query_to_pandas_safe(QUERY)
#df.to_csv('stories_test.csv', index = False)