package ru.sanctio.jakarta_laba_hib.xml_parsers;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.sanctio.jakarta_laba_hib.beans.SelectBeanLocal;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;
import java.util.Objects;

@Singleton
public class Transformer implements TransformerLocal {

    @EJB
    private SelectBeanLocal selectBean;

    public boolean createXml(String xmlFile) {
        File file = new File("/Users/evgeniysharychenkov/IdeaProjects/jakarta_laba_hib/" + xmlFile);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement("clients");
            document.appendChild(rootElement);
            selectBean.getAllClientWithAddress().values().forEach(client -> {
                Element clientElement = document.createElement("client");
                rootElement.appendChild(clientElement);
                clientElement.setAttribute("clientid", Objects.toString(client.getClientId(), ""));
                clientElement.setAttribute("clientName", Objects.toString(client.getClientName(), ""));
                clientElement.setAttribute("type", Objects.toString(client.getType(), ""));
                clientElement.setAttribute("added", Objects.toString(client.getAdded(), ""));
                List<AddressEntity> addresses = client.getAddresses();
                if (addresses != null && !addresses.isEmpty()) {
                    addresses.forEach(address -> {
                        Element addrElement = document.createElement("address");
                        clientElement.appendChild(addrElement);
                        addrElement.setAttribute("id", Objects.toString(address.getId(), ""));
                        addrElement.setAttribute("ip", Objects.toString(address.getIp(), ""));
                        addrElement.setAttribute("mac", Objects.toString(address.getMac(), ""));
                        addrElement.setAttribute("model", Objects.toString(address.getModel(), ""));
                        addrElement.setAttribute("address", Objects.toString(address.getAddress(), ""));
                        addrElement.setAttribute("clientId", Objects.toString(address.getClient().getClientId(), ""));
                    });
                }

            });

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return false;
    }
}
