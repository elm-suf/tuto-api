package com.api.service.resources;


import com.db.dao.DocenteDAO;
import com.db.dto.Docente;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
@Path("/teachers")
public class TeachersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Docente> getAll() {
        return DocenteDAO.getAll();
    }


}
