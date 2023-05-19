package ru.sanctio.jakarta_laba_hib.repository;

import jakarta.ejb.Local;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;


import java.util.List;

@Local
public interface DbManagerLocal {
    boolean checkUser(String login, String password);
    List<AddressEntity> getAllInformation();
    List<AddressEntity> getFilteredInformation(String filterName, String filterType);
    List<AddressEntity> getFilteredByNameAndAddressInformation(String filterName);
    List<AddressEntity> getFilteredByTypeInformation(String filterType);

    boolean addClientAddress(AddressEntity newAddress, String clientId);

    void deleteAddress(String addressId, String clientId);

    boolean createNewClient(ClientEntity newClient, AddressEntity newAddress);

    boolean update(ClientEntity client, AddressEntity addressEntity, String clientId);

    AddressEntity getAddressById(String addressId);
}
