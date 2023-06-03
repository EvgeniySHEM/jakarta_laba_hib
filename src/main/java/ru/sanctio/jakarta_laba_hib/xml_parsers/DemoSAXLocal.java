package ru.sanctio.jakarta_laba_hib.xml_parsers;

import jakarta.ejb.Local;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import java.util.List;

@Local
public interface DemoSAXLocal {
    List<ClientEntity> getClients();
}
