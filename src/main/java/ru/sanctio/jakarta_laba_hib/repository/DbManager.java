package ru.sanctio.jakarta_laba_hib.repository;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;
import ru.sanctio.jakarta_laba_hib.entity.UsersEntity;

import java.util.List;

@Singleton
public class DbManager implements DbManagerLocal {
    //    @PersistenceContext
//    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Override
    public boolean checkUser(String login, String password) {
        openEntityManager();
        Query query = entityManager.createNativeQuery("select * from users " +
                "where login = '" + login + "' and password = '" + password + "'");
        List<UsersEntity> user = query.getResultList();
        closeEntityManager();
        return user.size() > 0;
    }

    @Override
    public List<ClientEntity> getAllClient() {
        openEntityManager();
        Query query = entityManager.createNativeQuery("select * from client");
        List<ClientEntity> clientsList = query.getResultList();
        closeEntityManager();
        return clientsList;
    }

    @Override
    public List<AddressEntity> getAllInformation() {
        openEntityManager();
        Query query = entityManager.createNativeQuery("select * from address", AddressEntity.class);
        List<AddressEntity> addressList = query.getResultList();
        System.out.println(addressList);
        closeEntityManager();
        return addressList;
    }

    @Override
    public List<AddressEntity> getFilteredInformation(String filterName, String filterType) {
        openEntityManager();
        String sql = "select a.* from client c join address a on c.clientId = a.clientid\n" +
                "           where c.clientName like '%" + filterName + "%' and c.type = '" + filterType + "'";
        List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
        System.out.println(addressList);
        closeEntityManager();
        return addressList;
    }

    @Override
    public List<AddressEntity> getFilteredByNameInformation(String filterName) {
        openEntityManager();
        String sql = "select a.* from client c join address a on c.clientId = a.clientid\n" +
                "where c.clientName like '%" + filterName + "%'";
        List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
        System.out.println(addressList);
        closeEntityManager();
        return addressList;
    }

    @Override
    public List<AddressEntity> getFilteredByTypeInformation(String filterType) {
        openEntityManager();
        String sql = "select a.* from client c join address a on c.clientId = a.clientid\n" +
                "           where c.type = '" + filterType + "'";
        List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
        System.out.println(addressList);
        closeEntityManager();
        return addressList;
    }

    @Override
    public boolean createNewClient(ClientEntity newClient, AddressEntity newAddress) {
        openEntityManager();
        newClient = createClientEntity(newClient);
        newAddress.setClient(newClient);
        boolean result = createAddressEntity(newAddress);
        closeEntityManager();
        return result;
    }

    private ClientEntity createClientEntity(ClientEntity client) {
        String sql = "select * from client where clientName = '" + client.getClientName() + "' and" +
                " type = '" + client.getType() + "' and added = '" + client.getAdded() + "'";
        Query nativeQuery = entityManager.createNativeQuery(sql, ClientEntity.class);
        List<ClientEntity> clientList = nativeQuery.getResultList();
        if (clientList.size() > 0) {
            client = clientList.get(0);
        } else {
            client = entityManager.merge(client);
        }
        return client;
    }

    private boolean createAddressEntity(AddressEntity address) {
        String sql = "select * from address where ip = '" + address.getIp() + "' and " +
                "mac = '" + address.getMac() + "' and model = '" + address.getModel() + "' and" +
                " address = '" + address.getAddress() + "'";
        List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
        if (addressList.size() > 0) {
            entityManager.getTransaction().rollback();
            return false;
        } else {
            entityManager.persist(address);
            return true;
        }
    }

    @Override
    public boolean addClientAddress(AddressEntity newAddress, String clientId) {
        openEntityManager();
        String sql = "select * from address where ip = '" + newAddress.getIp() + "' and " +
                "mac = '" + newAddress.getMac() + "' and model = '" + newAddress.getModel() + "' and" +
                " address = '" + newAddress.getAddress() + "'";
        List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
        if (addressList.size() == 0) {
            String insert = "insert into address(ip,mac,model,address,clientid) values ('"
                    + newAddress.getIp() + "','" + newAddress.getMac() + "','"
                    + newAddress.getModel() + "','" + newAddress.getAddress() + "'," + clientId + ")";
            entityManager.createNativeQuery(insert).executeUpdate();
            closeEntityManager();
            return true;
        } else {
            closeEntityManager();
            return false;
        }
    }

    @Override
    public void deleteAddress(String addressId, String clientId) {
        openEntityManager();
        String deleteAddress = "delete from address where id = " + addressId;
        String deleteClient = "delete from client where clientid = " + clientId;
        entityManager.createNativeQuery(deleteAddress).executeUpdate();
        String checkFK = "select * from address where clientId =" + clientId;
        List<AddressEntity> addressList = entityManager.createNativeQuery(checkFK, AddressEntity.class).getResultList();
        if (addressList.size() == 0) {
            entityManager.createNativeQuery(deleteClient).executeUpdate();
        }
        closeEntityManager();
    }

//    @Override
//    public void updateAddress(AddressEntity newAddress) {
//        openEntityManager();
//        entityManager.merge(newAddress);
//        entityManager.flush();
//        closeEntityManager();
//    }
//
//    @Override
//    public void updateClient(ClientEntity newClient) {
//        openEntityManager();
//        entityManager.merge(newClient);
//        entityManager.flush();
//        closeEntityManager();
//    }


    private void openEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

    }

    private void closeEntityManager() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        entityManagerFactory.close();
    }

}
