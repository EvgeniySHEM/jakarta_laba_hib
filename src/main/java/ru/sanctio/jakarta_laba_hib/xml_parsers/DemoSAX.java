package ru.sanctio.jakarta_laba_hib.xml_parsers;

import jakarta.ejb.Singleton;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import java.util.*;

public class DemoSAX extends DefaultHandler {
    private String filter;

    private final Map<Integer, ClientEntity> clients = new HashMap<>();

    public List<ClientEntity> getClients() {
        return new ArrayList<>(clients.values());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("client")) {
            if(attributes.getValue("clientName").contains(filter)) {
                ClientEntity client = new ClientEntity(
                        Integer.parseInt(attributes.getValue("clientid")),
                        attributes.getValue("clientName"),
                        attributes.getValue("type"),
                        attributes.getValue("added"));
                clients.putIfAbsent(client.getClientId(), client);
            }
        }
        if (qName.equals("address")) {
            if(clients.get(Integer.parseInt(attributes.getValue("clientId"))) != null) {
                AddressEntity address = new AddressEntity(
                        Integer.parseInt(attributes.getValue("id")),
                        attributes.getValue("ip"),
                        attributes.getValue("mac"),
                        attributes.getValue("model"),
                        attributes.getValue("address"));
                clients.get(Integer.parseInt(attributes.getValue("clientId"))).addAddress(address);
            }
        }
    }

    public void setFilter(String filterXML) {
        filter = filterXML;
    }
}
