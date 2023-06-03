package ru.sanctio.jakarta_laba_hib.xml_parsers;

import jakarta.ejb.Singleton;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class DemoDOM implements DemoDOMLocal{

    public List<ClientEntity> readFile(String xmlFile, String filterXML){
        File file = new File("/Users/evgeniysharychenkov/IdeaProjects/jakarta_laba_hib/" + xmlFile);
        List<ClientEntity> clients = new LinkedList<>();
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element rootElement = document.getDocumentElement();
            NodeList nodeList = rootElement.getChildNodes();
            if(rootElement.getNodeName().equals("clients")) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node nodeClient = nodeList.item(i);
                    if (nodeClient.getNodeType() == Node.ELEMENT_NODE && nodeClient.getNodeName().equals("client")) {
                        Element clientElement = (Element) nodeClient;
                        if(clientElement.getAttribute("clientName").contains(filterXML)) {
                            ClientEntity client = new ClientEntity(
                                    Integer.parseInt(clientElement.getAttribute("clientid")),
                                    clientElement.getAttribute("clientName"),
                                    clientElement.getAttribute("type"),
                                    clientElement.getAttribute("added"));
                            clients.add(client);
                            NodeList nodeListAddress = nodeClient.getChildNodes();
                            for (int j = 0; j < nodeListAddress.getLength(); j++) {
                                Node nodeAddress = nodeListAddress.item(j);
                                if (nodeAddress.getNodeType() == Node.ELEMENT_NODE && nodeAddress.getNodeName().equals("address")) {
                                    Element addressElement = (Element) nodeAddress;
                                    AddressEntity address = new AddressEntity(
                                            Integer.parseInt(addressElement.getAttribute("id")),
                                            addressElement.getAttribute("ip"),
                                            addressElement.getAttribute("mac"),
                                            addressElement.getAttribute("model"),
                                            addressElement.getAttribute("address"));
                                    client.addAddress(address);
                                }
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
