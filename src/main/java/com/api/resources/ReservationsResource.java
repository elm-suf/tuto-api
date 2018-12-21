package com.api.resources;


import com.api.service.dao.PrenotazioneDAO;
import com.db.model.Prenotazione;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;


@Path("/reservations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReservationsResource {

    @GET
    public static List<Prenotazione> getAll() {
        return PrenotazioneDAO.getAll();
    }


    @PUT
    @Path("/{student}/{teacher}/{course}/{slot}/{date}")
    public static Prenotazione insert(@PathParam("student") String student,
                                      @PathParam("teacher") String teacher,
                                      @PathParam("course") String course,
                                      @PathParam("slot") String slot,
                                      @PathParam("date") String date) throws SQLException {

        return PrenotazioneDAO.insert(new Prenotazione(student, teacher, course, slot, "attiva", date));
    }

}
