-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 21, 2025 at 08:22 AM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vizsgaremek`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `min_age` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `driving_lessons`
--

CREATE TABLE `driving_lessons` (
  `id` int(11) NOT NULL,
  `payment_method` int(10) NOT NULL,
  `is_paid` tinyint(1) NOT NULL,
  `appointment_id` int(10) NOT NULL,
  `status` varchar(100) NOT NULL,
  `km` varchar(100) NOT NULL,
  `helyszin` varchar(100) NOT NULL,
  `felvetel` varchar(100) NOT NULL,
  `letetel` varchar(100) NOT NULL,
  `category` int(10) NOT NULL,
  `paid` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `exam_request`
--

CREATE TABLE `exam_request` (
  `id` int(11) NOT NULL,
  `felado_id` int(10) NOT NULL,
  `cimzett_id` int(10) NOT NULL,
  `status` varchar(100) NOT NULL,
  `mikorra` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `category` varchar(100) NOT NULL,
  `created_at` datetime NOT NULL,
  `tanulo_id` int(10) NOT NULL,
  `is_exam` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payment_method`
--

CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `reserved_appointments`
--

CREATE TABLE `reserved_appointments` (
  `id` int(11) NOT NULL,
  `tanulo_id` int(10) NOT NULL,
  `oktato_id` int(10) NOT NULL,
  `kezdes` datetime NOT NULL,
  `vegzes` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `text` longtext NOT NULL,
  `date` date NOT NULL,
  `rating` float NOT NULL,
  `akire_irta` int(10) NOT NULL,
  `ki_irta` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `saved_cards`
--

CREATE TABLE `saved_cards` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `pan` varchar(16) NOT NULL,
  `name` varchar(100) NOT NULL,
  `cvv_cvc` varchar(3) NOT NULL,
  `date` date NOT NULL,
  `user_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `school`
--

CREATE TABLE `school` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `owner_id` int(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `birth_date` date NOT NULL,
  `gender` varchar(50) NOT NULL,
  `education_qualification` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` int(10) NOT NULL,
  `oktato_id` int(10) NOT NULL,
  `school_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `license_plate` varchar(100) NOT NULL,
  `owner_id` int(10) NOT NULL,
  `fuel` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_types`
--

CREATE TABLE `vehicle_types` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `driving_lessons`
--
ALTER TABLE `driving_lessons`
  ADD PRIMARY KEY (`id`),
  ADD KEY `appointment` (`appointment_id`),
  ADD KEY `payment` (`payment_method`),
  ADD KEY `category` (`category`);

--
-- Indexes for table `exam_request`
--
ALTER TABLE `exam_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `felado` (`felado_id`),
  ADD KEY `cimzett` (`cimzett_id`),
  ADD KEY `tanulo` (`tanulo_id`);

--
-- Indexes for table `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reserved_appointments`
--
ALTER TABLE `reserved_appointments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ki_irta` (`ki_irta`),
  ADD KEY `akire_irta` (`akire_irta`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `saved_cards`
--
ALTER TABLE `saved_cards`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user_id`);

--
-- Indexes for table `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role` (`role`),
  ADD KEY `school` (`school_id`),
  ADD KEY `oktato` (`oktato_id`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `owner` (`owner_id`),
  ADD KEY `type` (`type_id`);

--
-- Indexes for table `vehicle_types`
--
ALTER TABLE `vehicle_types`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driving_lessons`
--
ALTER TABLE `driving_lessons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `exam_request`
--
ALTER TABLE `exam_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment_method`
--
ALTER TABLE `payment_method`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reserved_appointments`
--
ALTER TABLE `reserved_appointments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `saved_cards`
--
ALTER TABLE `saved_cards`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `school`
--
ALTER TABLE `school`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vehicle_types`
--
ALTER TABLE `vehicle_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `driving_lessons`
--
ALTER TABLE `driving_lessons`
  ADD CONSTRAINT `appointment` FOREIGN KEY (`appointment_id`) REFERENCES `reserved_appointments` (`id`),
  ADD CONSTRAINT `category` FOREIGN KEY (`category`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `payment` FOREIGN KEY (`payment_method`) REFERENCES `payment_method` (`id`);

--
-- Constraints for table `exam_request`
--
ALTER TABLE `exam_request`
  ADD CONSTRAINT `cimzett` FOREIGN KEY (`cimzett_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `felado` FOREIGN KEY (`felado_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `tanulo` FOREIGN KEY (`tanulo_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `akire_irta` FOREIGN KEY (`akire_irta`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `ki_irta` FOREIGN KEY (`ki_irta`) REFERENCES `users` (`id`);

--
-- Constraints for table `saved_cards`
--
ALTER TABLE `saved_cards`
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `oktato` FOREIGN KEY (`oktato_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `role` FOREIGN KEY (`role`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`);

--
-- Constraints for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `owner` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `type` FOREIGN KEY (`type_id`) REFERENCES `vehicle_types` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
