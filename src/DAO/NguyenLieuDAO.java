package DAO;

import java.util.ArrayList;

import DTO.DanhMucDTO;
import DTO.NguyenLieuDTO;
import java.util.List;
import java.sql.*;

public class NguyenLieuDAO extends BaseDAO<NguyenLieuDTO>{
    public NguyenLieuDAO() {
        super(
        "nguyenlieu", 
        List.of(
         "tenNL",
         "donvi",
         "trangthai"
        ));
    }

    @Override
    protected NguyenLieuDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new NguyenLieuDTO(
                rs.getInt("idNL"),
                rs.getString("tenNL"),
                rs.getString("donvi"),
                rs.getInt("trangthai")
        );
    }
    
    public boolean add(NguyenLieuDTO nl) {
        List<Object> params = new ArrayList<>();
        params.add(nl.getTenNL());
        params.add(nl.getDonvi());
        params.add(nl.getTrangthai());
        return super.add(params);
    }
    
    public boolean update(NguyenLieuDTO nl) {
        List<Object> params = new ArrayList<>();
//      params.add(nl.getIdNL());
        params.add(nl.getTenNL());
        params.add(nl.getDonvi());
        params.add(nl.getTrangthai());
        String condition = "idNL = "+nl.getIdNL();
        return super.update(params, condition);
    }
    
    public boolean isInRecipe(int idNL) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isValue = false;
        try {
            String sql = "SELECT * FROM ct_congthuc WHERE idNL = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idNL);
            rs = pstmt.executeQuery();
            isValue = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return isValue;
    }
    
    public boolean isInInvoice(int idNL) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean hasNL = false;
        try {
            String sql = "SELECT * FROM lo_nguyenlieu WHERE idNL = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1, idNL);
            rs = pstmt.executeQuery();
            hasNL = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return hasNL;
    }
    
    public boolean delete(int idNL) {
        String col = "idNL";
        return super.delete(col, idNL);
    }
    
    public List<NguyenLieuDTO> search(String keyWord){
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<NguyenLieuDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM  nguyenlieu WHERE idNL LIKE ? OR tenNL LIKE ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setString(1, "%" + keyWord + "%");
            pstmt.setString(2, "%" + keyWord + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
    }
    
    public NguyenLieuDTO findByIdNL(int idNL) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        NguyenLieuDTO result = null;
        try {
            String sql = "SELECT * FROM nguyenlieu WHERE idNL = ?";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            pstmt.setInt(1,idNL);
            rs = pstmt.executeQuery();
            if (rs.next()) result = mapResultSetToDTO(rs);
        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
    }
    
    public boolean isExist(NguyenLieuDTO nl) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isExist = false;
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM ");
            sql.append(table);
            sql.append(" WHERE tenNL LIKE ?");
            if(nl.getIdNL() != 0) 
                sql.append(" AND idNL != ?");
           
            // noi param
            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setString(1, nl.getTenNL().trim()); // Loại bỏ khoảng trắng thừa
            if(nl.getIdNL() != 0) 
                pstmt.setInt(2, nl.getIdNL());
            
            // thuc thi
            rs = pstmt.executeQuery();
            isExist = rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return isExist;
    }
    public List<NguyenLieuDTO> getAllActive(){
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<NguyenLieuDTO> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM nguyenlieu WHERE trangthai = 1";
            link = db.connectDB();
            pstmt = link.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) result.add(mapResultSetToDTO(rs));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            db.close(link);
        }
        return result;
    }
}
