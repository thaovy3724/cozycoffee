-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 02, 2025 at 05:51 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `coffeeshop`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `xuLyDonHang` (IN `input_idHD` INT)   BEGIN
    DECLARE sp_id INT;
    DECLARE sp_soLuong INT;
    DECLARE ct_id INT;
    DECLARE nl_id INT;
    DECLARE nl_soLuongCan FLOAT;
    DECLARE nl_soLuongCanUpdate FLOAT;
    DECLARE lo_id INT;
    DECLARE lo_soLuongTon FLOAT;

    DECLARE done_sp INT DEFAULT 0;
    DECLARE done_ct INT DEFAULT 0;
    DECLARE done_nl INT DEFAULT 0;
    DECLARE done_lo INT DEFAULT 0;

    DECLARE cur_sp CURSOR FOR 
        SELECT idSP, soluong FROM ct_hoadon WHERE idHD = input_idHD;

    DECLARE cur_ct CURSOR FOR 
        SELECT idCT FROM congthuc WHERE idSP = sp_id;

    DECLARE cur_nl CURSOR FOR 
        SELECT idNL, soluong FROM ct_congthucc WHERE idCT = ct_id;

    DECLARE cur_lo CURSOR FOR 
        SELECT idPN, tonkho FROM lo_nguyenlieu 
        WHERE idNL = nl_id AND tonkho > 0 
        ORDER BY hsd ASC;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_lo = 1;

    OPEN cur_sp;
    read_sp: LOOP
        FETCH cur_sp INTO sp_id, sp_soLuong;
        IF done_sp THEN
            LEAVE read_sp;
        END IF;

        SET done_ct = 0;
        OPEN cur_ct;
        read_ct: LOOP
            FETCH cur_ct INTO ct_id;
            IF done_ct THEN
                LEAVE read_ct;
            END IF;

            SET done_nl = 0;
            OPEN cur_nl;
            read_nl: LOOP
                FETCH cur_nl INTO nl_id, nl_soLuongCan;
                IF done_nl THEN
                    LEAVE read_nl;
                END IF;

                SET nl_soLuongCanUpdate = nl_soLuongCan * sp_soLuong;

                SET done_lo = 0;
                OPEN cur_lo;
                read_lo: LOOP
                    FETCH cur_lo INTO lo_id, lo_soLuongTon;
                    IF done_lo OR nl_soLuongCanUpdate <= 0 THEN
                        LEAVE read_lo;
                    END IF;

                    IF lo_soLuongTon >= nl_soLuongCanUpdate THEN
                        UPDATE lo_nguyenlieu
                        SET tonkho = tonkho - nl_soLuongCanUpdate
                        WHERE idPN = lo_id;
                        SET nl_soLuongCanUpdate = 0;
                        LEAVE read_lo;
                    ELSE
                        SET nl_soLuongCanUpdate = nl_soLuongCanUpdate - lo_soLuongTon;
                        UPDATE lo_nguyenlieu
                        SET tonkho = 0
                        WHERE idPN = lo_id;
                    END IF;
                END LOOP;
                CLOSE cur_lo;

            END LOOP;
            CLOSE cur_nl;

        END LOOP;
        CLOSE cur_ct;

    END LOOP;
    CLOSE cur_sp;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `congthuc`
--

