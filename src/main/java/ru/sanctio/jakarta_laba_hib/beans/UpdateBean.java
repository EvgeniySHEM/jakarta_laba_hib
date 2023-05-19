package ru.sanctio.jakarta_laba_hib.beans;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;
import ru.sanctio.jakarta_laba_hib.repository.DbManagerLocal;

@Stateless
public class UpdateBean implements UpdateBeanLocal{
    @EJB
    private DbManagerLocal repository;

    @Override
    public boolean createNewClient(ClientEntity newClient, AddressEntity newAddress) {
        return repository.createNewClient(newClient, newAddress);
    }

    @Override
    public boolean addClientAddress(AddressEntity newAddress, String clientId) {
        return repository.addClientAddress(newAddress, clientId);
    }

    @Override
    public void delete(String addressId, String clientId) {
        repository.deleteAddress(addressId, clientId);
    }

    @Override
    public boolean update(ClientEntity client, AddressEntity addressEntity, String clientId) {
        return repository.update(client, addressEntity, clientId);
    }

    @Override
    public AddressEntity selectAddress(String addressId) {
        return repository.getAddressById(addressId);
    }
}
