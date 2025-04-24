package DAO;

import DTO.SanPhamDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO extends BaseDAO<SanPhamDTO> {
    public SanPhamDAO() {
        super("sanpham", List.of("idSP", "tenSP", "giaban", "hinhanh", "idDM", "trangthai"));
    }

    @Override
    protected SanPhamDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new SanPhamDTO(
            rs.getInt("idSP"),
            rs.getString("tenSP"),
            rs.getInt("giaban"),
            rs.getString("hinhanh"),
            rs.getInt("trangthai"),
            rs.getInt("idDM")
        );
    }

    public boolean add(SanPhamDTO sp) {
        List<Object> params = new ArrayList<>();
        params.add(sp.getTenSP());
        params.add(sp.getGiaban());
        params.add(sp.getHinhanh());
        params.add(sp.getIdDM());
        params.add(sp.getTrangthai());
        return super.add(params);
    }

    public boolean update(SanPhamDTO sp) {
        List<Object> params = new ArrayList<>();
        params.add(sp.getTenSP());
        params.add(sp.getGiaban());
        params.add(sp.getHinhanh());
        params.add(sp.getIdDM());
        params.add(sp.getTrangthai());
        String condition = "idSP = " + sp.getIdSP();
        return super.update(params, condition);
    }

    public boolean delete(int idSP) {
        return super.delete("idSP", idSP);
    }

    public SanPhamDTO findByIdSP(int idSP) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SanPhamDTO result = null;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idSP = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idSP);
            rs = pstmt.executeQuery();
            if (rs.next()) result = mapResultSetToDTO(rs);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }
        return result;
    }

    public List<SanPhamDTO> getAll() {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SanPhamDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table;
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }
        return result;
    }

    public List<SanPhamDTO> getAllActive() {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SanPhamDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM sanpham WHERE trangthai = 1";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }
        return result;
    }

    public List<SanPhamDTO> search(String keyword) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<SanPhamDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM " + table + " WHERE tenSP LIKE ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }
        return result;
    }

    public boolean exists(int idSP) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            String sql = "SELECT * FROM " + table + " WHERE idSP = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idSP);
            rs = pstmt.executeQuery();
            exists = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(link);
        }
        return exists;
    }
}
