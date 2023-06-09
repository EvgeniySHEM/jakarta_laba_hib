package ru.sanctio.jakarta_laba_hib.api;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ru.sanctio.jakarta_laba_hib.dao.DbManagerLocal;
import ru.sanctio.jakarta_laba_hib.entity.ClientEntity;

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
    @Transactional
    public List<ClientEntity> getAllClients() {
        return dbManager.getAllClient();
    }

    @DELETE
    @Path("{id}")
    public void deleteClient(@PathParam("id") String id) {
        dbManager.deleteClient(id);
    }

}
