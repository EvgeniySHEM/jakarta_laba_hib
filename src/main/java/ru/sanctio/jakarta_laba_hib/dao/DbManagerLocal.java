package ru.sanctio.jakarta_laba_hib.dao;

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

    boolean createNewClient(ClientEntity newClient, AddressEntity newAddress);

    void createNewClient(ClientEntity client);

    boolean updateClient(ClientEntity client, AddressEntity addressEntity);
    void updateAddress(AddressEntity address);
    void updateClient(ClientEntity client);

    AddressEntity getAddressById(String addressId);

    List<ClientEntity> getAllClient();

    ClientEntity getClientById(Integer id);

    void deleteClient(String id);
    void deleteAddress(String addressId);
}
