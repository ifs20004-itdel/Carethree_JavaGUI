-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 25, 2022 at 07:21 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `carethree`
--

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `userID` varchar(100) NOT NULL,
  `productID` varchar(100) NOT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`userID`, `productID`, `qty`) VALUES
('U8652', 'S8412', 1),
('U8652', 'D5470', 1),
('U8652', 'D5635', 1),
('U8652', 'S4202', 1);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `type`, `brand`, `price`, `stock`) VALUES
('D5470', 'Drink', 'Mogu - Mogu', 75200, 56),
('D5635', 'Drink', 'Sprite', 5000, 27),
('D6286', 'Drink', 'Coca Cola', 3500, 751),
('D7550', 'Drink', 'Fanta', 7500, 62),
('F2067', 'Food', 'Xylitol', 1000, 548),
('F4464', 'Food', 'Menta', 5000, 125),
('F6403', 'Food', 'Chitato', 1500, 500),
('F7996', 'Food', 'Pocky', 8000, 300),
('S1995', 'Sanitary', 'Mami Poko', 80000, 67),
('S4202', 'Sanitary', 'Paseo', 18000, 29),
('S8412', 'Sanitary', 'Antis', 12500, 51);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL DEFAULT 'user',
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phoneNum` varchar(100) NOT NULL,
  `gender` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `role`, `username`, `password`, `email`, `phoneNum`, `gender`) VALUES
('U0000', 'admin', 'admin', 'admin', 'admin1234', 'admin@gmail.com', '+620000000000000', 'Male'),
('U8652', 'Budi Budiman', 'user', 'booodii', 'user1234', 'boodi@gmail.com', '+6248484454', 'Male');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
