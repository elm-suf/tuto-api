package com.api.resources;

import com.api.service.dao.CorsoDAO;
import com.db.model.Corso;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/courses")
public class CoursesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Corso> getAll(){
        return CorsoDAO.getAll();
    }

    @POST
    public static Corso insert(Corso course){
        return CorsoDAO.insert(course);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public static Corso delete(Corso course){
        //todo handle null - need id as well
        return CorsoDAO.delete(course);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public static Corso update(Corso course){
        return CorsoDAO.update(course);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{course}")
    public static Corso get(@PathParam("course") String title){
        return CorsoDAO.getCorso(title);
    }

}

