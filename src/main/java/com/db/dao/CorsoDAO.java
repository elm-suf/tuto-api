package com.db.dao;

import com.db.DBConnection;
import com.db.dto.Corso;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
@Path("/courses")
public class CorsoDAO {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static List<Corso> getAll() throws SQLException { //todo non capisco perche sta select torna anche gli id
        String getAll = "SELECT titolo FROM corso";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(getAll);
            ArrayList<Corso> c = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Corso s = new Corso(rs.getString("titolo"));
                c.add(s);
            }
            return c;
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }

    public static int insert(Corso c) throws SQLException {
        String insert = "INSERT INTO corso(titolo) VALUES(?)";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(insert);
            st.setString(1, c.getTitolo());
            return st.executeUpdate();

        }catch (Exception e){
            //catturo l'eccezione che potrebbe essere generata e ritorno immediatamente -1 error.
            return -1;
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }

    public static void delete(Corso c) throws SQLException {
        String remove = "DELETE FROM corso WHERE titolo = ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(remove);
            st.setString(1, c.getTitolo());
            System.out.println(st.toString());
            st.executeUpdate();
        }finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }

    public static void update(String titoloInit, String titoloFinal) throws SQLException {
        String modify = "UPDATE corso SET titolo = ? WHERE titolo = ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(modify);
            st.setString(1, titoloFinal);
            st.setString(2, titoloInit);
            st.executeUpdate();
        }finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }

    public static int getN() throws SQLException {
        String getN = "SELECT count(*) FROM corso";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(getN);
            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getInt(1);
        }finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }
}
