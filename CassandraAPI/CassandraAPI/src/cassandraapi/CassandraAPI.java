/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cassandraapi;
//import org.apache.cassandra.io.sstable.SSTable;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.IOException;
import javax.swing.JInternalFrame;
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
        String filePath = "C:\\Storage\\Dropbox\\Andrew Mit\\Courses\\2018 Term 03\\563.684 Big Data\\Project\\Demo\\Full-Test.csv";
        String savePath = "C:\\Storage\\Dropbox\\Andrew Mit\\Courses\\2018 Term 03\\563.684 Big Data\\Project\\Demo\\";
        String keyspace = "hacker_news";        
        
        //BulkLoader.storiesByAuthorTable(filePath, savePath, keyspace);
        //BulkLoader.storiesByRankTable(filePath, savePath, keyspace);
        //BulkLoader.comments_stories_Table(filePath, savePath, keyspace);
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
//        Cluster cluster = null;
//        try {
//            cluster = Cluster.builder()           
//                    .addContactPoint("127.0.0.1")
//                    .build();
//            Session session = cluster.connect();
//            
//            MainFrame mainFrame = new MainFrame();
//            mainFrame.setVisible(true);   
//            
//            //System.out.println(row.getString("release_version"));        
//        } finally {
//            if (cluster != null) cluster.close();                                    
//        }
        
        
    }
}