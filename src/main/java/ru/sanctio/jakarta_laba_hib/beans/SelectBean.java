package ru.sanctio.jakarta_laba_hib.beans;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.repository.DbManagerLocal;

import java.util.List;

@Stateless
public class SelectBean implements SelectBeanLocal {
    @EJB
    private DbManagerLocal dbManager;

    @Override
    public List<AddressEntity> getData(String filterName, String filterType) {
        if ((filterName != null && !filterName.isEmpty()) && (filterType != null && !filterType.isEmpty())) {
            return dbManager.getFilteredInformation(filterName, filterType);
        } else if (filterName != null && !filterName.isEmpty()) {
            return dbManager.getFilteredByNameInformation(filterName);
        } else if (filterType != null && !filterType.isEmpty()) {
            return dbManager.getFilteredByTypeInformation(filterType);
        } else {
            return dbManager.getAllInformation();
        }
    }
}
