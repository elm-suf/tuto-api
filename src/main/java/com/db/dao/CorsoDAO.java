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



public class CorsoDAO {

    public static List<Corso> getAll() {
        Connection conn = DBConnection.getInstance();
        String getAll = "SELECT * FROM corso";
        PreparedStatement st = null;
        ArrayList<Corso> c = null;
        try {
            st = conn.prepareStatement(getAll);
            c = new ArrayList<>();
            System.out.println();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Corso s = new Corso(rs.getInt("id"),rs.getString("titolo"));
                c.add(s);
            }

            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Corso insert(Corso c) {
        String insert = "INSERT INTO corso(titolo) VALUES(?)";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(insert);
            st.setString(1, c.getTitolo());
            System.out.println("CorsoDao Query: " + st.toString());
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (Exception e) {
            //catturo l'eccezione che potrebbe essere generata e ritorno immediatamente -1 error.
            return null;
        }
        return c;
    }

    public static Corso delete(Corso c) {
        String remove = "DELETE FROM corso WHERE titolo = ?";
        Connection conn = DBConnection.getInstance();
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(remove);
            st.setString(1, c.getTitolo());
            System.out.println("CorsoDao Query: " + st.toString());
            st.executeUpdate();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return c;
    }

    public static Corso update(Corso corso) {
        String modify = "UPDATE corso SET titolo = ? WHERE id = ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(modify);
            st.setString(1, corso.getTitolo());
            st.setInt(2, corso.getId());
            System.out.println("CorsoDao Query: " + st.toString());
            st.executeUpdate();

            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return corso;
    }

    public static int getN() {
        Connection conn = DBConnection.getInstance();
        String getN = "SELECT count(*) FROM corso";
        PreparedStatement st = null;
        int count = 0;
        try {
            st = conn.prepareStatement(getN);
            System.out.println("CorsoDao Query: " + st.toString());
            ResultSet rs = st.executeQuery();
            rs.next();
            count = rs.getInt(1);

            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return count;
        }
        return count;
    }

    public static Corso getCorso(String title) {
        String remove = "SELECT * FROM corso WHERE titolo = ?";
        Connection conn = DBConnection.getInstance();
        PreparedStatement st = null;
        Corso result = null;
        try {
            st = conn.prepareStatement(remove);
            st.setString(1, title);
            System.out.println("CorsoDao Query: " + st.toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                result = new Corso(rs.getInt("id"),rs.getString("titolo"));
            }
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static Corso getCorsoById(int id) {
        String remove = "SELECT * corso WHERE id = ?";
        Connection conn = DBConnection.getInstance();
        PreparedStatement st = null;
        Corso result = null;
        try {
            st = conn.prepareStatement(remove);
            st.setInt(1, id);
            System.out.println("CorsoDao Query: " + st.toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                result = new Corso(rs.getInt("id"),rs.getString("titolo"));
            }
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
