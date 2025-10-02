-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 02, 2025 at 07:43 AM
-- Server version: 5.7.24
-- PHP Version: 8.1.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vizsgaremek_4.0`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `asd` (IN `email` VARCHAR(100), IN `password` VARCHAR(100))   BEGIN
	SELECT  
    	u.id AS "userId",
        i.id AS "instructorId",
        s.id AS "studentId"
    FROM users u RIGHT JOIN students s ON
    u.id = s.user_id 
    LEFT JOIN instructor i ON 
    i.user_id = u.id
    WHERE 
    u.email = email AND 
    u.password = password;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllEmail` ()   BEGIN
	SELECT `users`.`email` FROM `users`;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserByEmail` (IN `emailIN` VARCHAR(100))   BEGIN
	SELECT * FROM `users` WHERE emailIN = `users`.`email`;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `login` (IN `emailIN` VARCHAR(100), IN `passwordIN` VARCHAR(100))   BEGIN
	SELECT * FROM users 
    WHERE 
    users.email = emailIN
    AND
    users.password = passwordIN;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `driving_lessons`
--

CREATE TABLE `driving_lessons` (
  `id` int(11) NOT NULL,
  `start_km` int(7) NOT NULL,
  `end_km` int(7) NOT NULL,
  `location` varchar(100) NOT NULL,
  `pick_up_place` varchar(100) NOT NULL,
  `drop_off_place` varchar(100) NOT NULL,
  `lesson_hour_number` int(1) NOT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT '0',
  `payment_method_id` int(10) NOT NULL,
  `hour_id` int(11) NOT NULL,
  `category_id` int(10) NOT NULL,
  `status_id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `driving_license_category`
--

CREATE TABLE `driving_license_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `min_age` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `driving_license_category`
--

INSERT INTO `driving_license_category` (`id`, `name`, `min_age`) VALUES
(1, 'AM', 14),
(2, 'A1', 16),
(3, 'A2', 18),
(4, 'A', 24),
(5, 'B1', 17),
(6, 'B', 17),
(7, 'C1', 18),
(8, 'C', 18),
(9, 'D1', 18),
(10, 'D', 18),
(11, 'BE', 17),
(12, 'C1E', 18),
(13, 'CE', 18),
(14, 'D1E', 18),
(15, 'DE', 18);

-- --------------------------------------------------------

--
-- Table structure for table `education`
--

CREATE TABLE `education` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `education`
--

INSERT INTO `education` (`id`, `name`) VALUES
(1, 'Általános Iskola'),
(2, 'Szakiskola / Szakképző iskola'),
(3, 'Szakközépiskola'),
(4, 'Gimnázium'),
(5, 'Felsőfokú szakképzés'),
(6, 'Egyetem'),
(7, 'Főiskola'),
(8, 'Doktori képzés');

-- --------------------------------------------------------

--
-- Table structure for table `fuel_types`
--

CREATE TABLE `fuel_types` (
  `id` int(11) NOT NULL,
  `name` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fuel_types`
--

INSERT INTO `fuel_types` (`id`, `name`) VALUES
(1, 'asd');

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

