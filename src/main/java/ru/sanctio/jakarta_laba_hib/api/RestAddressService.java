package ru.sanctio.jakarta_laba_hib.api;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import ru.sanctio.jakarta_laba_hib.dao.DbManagerLocal;
import ru.sanctio.jakarta_laba_hib.entity.AddressEntity;

import java.util.List;

@Stateless
@Path("address")
public class RestAddressService {

    @EJB
    private DbManagerLocal dbManager;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public AddressEntity getAddress(@PathParam("id") String id) {
        return dbManager.getAddressById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<AddressEntity> getAllAddress() {
        return dbManager.getAllInformation();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("{clientId}")
    public void addAddress(AddressEntity address, @PathParam("clientId") String clientId) {
        dbManager.addClientAddress(address, clientId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void update(AddressEntity address) {
        dbManager.updateAddress(address);
    }

    @DELETE
    @Path("{idAdr}")
    public void delete(@PathParam("idAdr") String idAdr) {
        dbManager.deleteAddress(idAdr);
    }
}
