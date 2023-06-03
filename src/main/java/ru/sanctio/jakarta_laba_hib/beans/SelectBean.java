package ru.sanctio.jakarta_laba_hib.beans;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpServletResponse;
import org.xml.sax.SAXException;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.dao.DbManagerLocal;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;
import ru.sanctio.jakarta_laba_hib.xml_parsers.DemoDOMLocal;
import ru.sanctio.jakarta_laba_hib.xml_parsers.DemoSAX;
import ru.sanctio.jakarta_laba_hib.xml_parsers.TransformerLocal;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class SelectBean implements SelectBeanLocal {
    @EJB
    private DbManagerLocal dbManager;

    @EJB
    private TransformerLocal transformer;

    @EJB
    private DemoDOMLocal demoDOM;

//    @EJB
//    private DemoSAX demoSAX;

    @Override
    public List<AddressEntity> getData(String filterName, String filterType) {
        if ((filterName != null && !filterName.isEmpty()) && (filterType != null && !filterType.isEmpty())) {
            return dbManager.getFilteredInformation(filterName, filterType);
        } else if (filterName != null && !filterName.isEmpty()) {
            return dbManager.getFilteredByNameAndAddressInformation(filterName);
        } else if (filterType != null && !filterType.isEmpty()) {
            return dbManager.getFilteredByTypeInformation(filterType);
        } else {
            return dbManager.getAllInformation();
        }
    }

    @Override
    public List<AddressEntity> getSortedData(String filterName, String filterType) {
        List<AddressEntity> list = getData(filterName, filterType);
        list.sort((a, b) -> a.getClient().getClientId() - b.getClient().getClientId());
        return list;
    }

    @Override
    public List<ClientEntity> getAllClient() {
        return dbManager.getAllClient();
    }

    @Override
    public List<ClientEntity> readXML(String xmlFile, String filterXML) {
        transformer.createXml(xmlFile);
        return demoDOM.readFile(xmlFile, filterXML);
    }

    @Override
    public List<ClientEntity> readXMLSAX(String xmlFile, String filterXML) {
        File file = new File("/Users/evgeniysharychenkov/IdeaProjects/jakarta_laba_hib/" + xmlFile);
        transformer.createXml(xmlFile);
        List<ClientEntity> clients = null;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            DemoSAX sax = new DemoSAX();
            sax.setFilter(filterXML);
            parser.parse(file, sax);
            clients = sax.getClients();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public void checkListSize(List<ClientEntity> clients, HttpServletResponse response) throws IOException {
        if(clients.size() == 0) {
            response.sendError(499, "Объекты, удовлетворяющие условиям поиска, отсутствуют");
        }
    }

    public List<AddressEntity> getAllInformation() {
        return dbManager.getAllInformation();
    }

    @Override
    public Map<Integer, ClientEntity> getAllClientWithAddress() {
        Map<Integer, ClientEntity> clients = new HashMap<>();
        dbManager.getAllInformation().forEach(address -> {
            ClientEntity client = new ClientEntity(
                    address.getClient().getClientId(),
                    address.getClient().getClientName(),
                    address.getClient().getType(),
                    address.getClient().getAdded().toString());
            clients.putIfAbsent(client.getClientId(), client);
            AddressEntity addressEntity = new AddressEntity(
                    address.getId(),
                    address.getIp(),
                    address.getMac(),
                    address.getModel(),
                    address.getAddress());
            clients.get(address.getClient().getClientId()).addAddress(addressEntity);
        });
        return new HashMap<>(clients);
    }
}