CREATE TABLE `instructor` (
  `id` int(11) NOT NULL,
  `user_id` int(10) NOT NULL,
  `school_id` int(10) NOT NULL,
  `promo_text` longtext NOT NULL,
  `vehicle_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `instructor`
--

INSERT INTO `instructor` (`id`, `user_id`, `school_id`, `promo_text`, `vehicle_id`) VALUES
(1, 7, 1, ' bbn nb', 4);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `message_to` int(10) NOT NULL,
  `message_from` int(10) NOT NULL,
  `content` longtext NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `opening_details`
--

CREATE TABLE `opening_details` (
  `id` int(11) NOT NULL,
  `opening_time` time NOT NULL,
  `close_time` time NOT NULL,
  `day` varchar(100) NOT NULL,
  `school_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payment_methods`
--

CREATE TABLE `payment_methods` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `payment_methods`
--

INSERT INTO `payment_methods` (`id`, `name`) VALUES
(1, 'card'),
(2, 'cash'),
(3, 'revolut');

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `sender_id` int(10) NOT NULL,
  `picker_id` int(10) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `requested_date` datetime NOT NULL,
  `msg` longtext NOT NULL,
  `status_id` int(11) NOT NULL,
  `is_exam` tinyint(1) NOT NULL,
  `student_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `reserved_date`
--

CREATE TABLE `reserved_date` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `is_full` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `reserved_hour`
--

CREATE TABLE `reserved_hour` (
  `id` int(11) NOT NULL,
  `start` int(2) NOT NULL,
  `end` int(2) NOT NULL,
  `date_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `author_id` int(10) NOT NULL,
  `text` longtext NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `rating` float NOT NULL,
  `instructor_id` int(10) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_user'),
(2, 'ROLE_student'),
(3, 'ROLE_instructor'),
(4, 'ROLE_school_admin'),
(5, 'ROLE_administrator'),
(6, 'ROLE_school_owner');

-- --------------------------------------------------------

--
-- Table structure for table `school`
--

CREATE TABLE `school` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `country` varchar(100) NOT NULL,
  `town` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `promo_text` longtext NOT NULL,
  `banner_img_path` longtext NOT NULL,
  `owner_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `school`
--

INSERT INTO `school` (`id`, `name`, `email`, `phone`, `country`, `town`, `address`, `promo_text`, `banner_img_path`, `owner_id`) VALUES
(1, 'suli_name1', 'suli_email1', 'suli_tel1', 'suli_country1', 'suli_town1', 'suli_addy1', 'suli_promo1', 'suli_banner1', 1);

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `school_id` int(10) NOT NULL,
  `instructor_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `school_id`, `instructor_id`, `user_id`) VALUES
(1, 1, 2, 4),
(2, 1, 3, 5),
(3, 1, 4, 6);

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
  `education_qualification` varchar(150) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role_id` int(11) DEFAULT '1',
  `pfp_path` longtext,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `school_administrator_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`, `phone`, `birth_date`, `gender`, `education_qualification`, `password`, `role_id`, `pfp_path`, `created_at`, `last_login`, `is_deleted`, `deleted_at`, `school_administrator_id`) VALUES
(1, 'elonev', 'utonev', 'emailcim', '0630303030', '2000-01-01', 'nembinaris', 'ovoda', 'test', NULL, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(2, 'Elonev2', 'Utonev2', 'Email2', 'Teloszam2', '2000-01-02', 'gender2', 'eq2', '6a05b9c2f026724d6f8ee83bdca46d8b5de9752600802900df61b6ae87f50260', NULL, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(3, 'instructor_fn1', 'instructor_ln1', 'instructor_email1', 'instructor_phone1', '2000-01-03', 'gender1', 'instructor__eq1', 'oktato_pass1', 3, 'instructor_pfp1', '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(4, 'testStudent', 'testStudent', 'testStudent', 'student_phone1', '2000-01-01', 'gender', 'student_eq1', 'testStudent', 2, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(5, 'student_fn2', 'student_ln2', 'student_email2', 'student_phone2', '2000-01-01', 'gender', 'student_eq2', 'c0cd37c8058e3378757d0e5e51378721e92a987a296d0e69c365632e0c701d1f', 2, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(6, 'student_fn3', 'student_ln3', 'student_email3', 'student_phone3', '2000-01-01', 'gender', 'student_eq3', 'f17becbf178a5ffe74257a596866dd821083793257db537e5dbbdc9325ec177f', 2, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(7, 'instructor_fn2', 'instructor_ln2', 'instructor_email2', 'instructor_phone2', '2000-01-01', 'gender', 'intructor_eq2', '69ba1bff07ab778655deb4e6b22ca1262c0de90d40ffbb38d45f57c5f28a89b6', 3, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(8, 'instructor_fn3', 'instructor_ln3', 'instructor_email3', 'instructor_phone3', '2000-01-01', 'gender', 'intructor_eq3', '5bd317fb1c75de595d41878198893bdcc3ff7dfc06c8cdd2f209e00e06218521', 3, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(9, 'admin', 'admin', 'admin', 'admin', '2000-01-01', 'admin', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 5, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(10, 'user', 'user', 'user', 'user', '2000-01-01', 'user', 'user', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 1, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(11, 'a', 'a', 'a', 'a', '2006-08-02', 'a', 'a', 'a', 1, NULL, '2025-09-16 15:11:08', NULL, 0, NULL, NULL),
(12, 'a', 'a', 'testasdaaa@gmail.com', 'a', '2006-08-02', 'a', 'a', 'test5.Asd', 1, NULL, '2025-09-22 08:23:18', NULL, 0, NULL, NULL),
(13, 'a', 'a', 'testasdaaaa@gmail.com', 'a', '2006-08-02', 'a', 'a', 'test5.Asd', NULL, NULL, '2025-09-22 08:28:03', NULL, 0, NULL, NULL),
(14, 'a', 'a', 'testasdaaaa@gmail.com', 'a', '2006-08-02', 'a', 'a', 'test5.Asd', 1, NULL, '2025-09-22 08:28:55', NULL, 0, NULL, NULL),
(15, 'a', 'a', 'testasdaaaa@gmail.com', 'a', '2006-08-02', 'a', 'a', 'test5.Asd', 1, NULL, '2025-09-22 08:29:12', NULL, 0, NULL, NULL),
(16, 'a', 'a', 'testasdaaaa@gmail.com', 'a', '2006-08-02', 'a', 'a', 'test5.Asd', 1, NULL, '2025-09-22 08:29:14', NULL, 0, NULL, NULL),
(17, 'a', 'a', 'testasdaaaa@gmail.com', 'a', '2006-08-02', 'a', 'a', 'test5.Asd', 1, NULL, '2025-09-22 08:29:51', NULL, 0, NULL, NULL),
(18, 'a', 'a', 'testasdaaaa@gmail.com', 'a', '2006-08-02', 'a', 'a', 'test5.Asd', 1, NULL, '2025-09-22 08:31:21', NULL, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `license_plate` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type_id` int(10) NOT NULL,
  `fuel_type_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`id`, `license_plate`, `name`, `type_id`, `fuel_type_id`) VALUES
(3, 'AAAA000', 'car_name', 1, 1),
(4, 'aaa000', 'name', 1, 1),
(5, 'ABC123', 'Suzuki Ignis', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_types`
--

CREATE TABLE `vehicle_types` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vehicle_types`
--

INSERT INTO `vehicle_types` (`id`, `name`) VALUES
(1, 'Auto'),
(2, 'Robogó'),
(3, 'Nagy motor'),
(4, 'Busz'),
(5, 'Kamion');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `driving_lessons`
--
ALTER TABLE `driving_lessons`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category` (`category_id`),
  ADD KEY `payment` (`payment_method_id`),
  ADD KEY `hour` (`hour_id`),
  ADD KEY `status` (`status_id`),
  ADD KEY `d_instructor` (`instructor_id`),
  ADD KEY `d_students` (`student_id`);

--
-- Indexes for table `driving_license_category`
--
ALTER TABLE `driving_license_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `education`
--
ALTER TABLE `education`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fuel_types`
--
ALTER TABLE `fuel_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `instructor` (`user_id`),
  ADD KEY `school` (`school_id`),
  ADD KEY `vehicle` (`vehicle_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `message_to` (`message_to`),
  ADD KEY `message_from` (`message_from`);

--
-- Indexes for table `opening_details`
--
ALTER TABLE `opening_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `school_2` (`school_id`);

--
-- Indexes for table `payment_methods`
--
ALTER TABLE `payment_methods`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender` (`sender_id`),
  ADD KEY `picker_2` (`picker_id`),
  ADD KEY `student_2` (`student_id`),
  ADD KEY `status_2` (`status_id`);

--
-- Indexes for table `reserved_date`
--
ALTER TABLE `reserved_date`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reserved_hour`
--
ALTER TABLE `reserved_hour`
  ADD PRIMARY KEY (`id`),
  ADD KEY `date` (`date_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `author` (`author_id`),
  ADD KEY `about_2` (`instructor_id`),
  ADD KEY `about_school` (`school_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`id`),
  ADD KEY `admin` (`owner_id`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user_id`),
  ADD KEY `instructor_2_id` (`instructor_id`),
  ADD KEY `school_3` (`school_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role` (`role_id`),
  ADD KEY `school_admin` (`school_administrator_id`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type` (`type_id`),
  ADD KEY `fuel` (`fuel_type_id`);

--
-- Indexes for table `vehicle_types`
--
ALTER TABLE `vehicle_types`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `driving_lessons`
--
ALTER TABLE `driving_lessons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driving_license_category`
--
ALTER TABLE `driving_license_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `education`
--
ALTER TABLE `education`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `fuel_types`
--
ALTER TABLE `fuel_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `instructor`
--
ALTER TABLE `instructor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `opening_details`
--
ALTER TABLE `opening_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment_methods`
--
ALTER TABLE `payment_methods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reserved_date`
--
ALTER TABLE `reserved_date`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reserved_hour`
--
ALTER TABLE `reserved_hour`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `school`
--
ALTER TABLE `school`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `vehicle_types`
--
ALTER TABLE `vehicle_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `driving_lessons`
--
ALTER TABLE `driving_lessons`
  ADD CONSTRAINT `category` FOREIGN KEY (`category_id`) REFERENCES `driving_license_category` (`id`),
  ADD CONSTRAINT `d_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `d_students` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
  ADD CONSTRAINT `hour` FOREIGN KEY (`hour_id`) REFERENCES `reserved_hour` (`id`),
  ADD CONSTRAINT `payment` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_methods` (`id`),
  ADD CONSTRAINT `status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`);

--
-- Constraints for table `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `asd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `vehicle` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_from` FOREIGN KEY (`message_from`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `message_to` FOREIGN KEY (`message_to`) REFERENCES `users` (`id`);

--
-- Constraints for table `opening_details`
--
ALTER TABLE `opening_details`
  ADD CONSTRAINT `school_2` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`);

--
-- Constraints for table `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `picker` FOREIGN KEY (`picker_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `picker_2` FOREIGN KEY (`picker_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `sender` FOREIGN KEY (`sender_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `status_2` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  ADD CONSTRAINT `student_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`);

--
-- Constraints for table `reserved_hour`
--
ALTER TABLE `reserved_hour`
  ADD CONSTRAINT `date` FOREIGN KEY (`date_id`) REFERENCES `reserved_date` (`id`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `about_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `about_school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `students` (`id`);

--
-- Constraints for table `school`
--
ALTER TABLE `school`
  ADD CONSTRAINT `admin` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `school_3` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `school_admin` FOREIGN KEY (`school_administrator_id`) REFERENCES `school` (`id`);

--
-- Constraints for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `fuel` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_types` (`id`),
  ADD CONSTRAINT `type` FOREIGN KEY (`type_id`) REFERENCES `vehicle_types` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
