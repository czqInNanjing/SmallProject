-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Nov 09, 2016 at 10:58 PM
-- Server version: 5.5.42
-- PHP Version: 5.6.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `railway`
--

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE `route` (
  `route_id` varchar(15) COLLATE utf8_bin NOT NULL,
  `station_id` int(11) NOT NULL,
  `route_number` int(11) NOT NULL,
  `date` date NOT NULL,
  `start_station` varchar(20) COLLATE utf8_bin NOT NULL,
  `end_station` varchar(20) COLLATE utf8_bin NOT NULL,
  `interval` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `r_order`
--

CREATE TABLE `r_order` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `bill` int(11) NOT NULL,
  `start_station` varchar(20) COLLATE utf8_bin NOT NULL,
  `end_station` varchar(20) COLLATE utf8_bin NOT NULL,
  `ticket_num` int(11) NOT NULL,
  `time` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `seat`
--

CREATE TABLE `seat` (
  `route_id` varchar(15) COLLATE utf8_bin NOT NULL,
  `station_id` int(11) NOT NULL,
  `route_number` int(11) NOT NULL,
  `date` date NOT NULL,
  `type` enum('商务座','一等座','二等座','无座') COLLATE utf8_bin NOT NULL DEFAULT '无座',
  `carriage` int(11) NOT NULL DEFAULT '0',
  `row` int(11) NOT NULL DEFAULT '0',
  `number` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'G',
  `cost` int(11) NOT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(20) COLLATE utf8_bin NOT NULL,
  `name` varchar(20) COLLATE utf8_bin NOT NULL,
  `password` varchar(20) COLLATE utf8_bin NOT NULL,
  `id_num` char(22) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`route_id`,`station_id`,`route_number`,`date`);

--
-- Indexes for table `r_order`
--
ALTER TABLE `r_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `seat`
--
ALTER TABLE `seat`
  ADD PRIMARY KEY (`route_id`,`station_id`,`route_number`,`date`,`carriage`,`row`,`number`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_email_uindex` (`email`),
  ADD UNIQUE KEY `user_id_num_uindex` (`id_num`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `r_order`
--
ALTER TABLE `r_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
