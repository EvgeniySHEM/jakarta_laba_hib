package ru.sanctio.jakarta_laba_hib.beans;

import jakarta.ejb.Local;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

@Local
public interface UpdateBeanLocal {
//    void createNewClient(String clientName, String selectType, String date, String ip, String mac,
//                         String model, String address);

    boolean createNewClient(ClientEntity newClient, AddressEntity newAddress);

    boolean addClientAddress(AddressEntity newAddress, String clientId);

    void delete(String addressId, String clientId);
}
