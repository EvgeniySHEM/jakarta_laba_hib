package ru.sanctio.jakarta_laba_hib.beans;

import jakarta.ejb.Local;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

@Local
public interface UpdateBeanLocal {

    boolean createNewClient(ClientEntity newClient, AddressEntity newAddress);

    boolean addClientAddress(AddressEntity newAddress, String clientId);

    void delete(String addressId);

    boolean update(ClientEntity client, AddressEntity addressEntity);

    AddressEntity selectAddress(String addressId);
}
