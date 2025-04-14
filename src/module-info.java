module cozycoffee {
    requires java.desktop; // Giữ nếu dùng GUI, bỏ nếu không cần
    requires java.sql;
    requires mysql.connector.j; // Sửa thành tên module tự động
    exports DAO;
}