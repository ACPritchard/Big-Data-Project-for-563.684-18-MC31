/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandraapi;
//import org.apache.cassandra.io.sstable.SSTable;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.sql.Types.NULL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cassandra.config.Config;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.apache.commons.lang3.math.NumberUtils;
/**
 *
 * @author Andrew
 */
public class CassandraAPI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String filePath = "F:\\ProjectData\\Complete\\Full-Complete.csv";
        String savePath = "F:\\ProjectData\\Tables\\";
        String keyspace = "dummy_db";
        String columnFamilyName = "test_tbl";
        Date rowDate = new Date(0);

        String TestTblSchema = String.format("CREATE TABLE %s.%s (" +
                                                "id int, " +
                                                "byUser text, " +
                                                "score int, " +
                                                "date timestamp, " +
                                                "title text, " +
                                                "type text, " +
                                                "url text, " +
                                                "body text, " +
                                                "parent int, " +
                                                "deleted boolean, " +
                                                "dead boolean, " +
                                                "descendants int, " +
                                                "ranking int, " +
                                                "PRIMARY KEY (id, date)" +
                                                ") WITH CLUSTERING ORDER BY (date DESC)", keyspace, columnFamilyName);

        String TestTblInsert = String.format("INSERT INTO %s.%s (" +
                                                "id, byUser, score, date, title, type, url, body, parent, deleted, dead, descendants, ranking" +
                                                ") VALUES (" +
                                                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                                                ")", keyspace, columnFamilyName);

        File outputDir = new File(savePath + File.separator + keyspace + File.separator + columnFamilyName);
        if (!outputDir.exists() && !outputDir.mkdirs())
        {
            throw new RuntimeException("Cannot create output directory: " + outputDir);
        }
        //System.out.print(1);
        File file = new File(filePath);
//        try {
//            FileReader filereader = new FileReader(file);
//            CSVReader csvReader = new CSVReader(filereader);
//            String[] nextRecord;
//            for (int i = 0; i < 10; i++) {
//                nextRecord = csvReader.readNext();
//                for (String cell : nextRecord) {
//                    System.out.print(cell + "\t");
//            }
//            System.out.println();
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(CassandraAPI.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //Config.setClientMode(true);
        //Sets up the CQL table writer
        CQLSSTableWriter.Builder builder = CQLSSTableWriter.builder();
        //System.out.print(2);
        builder.inDirectory(outputDir)
                .forTable(TestTblSchema)
                .using(TestTblInsert)
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

//        for (int i = 0; i < 800000; i++) {
//            nextRecord = csvReader.readNext();
//            rowDate.setTime(NumberUtils.toLong(nextRecord[2], 0)*1000);
//            writer.addRow(
//                    NumberUtils.toInt(nextRecord[12], NULL),
//                    nextRecord[0],
//                    NumberUtils.toInt(nextRecord[1], NULL),
//                    rowDate,
//                    nextRecord[4],
//                    nextRecord[5],
//                    nextRecord[6],
//                    nextRecord[7],
//                    NumberUtils.toInt(nextRecord[8], NULL),
//                    Boolean.parseBoolean(nextRecord[9]),
//                    Boolean.parseBoolean(nextRecord[10]),
//                    NumberUtils.toInt(nextRecord[11], NULL),
//                    NumberUtils.toInt(nextRecord[13], NULL)
//            );
//            System.out.print(i + "\n");
//        }

        int count = 0;
        while ((nextRecord = csvReader.readNext()) != null) {
            rowDate.setTime(NumberUtils.toLong(nextRecord[2], 0)*1000);
            writer.addRow(
                    NumberUtils.toInt(nextRecord[12], NULL),
                    nextRecord[0],
                    NumberUtils.toInt(nextRecord[1], NULL),
                    rowDate,
                    nextRecord[4],
                    nextRecord[5],
                    nextRecord[6],
                    nextRecord[7],
                    NumberUtils.toInt(nextRecord[8], NULL),
                    Boolean.parseBoolean(nextRecord[9]),
                    Boolean.parseBoolean(nextRecord[10]),
                    NumberUtils.toInt(nextRecord[11], NULL),
                    NumberUtils.toInt(nextRecord[13], NULL)
            );
            System.out.print(count + "\n");
            count++;
        }
        writer.close();
        //File tableLocation = new File("F:\\ProjectData\\Tables\\");
//        if (!tableLocation.exists()) {
//            tableLocation.mkdir();
//        }

    }
}
