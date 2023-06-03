package ru.sanctio.jakarta_laba_hib.xml_parsers;

import jakarta.ejb.Local;

@Local
public interface TransformerLocal {
    void createXml(String xmlFile);
}
