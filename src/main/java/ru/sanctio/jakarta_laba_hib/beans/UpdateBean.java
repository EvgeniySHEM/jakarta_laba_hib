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

//    @Override
//    public void createNewClient(String clientName, String selectType, String date,
//                                String ip, String mac, String model, String address)
//                                throws IllegalArgumentException, NullPointerException {
//        ClientEntity newClient = new ClientEntity();
//        AddressEntity newAddress = new AddressEntity();
//        newClient.setClientName(clientName);
//        newClient.setType(selectType);
//        newClient.setAdded(date);
//        newAddress.setIp(ip);
//        newAddress.setMac(mac);
//        newAddress.setModel(model);
//        newAddress.setAddress(address);
//        newClient.addAddress(newAddress);
//
//        newClient = repository.createClientEntity(newClient);
//
//        newAddress.setClient(newClient);
//        repository.createAddressEntity(newAddress);
//    }

    @Override
    public boolean createNewClient(ClientEntity newClient, AddressEntity newAddress) {
        return repository.createNewClient(newClient, newAddress);
//        newClient = repository.createClientEntity(newClient);
//        newAddress.setClient(newClient);
//        repository.createAddressEntity(newAddress);
    }

    @Override
    public boolean addClientAddress(AddressEntity newAddress, String clientId) {
        return repository.addClientAddress(newAddress, clientId);
    }

    @Override
    public void delete(String addressId, String clientId) {
        repository.deleteAddress(addressId, clientId);
    }
}
