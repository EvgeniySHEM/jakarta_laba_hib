package ru.sanctio.jakarta_laba_hib.beans;

import jakarta.ejb.Local;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;

import java.util.List;

@Local
public interface SelectBeanLocal {

    List<AddressEntity> getData(String filterName, String filterType);
    List<AddressEntity> getSortedData(String filterName, String filterType);

}
