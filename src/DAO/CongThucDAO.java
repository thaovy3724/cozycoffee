package DAO;

import DTO.CongThucDTO;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CongThucDAO extends BaseDAO<CongThucDTO> {
    public CongThucDAO() {
        super(
            "congthuc", 
            List.of(
                "idCT",
                "mota",
                "idSP"
            ));
    }

    @Override
    protected CongThucDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new CongThucDTO(
                rs.getInt("idCT"),
                rs.getInt("idSP"),
                rs.getString("mota")
        );
    }
    
    public int add(CongThucDTO ct) {
        List<Object> params = new ArrayList<>();
        params.add(ct.getMota());
        params.add(ct.getIdSP() == 0 ? null : ct.getIdSP());
        String sql = "INSERT INTO congthuc (mota, idSP) VALUES (?, ?)";
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            link = db.connectDB();
            pstmt = link.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                return -1;
            }
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Trả về idCT được sinh tự động
            }
            return -1;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
    }
    
    public boolean update(CongThucDTO ct) {
        List<Object> params = new ArrayList<>();
        params.add(ct.getMota());
        params.add(ct.getIdSP() == 0 ? null : ct.getIdSP());
        String condition = "idCT = " + ct.getIdCT();
        String sql = "UPDATE congthuc SET mota = ?, idSP = ? WHERE " + condition;
        Connection link = null;
        PreparedStatement pstmt = null;
        try {
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
    }

    public boolean isProduct(int idSP) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean hasProduct = false;
        try {
            String sql = "SELECT * FROM sanpham WHERE idSP = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idSP);
            rs = pstmt.executeQuery();
            hasProduct = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
        return hasProduct;
    }
    
    public boolean delete(int idCT) {
        String col = "idCT";
        return super.delete(col, idCT);
    }
    
    public List<CongThucDTO> search(String keyWord) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CongThucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idCT LIKE ? ";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, "%" + keyWord + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
        return result;
    }
    
    public CongThucDTO findByIdCT(int idCT) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CongThucDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idCT = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idCT);
            rs = pstmt.executeQuery();
            if (rs.next()) result = mapResultSetToDTO(rs);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
        return result;
    }

    public List<CongThucDTO> findByIdSP(int idSP) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CongThucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM congthuc WHERE idSP = ? ";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idSP);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
        return result;
    }

    public boolean isExist(CongThucDTO ct) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isExist = false;
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(table);
            sql.append(" WHERE idSP = ?");
            if (ct.getIdCT() != 0) {
                sql.append(" AND idCT != ?");
            }
            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setInt(1, ct.getIdSP());
            if (ct.getIdCT() != 0) {
                pstmt.setInt(2, ct.getIdCT());
            }
            rs = pstmt.executeQuery();
            isExist = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
        return isExist;
    }
    
    public List<CongThucDTO> getAllActiveEdit(int idCT, int idSP) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CongThucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE idCT != ? AND idSP != ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idCT);
            pstmt.setInt(2, idSP);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
        return result;
    }
    
    public List<CongThucDTO> getAllActive() {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CongThucDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM congthuc ";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            db.close(link);
        }
        return result;
    }
}