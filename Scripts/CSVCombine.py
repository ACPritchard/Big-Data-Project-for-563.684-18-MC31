#
#This is a simple script to merge CSV files
#
import os
import pandas as pd
import numpy as np


#Defines the folder that the csv files are in
sourceFolder = os.path.join('f:', os.sep, 'ProjectData', 'CombTest') + os.sep

#THe csv that this will append too
writeFile = sourceFolder + 'Full-Complete.csv'

#A list of the csv files to read from
files = [
    sourceFolder + 'full_test-rows-100001-1000000.csv',
    sourceFolder + 'full_test-rows-1000001-3000000.csv',
    sourceFolder + 'full_test-rows-3000001-5000000.csv',
    sourceFolder + 'full_test-rows-5000001-7000000.csv',
    sourceFolder + 'full_test-rows-7000001-9000000.csv',
    sourceFolder + 'full_test-rows-9000001-11000000.csv',
    sourceFolder + 'full_test-rows-11000001-13000000.csv',
    sourceFolder + 'full_test-rows-13000001-15000000.csv',
    sourceFolder + 'full_test-rows-15000001-17000000.csv',
    sourceFolder + 'full_test-rows-17000001-19000000.csv'
]

#just a variable to keep track of the writes
i = 0

for source in files:
    df = pd.read_csv(source, skiprows=0, chunksize=10000)
    for chunk in df:
        chunk.to_csv(writeFile, mode='a', index=False, header=False)
        print(i*10000, '\n')
        i += 1
