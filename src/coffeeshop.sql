-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 03, 2025 at 02:38 AM
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

    DECLARE done_sp INT DEFAULT FALSE;
    DECLARE cur_sp CURSOR FOR 
        SELECT idSP, soluong FROM ct_hoadon WHERE idHD = input_idHD;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_sp = TRUE;

    OPEN cur_sp;
    read_sp: LOOP
        FETCH cur_sp INTO sp_id, sp_soLuong;
        IF done_sp THEN
            LEAVE read_sp;
        END IF;

        -- Lấy công thức cho sản phẩm
        SELECT idCT INTO ct_id FROM congthuc WHERE idSP = sp_id LIMIT 1;

        -- Duyệt qua các nguyên liệu của công thức
        BEGIN
            DECLARE done_nl INT DEFAULT FALSE;
            DECLARE cur_nl CURSOR FOR 
                SELECT idNL, soluong FROM ct_congthuc WHERE idCT = ct_id;
            DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_nl = TRUE;

            OPEN cur_nl;
            read_nl: LOOP
                FETCH cur_nl INTO nl_id, nl_soLuongCan;
                IF done_nl THEN
                    LEAVE read_nl;
                END IF;

                SET nl_soLuongCanUpdate = nl_soLuongCan * sp_soLuong;

                -- Duyệt qua các lô nguyên liệu
                BEGIN
                    DECLARE done_lo INT DEFAULT FALSE;
                    DECLARE cur_lo CURSOR FOR 
                        SELECT idPN, tonkho FROM lo_nguyenlieu 
                        WHERE idNL = nl_id AND tonkho > 0 
                        ORDER BY hsd ASC;
                    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done_lo = TRUE;

                    OPEN cur_lo;
                    read_lo: LOOP
                        FETCH cur_lo INTO lo_id, lo_soLuongTon;
                        IF done_lo OR nl_soLuongCanUpdate <= 0 THEN
                            LEAVE read_lo;
                        END IF;

                        IF lo_soLuongTon >= nl_soLuongCanUpdate THEN
                            UPDATE lo_nguyenlieu
                            SET tonkho = tonkho - nl_soLuongCanUpdate
                            WHERE idPN = lo_id AND idNL = nl_id;
                            SET nl_soLuongCanUpdate = 0;
                            LEAVE read_lo;
                        ELSE
                            SET nl_soLuongCanUpdate = nl_soLuongCanUpdate - lo_soLuongTon;
                            UPDATE lo_nguyenlieu
                            SET tonkho = 0
                            WHERE idPN = lo_id AND idNL = nl_id;
                        END IF;
                    END LOOP;
                    CLOSE cur_lo;
                END;
            END LOOP;
            CLOSE cur_nl;
        END;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `congthuc`
--

