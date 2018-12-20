package com.api.service.resources;


import com.db.dao.DocenteDAO;
import com.db.dao.PrenotazioneDAO;
import com.db.dto.Docente;
import com.db.dto.Prenotazione;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
@Path("/reservations")
public class ReservationsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Prenotazione> getAll() {
        return PrenotazioneDAO.getAll();
    }


    /**
     * PUT        http:/students/{student}/{teacher}/{course}/{slot}/{date}
     * add a new reservation
     * method : put,
     * params {
     * "studente" : "studente",
     * "docente" : "mariolindo",
     * "corso" : "prog",
     * "slot" : "4",
     * "data" : "2018-12-31",
     * "stato" : "attiva",
     * "insegnamento" : "1"
     * };
     *
     * @param student  dkshbf vhsdbvk
     * @return fasuhfd sdbvsd;
     * @throws SQLException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/{student}/{teacher}/{course}/{slot}/{date}")
    public static int insert(@PathParam("student") String student,
                             @PathParam("teacher") String teacher,
                             @PathParam("course") String course,
                             @PathParam("slot") String slot,
                             @PathParam("date") String date) {

        int rowsAffected = PrenotazioneDAO.insert(new Prenotazione(student, teacher, course, slot, "attiva", date));

        //todo Handling errors

//        if (rowsAffected > 0){
        return rowsAffected;
//        }
    }

}
