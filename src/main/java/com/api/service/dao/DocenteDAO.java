package com.api.service.dao;

import com.db.DBConnection;
import com.db.model.Docente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DocenteDAO {

    public DocenteDAO() {
    }


    public static List<Docente> getAll() throws SQLException {
        String getAll = "SELECT username, password, nome, cognome FROM docente";
        Connection conn = DBConnection.getInstance();
        PreparedStatement st = null;
        List<Docente> stud = null;
        stud = new ArrayList<>();
        st = conn.prepareStatement(getAll);
        System.out.println("DocenteDao Query: " + st.toString());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Docente s = new Docente(rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getString("cognome"));
            stud.add(s);
        }
        return stud;
    }

    public static int insert(Docente user) throws SQLException {
        String sqlInsertion = "INSERT INTO docente(username, nome, cognome, password) VALUE (?,?,?,?)";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();

        try {
            st = conn.prepareStatement(sqlInsertion);
            st.setString(1, user.getUsername());
            st.setString(2, user.getNome());
            st.setString(3, user.getCognome());
            st.setString(4, user.getPassword());
            System.out.println("DocenteDao Query: " + st.toString());

            return st.executeUpdate();
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }

    public static void delete(Docente user) throws SQLException {
        String sqlDelete = "DELETE FROM docente WHERE username=?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();

        try {
            st = conn.prepareStatement(sqlDelete);
            st.setString(1, user.getUsername());
            System.out.println("DocenteDao Query: " + st.toString());

            st.executeUpdate();
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }

    public static Docente update(Docente user) throws SQLException {
        String sqlUpdate = "UPDATE docente SET nome = ?, cognome = ? WHERE username= ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        Docente docente = null;

        try {
            st = conn.prepareStatement(sqlUpdate);
            st.setString(1, user.getNome());
            st.setString(2, user.getCognome());
            st.setString(3, user.getUsername());
            System.out.println("DocenteDao Query: " + st.toString());

            int i = st.executeUpdate();
            if (i > 0) {
                return user;
            } else return null;
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }


    public static int getN() throws SQLException {
        String getN = "SELECT count(*) FROM docente";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        try {
            st = conn.prepareStatement(getN);
            System.out.println("DocenteDao Query: " + st.toString());

            ResultSet rs = st.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            if (st != null) st.close();
            if (conn != null) conn.close();
        }
    }


    /**
     * @param corso il titolo del corso
     * @return la lista di professori che insegnano @param corso
     * @throws SQLException
     */
    public static List<Docente> getAllInsegnaMateria(String corso) throws SQLException {
        String sql = "SELECT p.username, nome, cognome, password FROM docente AS p, insegnamento AS i WHERE  i.docente = p.username AND i.corso = ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        List<Docente> list = new ArrayList<>();


        st = conn.prepareStatement(sql);
        st.setString(1, corso);
        System.out.println("DocenteDao Query: " + st.toString());

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            list.add(new Docente(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)));

        }
        return list;
    }

    public static void main(String[] args) throws SQLException {

        DocenteDAO dao = new DocenteDAO();
        //System.out.println(dao.getAllInsegnaMateria("prog"));

//        Docente prof = new Docente("Trinciao", "Paolo", "Rossi", "password");
//        Corso corso = new Corso("italiano");
//        Corso corso1 = new Corso("prog");
//        prof.addInsegnamento(corso);
//        prof.addInsegnamento(corso1);
//
//        try {
//            dao.insert(prof);
//        } catch (SQLException e) {
//            e.getMessage();
//        }

//        try {
//            dao.addInsegnamento(prof,corso);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        try {
//            dao.deleteInsegnamento(prof,corso);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        try {
//            dao.insert(prof);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            dao.update(new Docente("Trinciao", "sassaassa", "altobelli", "password"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

//        try {
//            dao.delete(new Docente("Trinciao", "Paolo", "Rossi", "password"));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//

    }

    public static Docente getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM docente WHERE username = ?";
        Connection conn = DBConnection.getInstance();
        PreparedStatement st;
        Docente response = null;
        ResultSet rs;

        st = conn.prepareStatement(sql);
        st.setString(1, username);
        System.out.println("DocenteDao query :" + st.toString());
        rs = st.executeQuery();
        if (rs.next()) {
            response = new Docente(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getString("cognome"));
        }

        return response;
    }
}