CREATE TABLE `congthuc` (
  `idCT` int NOT NULL,
  `mota` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `idSP` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `congthuc`
--

INSERT INTO `congthuc` (`idCT`, `mota`, `idSP`) VALUES
(2, 'abc', 2),
(3, 'abc', 3);

--
-- Triggers `congthuc`
--
DELIMITER $$
CREATE TRIGGER `trg_after_delete_congthuc` AFTER DELETE ON `congthuc` FOR EACH ROW BEGIN
    UPDATE sanpham
    SET trangthai = 0
    WHERE idSP = OLD.idSP;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `ct_congthuc`
--

CREATE TABLE `ct_congthuc` (
  `idCT` int NOT NULL,
  `idNL` int NOT NULL,
  `soluong` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ct_congthuc`
--

INSERT INTO `ct_congthuc` (`idCT`, `idNL`, `soluong`) VALUES
(2, 1, 1),
(2, 2, 1),
(3, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ct_hoadon`
--

CREATE TABLE `ct_hoadon` (
  `idSP` int NOT NULL,
  `idHD` int NOT NULL,
  `soluong` int NOT NULL,
  `gialucdat` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `ct_hoadon`
--

INSERT INTO `ct_hoadon` (`idSP`, `idHD`, `soluong`, `gialucdat`) VALUES
(2, 2, 1, 1),
(2, 6, 1, 10000),
(2, 7, 1, 10000),
(2, 8, 1, 10000),
(2, 9, 2, 10000),
(3, 9, 1, 100000),
(3, 10, 2, 100000);

-- --------------------------------------------------------

--
-- Table structure for table `danhmuc`
--

CREATE TABLE `danhmuc` (
  `idDM` int NOT NULL,
  `tenDM` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idDMCha` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `danhmuc`
--

INSERT INTO `danhmuc` (`idDM`, `tenDM`, `trangthai`, `idDMCha`) VALUES
(13, 'nước uống', 0, NULL),
(15, 'chè', 0, NULL),
(16, 'sodaaaa2', 1, 13),
(17, 'cà phê', 1, 16),
(18, 'thức ăn', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

CREATE TABLE `hoadon` (
  `idHD` int NOT NULL,
  `ngaytao` date NOT NULL,
  `idTK` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `hoadon`
--

INSERT INTO `hoadon` (`idHD`, `ngaytao`, `idTK`) VALUES
(2, '2025-04-28', 2),
(3, '2025-04-28', 1),
(4, '2025-04-28', 1),
(5, '2025-04-28', 1),
(6, '2025-04-28', 1),
(7, '2025-04-29', 1),
(8, '2025-04-29', 1),
(9, '2025-05-01', 1),
(10, '2025-05-01', 1);

-- --------------------------------------------------------

--
-- Table structure for table `lo_nguyenlieu`
--

CREATE TABLE `lo_nguyenlieu` (
  `idNL` int NOT NULL,
  `idPN` int NOT NULL,
  `soluongnhap` float NOT NULL,
  `tonkho` float NOT NULL,
  `dongia` int NOT NULL,
  `hsd` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lo_nguyenlieu`
--

INSERT INTO `lo_nguyenlieu` (`idNL`, `idPN`, `soluongnhap`, `tonkho`, `dongia`, `hsd`) VALUES
(1, 1, 10, 10, 50000, '2025-05-14'),
(2, 1, 100, 100, 50000, '2025-05-14');

-- --------------------------------------------------------

--
-- Table structure for table `nguyenlieu`
--

CREATE TABLE `nguyenlieu` (
  `idNL` int NOT NULL,
  `donvi` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tenNL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `nguyenlieu`
--

INSERT INTO `nguyenlieu` (`idNL`, `donvi`, `tenNL`) VALUES
(1, 'củ', 'cà rốt'),
(2, 'kg', 'đường');

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `idNCC` int NOT NULL,
  `tenNCC` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `diachi` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `sdt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`idNCC`, `tenNCC`, `diachi`, `sdt`, `email`, `trangthai`) VALUES
(2, 'abc', 'abc', '0778052785', 'phuongnam@gmail.com', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `idNQ` int NOT NULL,
  `tenNQ` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `nhomquyen`
--

INSERT INTO `nhomquyen` (`idNQ`, `tenNQ`) VALUES
(1, 'Admin'),
(2, 'Nhân viên\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `phieunhap`
--

CREATE TABLE `phieunhap` (
  `idPN` int NOT NULL,
  `ngaytao` date NOT NULL,
  `ngaycapnhat` date NOT NULL,
  `idTK` int NOT NULL,
  `idNCC` int NOT NULL,
  `idTT` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `phieunhap`
--

INSERT INTO `phieunhap` (`idPN`, `ngaytao`, `ngaycapnhat`, `idTK`, `idNCC`, `idTT`) VALUES
(1, '2025-05-01', '2025-05-01', 2, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `idSP` int NOT NULL,
  `tenSP` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `giaban` int NOT NULL,
  `hinhanh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idDM` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`idSP`, `tenSP`, `giaban`, `hinhanh`, `trangthai`, `idDM`) VALUES
(2, 'honey', 10000, '173cc6db-396f-4574-8bc3-0a0ebb65f819.jpg', 1, 15),
(3, 'sữa', 100000, '019d0a20-7a41-432e-816d-1119e7a6a560.jpg', 1, 15);

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `idTK` int NOT NULL,
  `tenTK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `matkhau` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idNQ` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`idTK`, `tenTK`, `matkhau`, `email`, `trangthai`, `idNQ`) VALUES
(1, 'thaovy3724', '$2a$10$S9hM1lWCViB1IAeLqeGd9uh2aOtm1l/OG7atMylHQeInw2p6ECxgy', 'thaovy3724@gmail.com', 1, 1),
(2, 'Như Ý', '0778052785Vyle.', 'nhuy3011@gmail.com', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `trangthai_pn`
--

CREATE TABLE `trangthai_pn` (
  `idTT` int NOT NULL,
  `tenTT` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `trangthai_pn`
--

INSERT INTO `trangthai_pn` (`idTT`, `tenTT`) VALUES
(1, 'Chưa hoàn tất'),
(2, 'Hoàn tất'),
(3, 'Bị hủy');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `congthuc`
--
ALTER TABLE `congthuc`
  ADD PRIMARY KEY (`idCT`),
  ADD KEY `idSP` (`idSP`);

--
-- Indexes for table `ct_congthuc`
--
ALTER TABLE `ct_congthuc`
  ADD PRIMARY KEY (`idCT`,`idNL`),
  ADD KEY `idNL` (`idNL`);

--
-- Indexes for table `ct_hoadon`
--
ALTER TABLE `ct_hoadon`
  ADD PRIMARY KEY (`idSP`,`idHD`),
  ADD KEY `idHD` (`idHD`);

--
-- Indexes for table `danhmuc`
--
ALTER TABLE `danhmuc`
  ADD PRIMARY KEY (`idDM`);

--
-- Indexes for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`idHD`),
  ADD KEY `idTK` (`idTK`);

--
-- Indexes for table `lo_nguyenlieu`
--
ALTER TABLE `lo_nguyenlieu`
  ADD PRIMARY KEY (`idNL`,`idPN`),
  ADD KEY `idPN` (`idPN`);

--
-- Indexes for table `nguyenlieu`
--
ALTER TABLE `nguyenlieu`
  ADD PRIMARY KEY (`idNL`);

--
-- Indexes for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`idNCC`);

--
-- Indexes for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  ADD PRIMARY KEY (`idNQ`);

--
-- Indexes for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`idPN`),
  ADD KEY `idTK` (`idTK`),
  ADD KEY `idNCC` (`idNCC`),
  ADD KEY `idTT` (`idTT`);

--
-- Indexes for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`idSP`),
  ADD KEY `idDM` (`idDM`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`idTK`),
  ADD KEY `idNQ` (`idNQ`);

--
-- Indexes for table `trangthai_pn`
--
ALTER TABLE `trangthai_pn`
  ADD PRIMARY KEY (`idTT`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `congthuc`
--
ALTER TABLE `congthuc`
  MODIFY `idCT` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `danhmuc`
--
ALTER TABLE `danhmuc`
  MODIFY `idDM` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `idHD` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `nguyenlieu`
--
ALTER TABLE `nguyenlieu`
  MODIFY `idNL` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `idNCC` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  MODIFY `idNQ` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `idPN` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `idSP` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `idTK` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `trangthai_pn`
--
ALTER TABLE `trangthai_pn`
  MODIFY `idTT` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `congthuc`
--
ALTER TABLE `congthuc`
  ADD CONSTRAINT `congthuc_ibfk_1` FOREIGN KEY (`idSP`) REFERENCES `sanpham` (`idSP`);

--
-- Constraints for table `ct_congthuc`
--
ALTER TABLE `ct_congthuc`
  ADD CONSTRAINT `ct_congthuc_ibfk_1` FOREIGN KEY (`idCT`) REFERENCES `congthuc` (`idCT`),
  ADD CONSTRAINT `ct_congthuc_ibfk_2` FOREIGN KEY (`idNL`) REFERENCES `nguyenlieu` (`idNL`);

--
-- Constraints for table `ct_hoadon`
--
ALTER TABLE `ct_hoadon`
  ADD CONSTRAINT `ct_hoadon_ibfk_1` FOREIGN KEY (`idSP`) REFERENCES `sanpham` (`idSP`),
  ADD CONSTRAINT `ct_hoadon_ibfk_2` FOREIGN KEY (`idHD`) REFERENCES `hoadon` (`idHD`),
  ADD CONSTRAINT `ct_hoadon_ibfk_3` FOREIGN KEY (`idSP`) REFERENCES `sanpham` (`idSP`);

--
-- Constraints for table `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`idTK`) REFERENCES `taikhoan` (`idTK`);

--
-- Constraints for table `lo_nguyenlieu`
--
ALTER TABLE `lo_nguyenlieu`
  ADD CONSTRAINT `lo_nguyenlieu_ibfk_1` FOREIGN KEY (`idNL`) REFERENCES `nguyenlieu` (`idNL`),
  ADD CONSTRAINT `lo_nguyenlieu_ibfk_2` FOREIGN KEY (`idPN`) REFERENCES `phieunhap` (`idPN`);

--
-- Constraints for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `phieunhap_ibfk_1` FOREIGN KEY (`idTK`) REFERENCES `taikhoan` (`idTK`),
  ADD CONSTRAINT `phieunhap_ibfk_2` FOREIGN KEY (`idNCC`) REFERENCES `nhacungcap` (`idNCC`),
  ADD CONSTRAINT `phieunhap_ibfk_3` FOREIGN KEY (`idTT`) REFERENCES `trangthai_pn` (`idTT`);

--
-- Constraints for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`idDM`) REFERENCES `danhmuc` (`idDM`);

--
-- Constraints for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `taikhoan_ibfk_1` FOREIGN KEY (`idNQ`) REFERENCES `nhomquyen` (`idNQ`);

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `cap_nhat_ton_kho_het_han` ON SCHEDULE EVERY 1 DAY STARTS '2025-05-01 08:09:15' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE lo_nguyenlieu
  SET tonkho = 0
  WHERE hsd < CURRENT_DATE
    AND tonkho > 0$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
