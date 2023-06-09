package ru.sanctio.jakarta_laba_hib.api;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
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
    @Transactional
    public List<AddressEntity> getAllAddress() {
        return dbManager.getAllInformation();
    }

    @DELETE
    @Path("{idAdr}/{idCl}")
    public void delete(@PathParam("idAdr") String idAdr, @PathParam("idCl") String idCl) {
        dbManager.deleteAddress(idAdr, idCl);
    }
}