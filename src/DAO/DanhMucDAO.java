package DAO;

import java.util.ArrayList;
import DTO.DanhMucDTO;
import java.util.List;
import java.sql.*;

public class DanhMucDAO extends BaseDAO<DanhMucDTO>{
	public DanhMucDAO() {
		super("danhmuc", 
		List.of(
		 "idDM",
		 "tenDM",
		 "trangthai",
		 "idDMCha"
		));
	}

	@Override
	protected DanhMucDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new DanhMucDTO(
                rs.getInt("idDM"),
                rs.getString("tenDM"),
                rs.getInt("trangthai"),
                rs.getInt("idDMCha")
        );
    }
	
	public int add(DanhMucDTO danhMuc) {
		List<Object> params = new ArrayList<>();
		params.add(danhMuc.getIdDM());
		params.add(danhMuc.getTenDM());
		params.add(danhMuc.getTrangthai());
		params.add(danhMuc.getIdDMCha() == -1 ? null : danhMuc.getIdDMCha());

		return super.add(params);
	}
	
	public boolean update(DanhMucDTO danhMuc) {
		List<Object> params = new ArrayList<>();
		params.add(danhMuc.getIdDM());
		params.add(danhMuc.getTenDM());
		params.add(danhMuc.getTrangthai());
		params.add(danhMuc.getIdDMCha()==-1 ? null : danhMuc.getIdDMCha());
		String condition = "idDM = "+danhMuc.getIdDM();
		return super.update(params, condition);
	}
	
	public boolean isParentCategory(int idDM) {
		DatabaseConnect db = new DatabaseConnect();
        String sql = "SELECT * FROM " + table + " WHERE idDMCha = "+idDM;
        ResultSet rs = null;
        boolean isParent = false;
        try {
        	rs = db.getAll(sql, null);
            if (rs.next()) isParent = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        	try {
            db.close(rs);
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return isParent;
	}
	
	public boolean hasProductInCategory(int idDM) {
		DatabaseConnect db = new DatabaseConnect();
        String sql = "SELECT * FROM sanpham WHERE idDM = "+idDM;
        ResultSet rs = null;
        boolean isParent = false;
        try {
        	rs = db.getAll(sql, null);
            if (rs.next()) isParent = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        	try {
            db.close(rs);
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return isParent;
	}
	
	public boolean delete(int idDM) {
        DatabaseConnect db = new DatabaseConnect();
        String sql = "DELETE FROM "+ table +" WHERE idDM = "+idDM;
        return db.execute(sql, null);
    }
	
	public boolean lock(int idDM) {
        DatabaseConnect db = new DatabaseConnect();
        String sql = "UPDATE "+ table +" SET trangthai = 0 WHERE idDM = "+idDM;
        return db.execute(sql, null);
    }
	
	public boolean unlock(int idDM) {
		DatabaseConnect db = new DatabaseConnect();
        String sql = "UPDATE "+ table +" SET trangthai = 1 WHERE idDM = "+idDM;
        return db.execute(sql, null);
	} 
	
	public List<DanhMucDTO> search(String keyWord){
		List<String> conditions = new ArrayList<>();
		conditions.add("idDM");
		conditions.add("tenDM");
		List<Object> params = new ArrayList<>();
		params.add(keyWord);
		params.add(keyWord);
		return super.search(conditions, params);
	}
}
