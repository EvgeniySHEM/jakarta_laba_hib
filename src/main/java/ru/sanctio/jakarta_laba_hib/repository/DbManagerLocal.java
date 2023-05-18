package ru.sanctio.jakarta_laba_hib.repository;

import jakarta.ejb.Local;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;


import java.util.List;

@Local
public interface DbManagerLocal {
    boolean checkUser(String login, String password);
    List<ClientEntity> getAllClient();

    List<AddressEntity> getAllInformation();
    List<AddressEntity> getFilteredInformation(String filterName, String filterType);
    List<AddressEntity> getFilteredByNameInformation(String filterName);
    List<AddressEntity> getFilteredByTypeInformation(String filterType);


//    ClientEntity createClientEntity(ClientEntity client);

//    AddressEntity createAddressEntity(AddressEntity address);

//    void updateAddress(AddressEntity newAddress);
//    void updateClient(ClientEntity newClient);

    boolean addClientAddress(AddressEntity newAddress, String clientId);

    void deleteAddress(String addressId, String clientId);

    boolean createNewClient(ClientEntity newClient, AddressEntity newAddress);
}
