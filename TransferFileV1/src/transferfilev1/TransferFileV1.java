/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferfilev1;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import thrift.services.FileUpload;
import transferfilev1.servers.FileUploadServer;

/**
 *
 * @author cpu11418
 */
public class TransferFileV1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int uploadPort = Integer.getInteger("upload", 10400);
        
        new Thread(() -> {
            try {
                TNonblockingServerSocket transport = new TNonblockingServerSocket(uploadPort);
                TNonblockingServer.Args serverAgs = new TNonblockingServer.Args(transport);
                
                FileUploadServer fileUploadServer = new FileUploadServer();
                FileUpload.Processor<FileUpload.Iface> processor = new FileUpload.Processor<>(fileUploadServer);
                
                serverAgs.processor(processor);
                TNonblockingServer simpleServer = new TNonblockingServer((serverAgs));
                
                System.out.println("Start upload service.");
                simpleServer.serve();
                
            } catch (TTransportException ex) {
                Logger.getLogger(TransferFileV1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }).start();
    }
    
}