INSERT INTO `congthuc` (`idCT`, `mota`, `idSP`) VALUES
(4, 'Pha cà phê bột với nước nóng, thêm sữa đặc, khuấy đều, cho đá vào và phục vụ.', 4),
(5, 'Pha cà phê bột với nước nóng bằng máy espresso, phục vụ ngay khi nóng', 5),
(6, 'Pha cà phê, thêm sữa tươi đã làm nóng, phủ bọt sữa lên trên, phục vụ ngay', 6),
(7, 'Đổ syrup chanh dây vào ly, thêm đá, tiếp theo là soda, khuấy nhẹ và phục vụ.', 14),
(8, 'Xay nhuyễn xoài với sữa tươi và đá, đổ ra ly và phục vụ ngay.', 9),
(9, 'Xay nhuyễn dâu tây với sữa tươi và đá, đổ ra ly và phục vụ ngay.', 10),
(10, 'Đổ syrup Blue Curacao vào ly, thêm đá, tiếp theo là soda, khuấy nhẹ và phục vụ.', 13),
(11, 'Pha trà, để nguội, thêm sữa tươi và trân châu, cho đá vào và phục vụ.', 16),
(12, 'Nghiền bánh quy làm đế, trộn cream cheese với trứng, đường, và cacao, đổ lên lớp đế, nướng ở 160°C trong 40 phút, để nguội và phục vụ.', 18),
(13, 'Lót bánh ladyfinger làm đế, trộn cream cheese, cacao, trứng, đường, và kem tươi thành hỗn hợp mịn, đổ lên đế, để lạnh trong tủ 2-3 giờ, rồi phục vụ.', 19),
(14, 'Pha cà phê, nhúng bánh ladyfinger vào cà phê, xếp lớp. Trộn cream cheese, kem tươi, và đường thành kem, phết lên bánh, rắc cacao lên trên, để lạnh 4 giờ trước khi phục vụ.', 20),
(15, 'Pha trà với nước nóng, để nguội, thêm sữa tươi và đường, khuấy đều, cho đá vào và phục vụ ngay.', 17);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ct_congthuc`
--

INSERT INTO `ct_congthuc` (`idCT`, `idNL`, `soluong`) VALUES
(4, 3, 20),
(4, 4, 30),
(5, 3, 18),
(6, 3, 15),
(6, 5, 100),
(7, 9, 150),
(7, 24, 100),
(8, 5, 100),
(8, 14, 1),
(9, 5, 80),
(9, 15, 120),
(10, 11, 20),
(10, 24, 180),
(11, 5, 120),
(11, 6, 10),
(11, 12, 50),
(12, 8, 80),
(12, 17, 100),
(12, 21, 20),
(12, 22, 200),
(12, 23, 2),
(13, 8, 60),
(13, 20, 4),
(13, 21, 50),
(13, 22, 150),
(13, 23, 1),
(14, 3, 10),
(14, 8, 50),
(14, 20, 120),
(14, 21, 20),
(14, 22, 180),
(15, 5, 120),
(15, 6, 10),
(15, 8, 20);

-- --------------------------------------------------------

--
-- Table structure for table `ct_hoadon`
--

CREATE TABLE `ct_hoadon` (
  `idSP` int NOT NULL,
  `idHD` int NOT NULL,
  `soluong` int NOT NULL,
  `gialucdat` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ct_hoadon`
--

INSERT INTO `ct_hoadon` (`idSP`, `idHD`, `soluong`, `gialucdat`) VALUES
(4, 15, 1, 25000),
(4, 16, 1, 25000),
(4, 17, 1, 25000),
(5, 16, 1, 35000),
(5, 17, 2, 35000);

-- --------------------------------------------------------

--
-- Table structure for table `danhmuc`
--

