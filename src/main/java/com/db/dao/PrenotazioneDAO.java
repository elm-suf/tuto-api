package com.db.dao;

import com.db.DBConnection;
import com.db.dto.Prenotazione;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PrenotazioneDAO {

    public static List<Prenotazione> getAll() {
        Connection conn = DBConnection.getInstance();
        String getAll = "SELECT * FROM prenotazione";
        ArrayList<Prenotazione> pren = null;
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(getAll);
            pren = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Prenotazione s = new Prenotazione(rs.getString("studente"), rs.getString("docente"), rs.getString("corso"), rs.getString("slot"), rs.getString("stato"), rs.getString("data"));
                pren.add(s);
            }
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pren;
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
     * @param pren dkshbf vhsdbvk
     * @return fasuhfd sdbvsd;
     * @throws SQLException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/{student}/{teacher}/{course}/{slot}/{date}")
    public static int insert(Prenotazione pren) {

        //todo learn about @QueryParam annotation - Reactive Streams
        String sql = "INSERT INTO prenotazione(studente, stato, docente, corso, slot, data) VALUES  (?,?,?,?,?,?)";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, pren.getStato());
            st.setString(2, pren.getStudente());
            st.setString(3, pren.getDocente());
            st.setString(4, pren.getSlot());
            st.setString(5, pren.getData());
            st.setString(6, pren.getCorso());
            //todo non capisco perche hai rimosso corso
            System.out.println("Query : " + st.toString());
            return st.executeUpdate();

        } catch (SQLException e) {
            return -1;
        }
    }


    public static int delete(Prenotazione pren) throws SQLException {
        String remove = "DELETE FROM prenotazione WHERE stato = ? AND studente = ? AND docente = ? AND slot = ? AND data = ? AND corso = ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(remove);
            st.setString(1, pren.getStato());
            st.setString(2, pren.getStudente());
            st.setString(3, pren.getDocente());
            st.setString(4, pren.getSlot());
            st.setString(5, pren.getData());
            st.setString(6, pren.getCorso());
            return st.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
            return -1;
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }

    public static int disdisci(String docente, String data, String slot) throws SQLException {
        String remove = "UPDATE prenotazione SET stato = 'disdetta' WHERE docente=? AND data = ? AND slot =?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(remove);
            st.setString(1, docente);
            st.setString(2, data);
            st.setString(3, slot);
            System.out.println(st.toString());
            return st.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
            return -1;
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }


    public static int getN() throws SQLException {
        String getN = "SELECT count(id) FROM prenotazione";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(getN);
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }

    public static List getSlotDisponibili(String docente, String data) {
        //todo in realta' mi serve solo slot e lo stato
        String sql = "SELECT slot,stato FROM prenotazione WHERE docente = ? and data = ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        List list = new ArrayList<Prenotazione>();
        List<String> active = new ArrayList<>(); //todo forse e' meglio usare int
        active.add("1");
        active.add("2");
        active.add("3");
        active.add("4");
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, docente);
            st.setString(2, data);
            ResultSet rs = st.executeQuery();
            System.out.println("executing following query\n" + st.toString());
            while (rs.next()) {
                String stato = rs.getString("stato");
                String slot = rs.getString("slot");

                if (stato.equals("attiva")) {
                    active.remove(slot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return active;
    }

    public static List<Prenotazione> getAllPrenotazioniUtente(String studente) {
        String sql = "SELECT docente,corso,slot,stato,data FROM prenotazione where studente = ?";
        Connection conn = DBConnection.getInstance();
        List<Prenotazione> list = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, studente);

            System.out.println(st.toString());
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String docente = rs.getString("docente");
                String corso = rs.getString("corso");
                String slot = rs.getString("slot");
                String stato = rs.getString("stato");
                String data = rs.getString("data");

                list.add(new Prenotazione(studente, docente, corso, slot, stato, data));
            }

        } catch (SQLException e) {
            e.getMessage();
        }

        return list.isEmpty() ? null : list;
    }
}
