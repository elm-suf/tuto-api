package com.db.dao;

import com.db.DBConnection;
import com.db.dto.Docente;
import com.db.dto.Corso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class InsegnamentoDAO {
    public void addInsegnamento(Docente user, Corso corso) throws SQLException {

        String sql = "INSERT INTO insegnamento(corso, docente) VALUE (?,?);";
        Connection conn = DBConnection.getInstance();
        PreparedStatement st = conn.prepareStatement(sql);
        try {
            st.setString(1, corso.getTitolo());
            st.setString(2, user.getUsername());
            System.out.println(st.toString());
            st.executeUpdate();
        } finally {
            if (st != null) st.close();
            conn.close();
        }
    }

    public void deleteInsegnamento(Docente user, Corso corso) throws SQLException {
        user.removeInsegnamento(corso);
        String sql = "DELETE FROM insegnamento \n" +
                "WHERE corso = ? AND docente = ?;";
        Connection conn = DBConnection.getInstance();
        PreparedStatement st = conn.prepareStatement(sql);
        try {
            st.setString(1, corso.getTitolo());
            st.setString(2, user.getUsername());

            st.executeUpdate();
        } finally {
            if (st != null) st.close();
            conn.close();
        }

    }


    /**
     *
     * @param corso il titolo del corso
     * @return la lista di professori che insegnano @param corso
     * @throws SQLException
     */
    public List<Docente> getAllInsegnaMateria(String corso) throws SQLException {
        String sql = "SELECT p.username, nome, cognome, password FROM docente AS p, insegnamento AS i WHERE  i.docente = p.username AND i.corso = ?";
        PreparedStatement st = null;
        Connection conn = DBConnection.getInstance();
        List<Docente> list = new ArrayList<>();


        st = conn.prepareStatement(sql);
        st.setString(1, corso);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            list.add(new Docente(rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)));

        }
        return list;
    }


    public static int getIdInsegnamento(String corso, String docente) throws SQLException {
        String sql = "SELECT id FROM insegnamento WHERE corso = ? AND docente = ?;";
        Connection conn = DBConnection.getInstance();
        PreparedStatement st = conn.prepareStatement(sql);
        try {
            st.setString(1, corso);
            st.setString(2, docente);

            ResultSet resultSet = st.executeQuery();

            if(resultSet.first()){
                return resultSet.getInt(1);
            }

            return -1;

        } finally {
            if (st != null) st.close();
            conn.close();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(InsegnamentoDAO.getIdInsegnamento("asa","mariolindo" ));
        } catch (SQLException e) {
            e.getMessage();
        }

    }
}
