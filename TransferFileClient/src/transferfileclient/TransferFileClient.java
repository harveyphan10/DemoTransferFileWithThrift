/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferfileclient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import thrift.services.FileInfo;
import thrift.services.FileUpload;

/**
 *
 * @author cpu11418
 */
public class TransferFileClient {

    private static final int BUFFER_SIZE = 7024000;
    private static final int uploadPort = Integer.getInteger("port.upload", 10400);
    private static final String address = System.getProperty("address", "localhost");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            uploadFile("data/abc.pdf", new TFramedTransport(new TSocket(address, uploadPort)));
            uploadFile("data/Designing Data-Intensive Applications.pdf", new TFramedTransport(new TSocket(address, uploadPort)));
            uploadFile("data/abc.pdf", new TFramedTransport(new TSocket(address, uploadPort)));

        } catch (TTransportException | IOException ex) {
            Logger.getLogger(TransferFileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void uploadFile(String fileName, TTransport socket) throws FileNotFoundException, TTransportException, IOException {

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("File not found " + file.getAbsolutePath());
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.fileName = fileName;
        fileInfo.length = file.length();

        socket.open();
        TProtocol protocol = new TBinaryProtocol(socket);
        FileUpload.Client client = new FileUpload.Client(protocol);
        byte[] data = new byte[BUFFER_SIZE];
        int byteRead = 0;

        try (BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(file))) {
            while ((byteRead = buffIn.read(data)) != -1) {
                fileInfo.content = ByteBuffer.wrap(data);
                client.uploadFile(fileInfo);
            }
        } catch (TException ex) {
            Logger.getLogger(TransferFileClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Success to upload " + fileInfo.fileName);

    }
}
