/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferfilev1.servers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import thrift.services.FileInfo;
import thrift.services.FileUpload;

/**
 *
 * @author cpu11418
 */
public class FileUploadServer implements FileUpload.Iface {
 
    @Override
    public long uploadFile(FileInfo info) throws TException {
        final File BASE_DIRECTORY = new File("storage");
        
        if (!BASE_DIRECTORY.exists()) {
            BASE_DIRECTORY.mkdir();
        }
        
        String pathStorage = BASE_DIRECTORY.getAbsolutePath();
        String realFileName = info.fileName.substring(info.fileName.lastIndexOf('/'));
        
        long byteWritten = -1;
        System.out.println("File: " + realFileName+ " is uploading");

        // Convert bytebuffer to byte[]
        byte[] data = new byte[info.content.remaining()];
        info.content.get(data, 0, data.length);

        try (BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(pathStorage + realFileName, true))) {
            out.write(data);
            byteWritten = data.length;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUploadServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUploadServer.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        System.out.println("File: " + realFileName + " uploaded");
        return byteWritten;
    }
     

}
