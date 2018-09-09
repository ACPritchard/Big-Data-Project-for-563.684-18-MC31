/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandraapi;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.sql.Types.NULL;
import java.util.Date;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author Andrew
 */
public class BulkLoader {
    public static void storiesByRankTable (String filePath, String savePath, String keyspace) throws FileNotFoundException, IOException {
        String columnFamilyName = "storiesByRank";
        String tblSchema = String.format("CREATE TABLE %s.%s (" +
                                                "storyID int, " +
                                                "score int, " +
                                                "title text, " +
                                                "author text, " +
                                                "story text, " +
                                                "PRIMARY KEY (storyID, score)" +
                                                ") WITH CLUSTERING ORDER BY (score DESC)", keyspace, columnFamilyName);

        String tblInsert = String.format("INSERT INTO %s.%s (" +
                                                "storyID, score, title, author, story" +
                                                ") VALUES (" +
                                                "?, ?, ?, ?, ?" +
                                                ")", keyspace, columnFamilyName);
        File outputDir = new File(savePath + File.separator + keyspace + File.separator + columnFamilyName);
        if (!outputDir.exists() && !outputDir.mkdirs())
        {
            throw new RuntimeException("Cannot create output directory: " + outputDir);
        }
        File file = new File(filePath);

        //Sets up the CQL table writer
        CQLSSTableWriter.Builder builder = CQLSSTableWriter.builder();
        //System.out.print(2);
        builder.inDirectory(outputDir)
                .forTable(tblSchema)
                .using(tblInsert)
                .withBufferSizeInMB(64)
                .withPartitioner(new Murmur3Partitioner());
        CQLSSTableWriter writer = builder.build();

        //Sets up CSV Reader
        CSVParserBuilder parserBuilder = new CSVParserBuilder();
        parserBuilder.withEscapeChar('\0');
        CSVParser parser = parserBuilder.build();

        FileReader filereader = new FileReader(file);//gets the CSV file of interest as a Java IO file
        CSVReaderBuilder csvBuild = new CSVReaderBuilder(filereader);//Using the built in csv buiilder rather than constructing directly
        csvBuild.withSkipLines(1)
                .withCSVParser(parser);//THe only option I want set, to skip the header line
        CSVReader csvReader = csvBuild.build();//Instatiates the csv reader object

        String[] nextRecord;

        int count = 0;
        while ((nextRecord = csvReader.readNext()) != null) {
            writer.addRow(
                    NumberUtils.toInt(nextRecord[12], NULL),
                    NumberUtils.toInt(nextRecord[1], NULL),
                    nextRecord[4],
                    nextRecord[0],
                    nextRecord[7]
            );
            System.out.print(count + "\n");
            count++;
        }
        writer.close();
    }

