-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 15, 2025 at 08:09 AM
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
CREATE DATABASE IF NOT EXISTS `coffeeshop` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `coffeeshop`;

-- --------------------------------------------------------

--
-- Table structure for table `congthuc`
--

CREATE TABLE `congthuc` (
  `idCT` int NOT NULL,
  `mota` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idSP` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ct_congthuc`
--

CREATE TABLE `ct_congthuc` (
  `idCT` int NOT NULL,
  `idNL` int NOT NULL,
  `soluong` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- --------------------------------------------------------

--
-- Table structure for table `danhmuc`
--

CREATE TABLE `danhmuc` (
  `idDM` int NOT NULL,
  `tenDM` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idDMCha` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `danhmuc`
--

INSERT INTO `danhmuc` (`idDM`, `tenDM`, `trangthai`, `idDMCha`) VALUES
(13, 'nước uống', 0, NULL),
(15, 'chè', 1, 13),
(16, 'sodaaaa', 1, 13),
(17, 'cà phê', 1, 13),
(18, 'thức ăn', 1, NULL),
(19, 'nuoc', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `hoadon`
--

CREATE TABLE `hoadon` (
  `idHD` int NOT NULL,
  `ngaytao` date NOT NULL,
  `idTK` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- --------------------------------------------------------

--
-- Table structure for table `nguyenlieu`
--

CREATE TABLE `nguyenlieu` (
  `idNL` int NOT NULL,
  `donvi` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `tenNL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `idNCC` int NOT NULL,
  `tenNCC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `diachi` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `sdt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nhomquyen`
--

CREATE TABLE `nhomquyen` (
  `idNQ` int NOT NULL,
  `tenNQ` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `nhomquyen`
--

INSERT INTO `nhomquyen` (`idNQ`, `tenNQ`) VALUES
(1, 'admin'),
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

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `idSP` int NOT NULL,
  `tenSP` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `giaban` int NOT NULL,
  `hinhanh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idDM` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `idTK` int NOT NULL,
  `tenTK` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `matkhau` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `trangthai` tinyint(1) NOT NULL,
  `idNQ` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `trangthai_pn`
--

CREATE TABLE `trangthai_pn` (
  `idTT` int NOT NULL,
  `tenTT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
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
  MODIFY `idCT` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `danhmuc`
--
ALTER TABLE `danhmuc`
  MODIFY `idDM` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `idHD` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nguyenlieu`
--
ALTER TABLE `nguyenlieu`
  MODIFY `idNL` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `idNCC` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nhomquyen`
--
ALTER TABLE `nhomquyen`
  MODIFY `idNQ` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `idPN` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `idSP` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `idTK` int NOT NULL AUTO_INCREMENT;

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
  ADD CONSTRAINT `ct_hoadon_ibfk_2` FOREIGN KEY (`idHD`) REFERENCES `hoadon` (`idHD`);

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