CREATE TABLE `danhmuc` (
  `idDM` int NOT NULL,
  `tenDM` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idDMCha` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `danhmuc`
--

INSERT INTO `danhmuc` (`idDM`, `tenDM`, `trangthai`, `idDMCha`) VALUES
(22, 'Nước uống', 1, NULL),
(23, 'Cà phê', 1, 22),
(24, 'Sinh tố', 1, 22),
(25, 'Soda', 1, 22),
(26, 'Trà sữa', 1, 22),
(27, 'Bánh và đồ ăn nhẹ', 1, NULL),
(28, 'Bánh ngọt', 1, 27);

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

CREATE TABLE `hoadon` (
  `idHD` int NOT NULL,
  `ngaytao` date NOT NULL,
  `idTK` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `hoadon`
--

INSERT INTO `hoadon` (`idHD`, `ngaytao`, `idTK`) VALUES
(15, '2025-05-03', 3),
(16, '2025-05-03', 3),
(17, '2025-05-03', 3);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `lo_nguyenlieu`
--

INSERT INTO `lo_nguyenlieu` (`idNL`, `idPN`, `soluongnhap`, `tonkho`, `dongia`, `hsd`) VALUES
(3, 4, 10000, 10000, 10000, '2025-05-14'),
(3, 6, 500, 386, 10000, '2025-05-07'),
(3, 7, 200, 200, 10000, '2025-05-14'),
(3, 8, 100, 100, 10000, '2025-05-08'),
(4, 3, 1000, 910, 2000, '2025-05-13'),
(5, 3, 5000, 5000, 2000, '2025-05-07'),
(8, 5, 5000, 0, 3000, '2025-05-12'),
(9, 5, 1000, 0, 3500, '2025-05-31'),
(10, 5, 1000, 0, 3500, '2025-05-31'),
(11, 5, 1000, 0, 3500, '2025-05-31'),
(13, 2, 50, 50, 15000, '2025-05-17'),
(14, 2, 30, 30, 5000, '2025-05-17'),
(15, 2, 5000, 5000, 2000, '2025-05-17'),
(18, 3, 1000, -30, 5000, '2025-05-07'),
(21, 4, 1000, 1000, 10000, '2025-05-14'),
(22, 3, 1000, -30, 5000, '2025-05-22');

-- --------------------------------------------------------

--
-- Table structure for table `nguyenlieu`
--

CREATE TABLE `nguyenlieu` (
  `idNL` int NOT NULL,
  `donvi` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tenNL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `nguyenlieu`
--

INSERT INTO `nguyenlieu` (`idNL`, `donvi`, `tenNL`) VALUES
(3, 'gram', 'Cà phê bột'),
(4, 'ml', 'Sữa đặc'),
(5, 'ml', 'Sữa tươi'),
(6, 'gram', 'Trà'),
(7, 'gram', 'Bột sữa'),
(8, 'gram', 'Đường trắng'),
(9, 'ml', 'Syrup chanh dây'),
(10, 'ml', 'Syrup việt quất'),
(11, 'ml', 'Syrup Blue Curacao'),
(12, 'gram', 'Trân châu'),
(13, 'quả', 'Bơ'),
(14, 'trái', 'Xoài'),
(15, 'gram', 'Dâu tây'),
(16, 'gram', 'Việt quất'),
(17, 'gram', 'Bánh quy nghiền'),
(18, 'gram', 'Bơ lạt'),
(19, 'gram', 'Socola'),
(20, 'cái', 'Bánh ladyfinger'),
(21, 'gram', 'Bột cacao'),
(22, 'gram', 'Cream cheese'),
(23, 'quả', 'Trứng gà'),
(24, 'ml', 'Soda');

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `idNCC` int NOT NULL,
  `tenNCC` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `diachi` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `sdt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`idNCC`, `tenNCC`, `diachi`, `sdt`, `email`, `trangthai`) VALUES
