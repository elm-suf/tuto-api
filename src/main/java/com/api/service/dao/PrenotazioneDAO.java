package com.api.service.dao;

import com.db.DBConnection;
import com.db.model.Prenotazione;

import java.sql.*;
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


    public static Prenotazione insert(Prenotazione pren) throws SQLException {

        //todo learn about @QueryParam annotation - Reactive Streams
        String sql = "INSERT INTO prenotazione(studente, stato, docente, corso, slot, data) VALUES  (?,?,?,?,?,?)";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        st = conn.prepareStatement(sql);
        st.setString(1, pren.getStudente());
        st.setString(2, pren.getStato());
        st.setString(3, pren.getDocente());
        st.setString(4, pren.getCorso());
        st.setString(5, pren.getSlot());
        st.setString(6, pren.getData());
        System.out.println("Query : " + st.toString());
        int affected = st.executeUpdate();

        if (affected > 0) {
            return pren;
        } else {
            throw new SQLDataException("no rows affected");
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

    public static List<Prenotazione> getSlotDisponibili(String docente, String data) throws SQLException {
        String sql = "SELECT slot,stato FROM prenotazione WHERE docente = ? and data = ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        List list = new ArrayList<Prenotazione>();
        List<Prenotazione> list1 = new ArrayList<>(); //todo forse e' meglio usare int
        for (int i = 1; i < 5; i++) {
            list1.add(new Prenotazione(String.valueOf(i), "available"));
        }

        st = conn.prepareStatement(sql);
        st.setString(1, docente);
        st.setString(2, data);
        System.out.println("PrenotazioneDao query:\n" + st.toString());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            String stato = rs.getString("stato");
            String slot = rs.getString("slot");

            if (stato.equals("attiva")) {
                stato = "not available";
            }
            list1.set(Integer.parseInt(slot) - 1, new Prenotazione(slot, stato));
        }
        return list1;
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
