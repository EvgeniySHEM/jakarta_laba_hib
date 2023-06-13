package ru.sanctio.jakarta_laba_hib.api;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ru.sanctio.jakarta_laba_hib.dao.DbManagerLocal;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

import java.time.LocalDate;
import java.util.List;

@Stateless
@Path("client")
public class RestClientService {
    @EJB
    private DbManagerLocal dbManager;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public ClientEntity getClient(@PathParam("id") Integer id) {
        return dbManager.getClientById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<ClientEntity> getAllClients() {
        System.out.println(dbManager.getAllClient());
        return dbManager.getAllClient();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createNewClient(ClientEntity client) {
        if (client.getAdded() == null) {
            client.setAdded(LocalDate.now().toString());
        }
        dbManager.createNewClient(client);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void update(ClientEntity client) {
        if (client.getAdded() == null) {
            client.setAdded(LocalDate.now().toString());
        }
        System.out.println(client);
        dbManager.updateClient(client);
    }

    @DELETE
    @Path("{id}")
    public void deleteClient(@PathParam("id") String id) {
        dbManager.deleteClient(id);
    }

}