(3, 'Trái Cây Tươi GreenFarm', ' 56 Phan Đình Phùng, Phú Nhuận, TP.HCM', '0902789123', 'lienhe@greenfarm.vn', 1),
(4, 'Sữa Tươi Đà Lạt Milk', '12 Trần Hưng Đạo, TP. Đà Lạt, Lâm Đồng', '0263382222', 'contact@dalatmilk.vn', 1),
(5, 'Cà Phê Nguyên Chất Sài Gòn', '123 Nguyễn Thị Minh Khai, Quận 1, TP.HCM', '0903456789', 'lienhe@caphesaigon.vn', 1),
(6, 'Đường Mía Biên Hòa', ' KCN Biên Hòa 1, Đồng Nai', '0251389998', 'info@duongbienhoa.com', 1),
(7, 'Nguyên Liệu Bánh Ánh Dương', '210 Cách Mạng Tháng 8, Quận 10, TP.HCM', '0934563211', 'contact@anhduongbakery.vn', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `idNQ` int NOT NULL,
  `tenNQ` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `phieunhap`
--

INSERT INTO `phieunhap` (`idPN`, `ngaytao`, `ngaycapnhat`, `idTK`, `idNCC`, `idTT`) VALUES
(2, '2025-05-02', '2025-05-02', 1, 3, 2),
(3, '2025-05-02', '2025-05-03', 1, 3, 2),
(4, '2025-05-02', '2025-05-03', 1, 3, 2),
(5, '2025-05-02', '2025-05-02', 1, 6, 1),
(6, '2025-05-03', '2025-05-03', 1, 5, 2),
(7, '2025-05-03', '2025-05-03', 1, 5, 2),
(8, '2025-05-03', '2025-05-03', 1, 5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `idSP` int NOT NULL,
  `tenSP` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `giaban` int NOT NULL,
  `hinhanh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idDM` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`idSP`, `tenSP`, `giaban`, `hinhanh`, `trangthai`, `idDM`) VALUES
(4, 'Cà phê đen đá	', 25000, '7c31ffcf-f038-4580-8eda-1af064cd8c46.png', 1, 23),
(5, 'Espresso nóng', 35000, 'f05bbdc9-fbf0-4847-9cfe-7c95efc8ba55.jpg', 1, 23),
(6, 'Cappuchino', 45000, '52ef6bed-299d-47cd-8236-ed9ca7c6c854.jpg', 1, 23),
(7, 'Cà phê sữa đá', 28000, '9f8a4ec9-ea38-4582-9df1-b79f091e930d.jpg', 0, 23),
(8, 'Sinh tố bơ', 40000, '40541bdf-db1c-4751-b611-89bc34b2073e.png', 0, 24),
(9, 'Sinh tố xoài	', 38000, 'a36a6baf-1836-4a6a-b3a3-055b1b9ad282.jpg', 1, 24),
(10, 'Sinh tố dâu', 38000, 'b50691b0-0bed-4e66-a4a2-11f574daa6f8.jpg', 1, 24),
(11, 'Sinh tố việt quất', 42000, '40b448ed-c491-4b58-a585-35615c0d8749.png', 0, 24),
(12, 'Soda việt quất', 36000, 'a0e6f360-173a-46c7-820e-f49ab453f879.jpg', 0, 25),
(13, 'Soda Blue Ocean', 37000, '1921e894-0f07-4da4-813c-e3cae13b5967.jpg', 1, 25),
(14, 'Soda chanh dây', 35000, '6790879d-252e-457d-bc14-e2e26874729b.png', 1, 25),
(15, 'Trà sữa matcha	', 38000, '4fe71904-ccb2-47f7-a50c-1d1ce5d4607c.jpg', 0, 26),
(16, 'Trà sữa trân châu đường đen', 35000, '629393e4-99ec-474c-a2e0-77ae346131e6.jpg', 1, 26),
(17, 'Trà sữa truyền thống', 32000, 'addf6ffc-994d-4bbc-83d4-e4ec80f17b77.png', 0, 26),
(18, 'Bánh phô mai nướng', 35000, '1301bb92-4acd-4995-8ac7-cc004045f167.jpg', 0, 28),
(19, 'Bánh mousse socola', 30000, '9fbff395-8199-45ef-b717-1b40fabe655e.jpg', 0, 28),
(20, 'Tiramisu', 32000, 'a17511a5-7b8a-426c-8109-b15f72c5a842.jpg', 0, 28);

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `idTK` int NOT NULL,
  `tenTK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `matkhau` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idNQ` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`idTK`, `tenTK`, `matkhau`, `email`, `trangthai`, `idNQ`) VALUES
(1, 'thaovy3724', '$2a$10$S9hM1lWCViB1IAeLqeGd9uh2aOtm1l/OG7atMylHQeInw2p6ECxgy', 'thaovy3724@gmail.com', 1, 1),
(3, 'quynhhuong123', '$2a$10$UcftXmVBP2QDsg3hYXZN6u2tNxg6dx5ZwEGSV/775TI81nxc.PGZG', 'quynhhuong123@gmail.com', 0, 2),
(4, 'nhuy123', '$2a$10$bhcPYypL4GEfPxcPuBojXeCNoN59f/zAxuTE48yRoSFO0OXoFMWbe', 'nhuy123@gmail.com', 1, 1),
(6, 'xuannhi123', '$2a$10$vUQjLiVF.PSfkw8EsoJRQOGcJKfL9WYR28KKcqTV2g0SQugpnhVh6', 'xuannhi123@gmail.com', 0, 2),
(7, 'thaovy3442', '$2a$10$.twE6L93g6.V1PUo7ue6p.QHbeEHzJ/M7Kk7QXRsTJ3d1GRxjNixe', 'thaovy3442@gmail.com', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `trangthai_pn`
--

CREATE TABLE `trangthai_pn` (
  `idTT` int NOT NULL,
  `tenTT` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `trangthai_pn`
--

INSERT INTO `trangthai_pn` (`idTT`, `tenTT`) VALUES
(1, 'Chưa hoàn tất'),
(2, 'Hoàn tất');

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
  MODIFY `idCT` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `danhmuc`
--
ALTER TABLE `danhmuc`
  MODIFY `idDM` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `idHD` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `nguyenlieu`
--
ALTER TABLE `nguyenlieu`
  MODIFY `idNL` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `idNCC` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  MODIFY `idNQ` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `idPN` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `idSP` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `idTK` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

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
