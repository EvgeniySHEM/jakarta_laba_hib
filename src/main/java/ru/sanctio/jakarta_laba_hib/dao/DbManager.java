package ru.sanctio.jakarta_laba_hib.dao;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;
import ru.sanctio.jakarta_laba_hib.entity.UsersEntity;

import java.util.List;

@Singleton
public class DbManager implements DbManagerLocal {
    //    @PersistenceContext
//    private EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private EntityManager entityManager;

    @Override
    public boolean checkUser(String login, String password) {
        openEntityManager();
        try {
            Query query = entityManager.createNativeQuery("select * from users " +
                    "where login = '" + login + "' and password = '" + password + "'");
            List<UsersEntity> user = query.getResultList();
            return user.size() > 0;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<AddressEntity> getAllInformation() {
        openEntityManager();
        try {
            return entityManager.createNativeQuery("select * from address", AddressEntity.class).getResultList();
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<AddressEntity> getFilteredInformation(String filterName, String filterType) {
        openEntityManager();
        try {
            String sql = "select a.* from client c join address a on c.clientId = a.clientid\n" +
                    "           where (c.clientName like '%" + filterName + "%' or a.address like '%" + filterName + "%')" +
                    "and c.type = '" + filterType + "'";
            List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
//            System.out.println(addressList);
            Hibernate.initialize(addressList);
            return addressList;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<AddressEntity> getFilteredByNameAndAddressInformation(String filterName) {
        openEntityManager();
        try {
            String sql = "select a.* from client c join address a on c.clientId = a.clientid\n" +
                    "where c.clientName like '%" + filterName + "%' or a.address like '%" + filterName + "%'";
            List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
            Hibernate.initialize(addressList);
            return addressList;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<AddressEntity> getFilteredByTypeInformation(String filterType) {
        openEntityManager();
        try {
            String sql = "select a.* from client c join address a on c.clientId = a.clientid\n" +
                    "           where c.type = '" + filterType + "'";
            List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
            Hibernate.initialize(addressList);
            return addressList;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public boolean createNewClient(ClientEntity newClient, AddressEntity newAddress) {
        openEntityManager();
        try {
            newClient = createClientEntity(newClient);
            newAddress.setClient(newClient);
            return createAddressEntity(newAddress);
        } finally {
            closeEntityManager();
        }
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
    public void createNewClient(ClientEntity client) {
        openEntityManager();
        try {
            String sql = "select * from client where clientName = '" + client.getClientName() + "' and" +
                    " type = '" + client.getType() + "'";
            Query nativeQuery = entityManager.createNativeQuery(sql, ClientEntity.class);
            List<ClientEntity> clientList = nativeQuery.getResultList();
            if (clientList.size() == 0) {
                entityManager.persist(client);
            }
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public boolean updateClient(ClientEntity client, AddressEntity addressEntity) {
        openEntityManager();
        try {
            entityManager.merge(client);
            entityManager.merge(addressEntity);
            return true;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public void updateAddress(AddressEntity address) {
        openEntityManager();
        try {
            entityManager.merge(address);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public void updateClient(ClientEntity client) {
        openEntityManager();
        try {
            entityManager.merge(client);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public AddressEntity getAddressById(String addressId) {
        openEntityManager();
        try {
            AddressEntity addressEntity = entityManager.find(AddressEntity.class, addressId);
            return addressEntity;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<ClientEntity> getAllClient() {
        openEntityManager();
        try {
            List<ClientEntity> clients =
                    entityManager.createNativeQuery("select * from client", ClientEntity.class).getResultList();
            Hibernate.initialize(clients);
            return clients;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public ClientEntity getClientById(Integer id) {
        openEntityManager();
        try {
            ClientEntity client = entityManager.find(ClientEntity.class, id);
            Hibernate.initialize(client);
            return client;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public boolean addClientAddress(AddressEntity newAddress, String clientId) {
        openEntityManager();
        try {
            String sql = "select * from address where ip = '" + newAddress.getIp() + "' and " +
                    "mac = '" + newAddress.getMac() + "' and model = '" + newAddress.getModel() + "' and" +
                    " address = '" + newAddress.getAddress() + "'";
            List<AddressEntity> addressList = entityManager.createNativeQuery(sql, AddressEntity.class).getResultList();
            if (addressList.size() == 0) {
                ClientEntity client = entityManager.find(ClientEntity.class, clientId);
                Hibernate.initialize(client);
                newAddress.setClient(client);
                entityManager.persist(newAddress);
                return true;
            } else {
                return false;
            }
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public void deleteClient(String id) {
        openEntityManager();
        try {
            ClientEntity client = entityManager.find(ClientEntity.class, id);
            entityManager.remove(client);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public void deleteAddress(String addressId) {
        openEntityManager();
        try {
            AddressEntity address = entityManager.find(AddressEntity.class, addressId);
            int client = address.getClient().getClientId();
            entityManager.remove(address);
            String checkFK = "select * from address where clientId =" + client;
            List<AddressEntity> addressList = entityManager.createNativeQuery(checkFK, AddressEntity.class).getResultList();
            if (addressList.size() == 0) {
                String delete = "delete from client where clientId =" + client;
                entityManager.createNativeQuery(delete).executeUpdate();
            }
        } finally {
            closeEntityManager();
        }
    }


    private void openEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

    }

    private void closeEntityManager() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
        entityManager.close();
    }

}
