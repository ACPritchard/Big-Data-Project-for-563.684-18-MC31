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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cassandra.config.Config;
import org.apache.cassandra.dht.Murmur3Partitioner;
import org.apache.cassandra.io.sstable.CQLSSTableWriter;
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
        String filePath = "F:\\ProjectData\\comments_test_rows-0-100000.csv";
        String savePath = "F:\\ProjectData\\Tables\\";
        String keyspace = "dummy_db";
        String columnFamilyName = "test_tbl";
        
        String TestTblSchema = String.format("CREATE TABLE %s.%s (" +
                                                "id int, " +
                                                "byUser text, " +
                                                "author text, " +
                                                "isDead boolean, " +
                                                "PRIMARY KEY (id, author)" +
                                                ") WITH CLUSTERING ORDER BY (author DESC)", keyspace, columnFamilyName);
        
        String TestTblInsert = String.format("INSERT INTO %s.%s (" +
                                                "id, byUser, author, isDead" +
                                                ") VALUES (" +
                                                "?, ?, ?, ?" +
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
        
//        for (int i = 0; i < 100; i++) {
//            nextRecord = csvReader.readNext();
//            writer.addRow(
//                    Integer.parseInt(nextRecord[0]),
//                    nextRecord[1],
//                    nextRecord[2],
//                    Boolean.parseBoolean(nextRecord[8])
//            );
//            System.out.print(i + "\n");
//        }
        int count = 0;
        while ((nextRecord = csvReader.readNext()) != null) {
            writer.addRow(                    
                    Integer.parseInt(nextRecord[0]),
                    nextRecord[1],
                    nextRecord[2],
                    Boolean.parseBoolean(nextRecord[8])
            );
            System.out.print(count + "\t" + nextRecord[0] + "\n");
            count++;
        }
        writer.close();
        //File tableLocation = new File("F:\\ProjectData\\Tables\\");
//        if (!tableLocation.exists()) {
//            tableLocation.mkdir();
//        }
        
    }
}
