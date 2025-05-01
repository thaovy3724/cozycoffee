package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseDAO<T> {
    protected String table;
    protected List<String> tableColumns;
    protected DatabaseConnect db;

    public BaseDAO(String table, List<String> tableColumns) {
        this.table = table;
        this.tableColumns = tableColumns;
        db = new DatabaseConnect();
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            link = db.connectDB();
            String sql = "SELECT * FROM " + table;
            pstmt = link.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
				list.add(mapResultSetToDTO(rs));
			}
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
        return list;
    }

    public int getNewAutoIncrementNumber() {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int newID = -1;
        try {
            link = db.connectDB();
            String sql = "SELECT AUTO_INCREMENT as newID FROM information_schema.TABLES " +
                        "WHERE TABLE_SCHEMA = '" + db.getDBName() + "' " +
                        "AND TABLE_NAME = '" + table + "'";
            pstmt = link.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
				newID = rs.getInt("newID");
			}
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
        return newID;
    }

    public boolean add(List<Object> params) {
        Connection link = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        try {
            if (params != null && params.size() == tableColumns.size()) {
                StringBuilder sql = new StringBuilder("INSERT INTO ");
                sql.append(table);
                sql.append(" (");
                sql.append(String.join(",", tableColumns));
                sql.append(") VALUES (");
                sql.append(String.join(",", Collections.nCopies(params.size(), "?")));
                sql.append(")");

                link = db.connectDB();
                pstmt = link.prepareStatement(sql.toString());
                for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}

                success = pstmt.executeUpdate() > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
        return success;
    }

    public int addWithoutPrimaryKey(List<Object> params) {
        Connection link = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            if (params != null && params.size() == tableColumns.size() - 1) {
                List<String> columnsWithoutPrimaryKey = new ArrayList<>(tableColumns.subList(1, tableColumns.size()));
                StringBuilder sql = new StringBuilder("INSERT INTO ");
                sql.append(table);
                sql.append(" (");
                sql.append(String.join(",", columnsWithoutPrimaryKey));
                sql.append(") VALUES (");
                sql.append(String.join(",", Collections.nCopies(params.size(), "?")));
                sql.append(")");

                link = db.connectDB();
                pstmt = link.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected == 0) {
                    return -1;
                }
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return -1;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
				try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
        return -1;
    }

    public boolean update(List<Object> params, String condition) {
        Connection link = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        try {
            if (params != null && params.size() == tableColumns.size()) {
                StringBuilder sql = new StringBuilder("UPDATE ");
                sql.append(table);
                sql.append(" SET ");
                for (int i = 0; i < tableColumns.size(); i++) {
                    sql.append(tableColumns.get(i)).append(" = ?");
                    if (i < tableColumns.size() - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(" WHERE ").append(condition);

                link = db.connectDB();
                pstmt = link.prepareStatement(sql.toString());
                for (int i = 0; i < params.size(); i++) {
					pstmt.setObject(i + 1, params.get(i));
				}

                success = pstmt.executeUpdate() > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
        return success;
    }

    public boolean delete(String column, Object value) {
        Connection link = null;
        PreparedStatement pstmt = null;
        boolean success = false;
        try {
            StringBuilder sql = new StringBuilder("DELETE FROM ");
            sql.append(table);
            sql.append(" WHERE ");
            sql.append(column);
            sql.append(" = ?");

            link = db.connectDB();
            pstmt = link.prepareStatement(sql.toString());
            pstmt.setObject(1, value);

            success = pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
			}
            db.close(link);
        }
        return success;
    }

    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
}