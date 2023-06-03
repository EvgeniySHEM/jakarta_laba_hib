package ru.sanctio.jakarta_laba_hib.beans;

import jakarta.ejb.Local;
import jakarta.servlet.http.HttpServletResponse;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Local
public interface SelectBeanLocal {

    List<AddressEntity> getData(String filterName, String filterType);
    List<AddressEntity> getSortedData(String filterName, String filterType);

    List<ClientEntity> getAllClient();

    List<ClientEntity> readXML(String xmlFile, String filterXML);

    List<AddressEntity> getAllInformation();

    Map<Integer, ClientEntity> getAllClientWithAddress();

    List<ClientEntity> readXMLSAX(String xmlFile, String filterXML);

    void checkListSize(List<ClientEntity> clients, HttpServletResponse response) throws IOException;
}
