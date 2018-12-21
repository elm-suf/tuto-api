package com.api.resources;


import com.api.service.exception.NotFoundException;
import com.api.service.dao.DocenteDAO;
import com.api.service.dao.PrenotazioneDAO;
import com.db.model.Docente;
import com.db.model.Prenotazione;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/teachers")
public class TeachersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Docente> getAll() throws SQLException {
        return DocenteDAO.getAll();
    }

    @GET
    @Path("/{username}")
    public static Docente update(@PathParam("username") String username) throws SQLException {
        Docente byUsername = DocenteDAO.getByUsername(username);
        if (byUsername == null) {
            throw new NotFoundException("User not found");
        } else {
            return byUsername;
        }
    }

    @PUT
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public static Docente update(@PathParam("username") String username, Docente docente) throws SQLException {
        docente.setUsername(username);
        return DocenteDAO.update(docente);
    }

    @GET
    @Path("/teaching/{course}")
    public static List<Docente> getTeachingSubject(@PathParam("course") String subject) throws SQLException {
        List<Docente> list = DocenteDAO.getAllInsegnaMateria(subject);
        if (list.isEmpty()) {
            throw new javax.ws.rs.NotFoundException("empty list");
        } else return list;
    }

    @GET
    @Path("/{username}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Prenotazione> getAvailable
            (@PathParam("username") String username, @PathParam("date") String date) throws SQLException {
        return PrenotazioneDAO.getSlotDisponibili(username, date);
    }

}
