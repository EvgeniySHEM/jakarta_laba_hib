package ru.sanctio.jakarta_laba_hib.xml_parsers;

import jakarta.ejb.Local;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import java.io.File;
import java.util.List;

@Local
public interface DemoDOMLocal {
    List<ClientEntity> readFile(String xmlFile, String filterXML);
}