    public static void storiesByAuthorTable (String filePath, String savePath, String keyspace) throws FileNotFoundException, IOException {
        String columnFamilyName = "storiesByAuthor";
        String tblSchema = String.format("CREATE TABLE %s.%s (" +
                                                "storyID int, " +
                                                "author text, " +
                                                "score int, " +
                                                "title text, " +
                                                "story text, " +
                                                "PRIMARY KEY (storyID, author)" +
                                                ") WITH CLUSTERING ORDER BY (author DESC)", keyspace, columnFamilyName);

        String tblInsert = String.format("INSERT INTO %s.%s (" +
                                                "storyID, author, score, title, story" +
                                                ") VALUES (" +
                                                "?, ?, ?, ?, ?" +
                                                ")", keyspace, columnFamilyName);
        File outputDir = new File(savePath + File.separator + keyspace + File.separator + columnFamilyName);
        if (!outputDir.exists() && !outputDir.mkdirs())
        {
            throw new RuntimeException("Cannot create output directory: " + outputDir);
        }
        File file = new File(filePath);

        //Sets up the CQL table writer
        CQLSSTableWriter.Builder builder = CQLSSTableWriter.builder();
        //System.out.print(2);
        builder.inDirectory(outputDir)
                .forTable(tblSchema)
                .using(tblInsert)
                .withBufferSizeInMB(64)
                .withPartitioner(new Murmur3Partitioner());
        CQLSSTableWriter writer = builder.build();

        //Sets up CSV Reader
        CSVParserBuilder parserBuilder = new CSVParserBuilder();
        parserBuilder.withEscapeChar('\0');
        CSVParser parser = parserBuilder.build();

        FileReader filereader = new FileReader(file);//gets the CSV file of interest as a Java IO file
        CSVReaderBuilder csvBuild = new CSVReaderBuilder(filereader);//Using the built in csv buiilder rather than constructing directly
        csvBuild.withSkipLines(1)
                .withCSVParser(parser);//THe only option I want set, to skip the header line
        CSVReader csvReader = csvBuild.build();//Instatiates the csv reader object

        String[] nextRecord;

        int count = 0;
        while ((nextRecord = csvReader.readNext()) != null) {
            writer.addRow(
                    NumberUtils.toInt(nextRecord[12], NULL),
                    nextRecord[0],
                    NumberUtils.toInt(nextRecord[1], NULL),
                    nextRecord[4],
                    nextRecord[7]
            );
            System.out.print(count + "\n");
            count++;
        }
        writer.close();
    }

    public static void comments_stories_Table (String filePath, String savePath, String keyspace) throws FileNotFoundException, IOException {
        String columnFamilyName = "comments_Stories";
        String tblSchema = String.format("CREATE TABLE %s.%s (" +
                                                "unique_id int, " +
                                                "author text, " +
                                                "comment_time timestamp, " +
                                                "comment_story text, " +
                                                "type text, " +
                                                "ranking int," +
                                                "PRIMARY KEY (unique_id, author)" +
                                                ")", keyspace, columnFamilyName);

        String tblInsert = String.format("INSERT INTO %s.%s (" +
                                                "unique_id, author, comment_time, comment_story, type, ranking" +
                                                ") VALUES (" +
                                                "?, ?, ?, ?, ?, ?" +
                                                ")", keyspace, columnFamilyName);
        Date rowDate = new Date(0);
        File outputDir = new File(savePath + File.separator + keyspace + File.separator + columnFamilyName);
        if (!outputDir.exists() && !outputDir.mkdirs())
        {
            throw new RuntimeException("Cannot create output directory: " + outputDir);
        }
        File file = new File(filePath);

        //Sets up the CQL table writer
        CQLSSTableWriter.Builder builder = CQLSSTableWriter.builder();
        //System.out.print(2);
        builder.inDirectory(outputDir)
                .forTable(tblSchema)
                .using(tblInsert)
                .withBufferSizeInMB(64)
                .withPartitioner(new Murmur3Partitioner());
        CQLSSTableWriter writer = builder.build();

        //Sets up CSV Reader
        CSVParserBuilder parserBuilder = new CSVParserBuilder();
        parserBuilder.withEscapeChar('\0');
        CSVParser parser = parserBuilder.build();

        FileReader filereader = new FileReader(file);//gets the CSV file of interest as a Java IO file
        CSVReaderBuilder csvBuild = new CSVReaderBuilder(filereader);//Using the built in csv buiilder rather than constructing directly
        csvBuild.withSkipLines(1)
                .withCSVParser(parser);//THe only option I want set, to skip the header line
        CSVReader csvReader = csvBuild.build();//Instatiates the csv reader object

        String[] nextRecord;

        int count = 0;
        while ((nextRecord = csvReader.readNext()) != null) {
            rowDate.setTime(NumberUtils.toLong(nextRecord[2], 0)*1000);
            writer.addRow(
                    NumberUtils.toInt(nextRecord[12], NULL),
                    nextRecord[0],
                    rowDate,
                    nextRecord[7],
                    nextRecord[5],
                    NumberUtils.toInt(nextRecord[1], NULL)
            );
            System.out.print(count + "\n");
            count++;
        }
        writer.close();
    }
}
