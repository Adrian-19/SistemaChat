/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SistemaChat.data;
import SistemaChat.logic.User;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
/**
 *
 * @author adria
 */
public class XmlPersister {
    private String path;
    private static XmlPersister instance;
    public static XmlPersister getInstance()
    {
        if(instance == null)
        {
            instance = new XmlPersister("persister.xml");
        }
        return instance;
    }
    public XmlPersister(String p)
    {
        path = p;
    }
    public void setPath(String p)
    {
        path = p;
    }
    public String getPath()
    {
        return path;
    }
    public User load() throws Exception // por que mandara exception?? 
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        FileInputStream is = new FileInputStream(path);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        User result = (User) unmarshaller.unmarshal(is);
        is.close();
        return result;
    }
    
    public void store(User d) throws Exception
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        FileOutputStream os = new FileOutputStream(path);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(d, os);
        os.flush();
        os.close();
    }
}
