-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 22, 2025 at 09:24 AM
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
-- Database: `vizsgaremek_2.0`
--

-- --------------------------------------------------------

--
-- Table structure for table `driving_lessons`
--

CREATE TABLE `driving_lessons` (
  `id` int(11) NOT NULL,
  `status` varchar(100) NOT NULL,
  `start_km` int(7) NOT NULL,
  `location` varchar(100) NOT NULL,
  `pick_up_place` varchar(100) NOT NULL,
  `drop_off_place` varchar(100) NOT NULL,
  `category_id` int(10) NOT NULL,
  `is_paid` tinyint(1) NOT NULL,
  `payment_method` int(10) NOT NULL,
  `appointment_id` int(10) NOT NULL,
  `end_km` int(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `driving_lesson_category`
--

CREATE TABLE `driving_lesson_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `min_age` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

CREATE TABLE `instructor` (
  `id` int(11) NOT NULL,
  `user_id` int(10) NOT NULL,
  `promo_text` longtext NOT NULL,
  `school_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `sender_id` int(10) NOT NULL,
  `picker_id` int(10) NOT NULL,
  `created_at` datetime NOT NULL,
  `requested_date` datetime NOT NULL,
  `msg` longtext NOT NULL,
  `status` varchar(100) NOT NULL,
  `is_exam` tinyint(1) NOT NULL,
  `student_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `reserved_appointment`
--

CREATE TABLE `reserved_appointment` (
  `id` int(11) NOT NULL,
  `instructor_id` int(10) NOT NULL,
  `student_id` int(10) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `author_id` int(10) NOT NULL,
  `text` longtext NOT NULL,
  `date` datetime NOT NULL,
  `rating` float NOT NULL,
  `about_id` int(10) NOT NULL
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
  `user_id` int(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `pan` varchar(16) NOT NULL,
  `name` varchar(100) NOT NULL,
  `cvv_cvc` varchar(3) NOT NULL,
  `exp_date` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `banner_img_path` varchar(100) NOT NULL,
  `opening_details_id` int(10) NOT NULL,
  `administrator_id` int(10) NOT NULL
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
  `role_id` int(11) NOT NULL,
  `pfp_path` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `license_plate` varchar(10) NOT NULL,
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
-- Indexes for table `driving_lessons`
--
ALTER TABLE `driving_lessons`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category` (`category_id`),
  ADD KEY `payment` (`payment_method`),
  ADD KEY `appointment` (`appointment_id`);

--
-- Indexes for table `driving_lesson_category`
--
ALTER TABLE `driving_lesson_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `instructor` (`user_id`),
  ADD KEY `school` (`school_id`);

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
  ADD KEY `student_2` (`student_id`);

--
-- Indexes for table `reserved_appointment`
--
ALTER TABLE `reserved_appointment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student` (`student_id`),
  ADD KEY `instructor_id` (`instructor_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `author` (`author_id`),
  ADD KEY `about_2` (`about_id`);

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
  ADD KEY `sc_user` (`user_id`);

--
-- Indexes for table `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`id`),
  ADD KEY `admin` (`administrator_id`);

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
  ADD KEY `role` (`role_id`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type` (`type_id`),
  ADD KEY `owner` (`owner_id`);

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
-- AUTO_INCREMENT for table `driving_lesson_category`
--
ALTER TABLE `driving_lesson_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `instructor`
--
ALTER TABLE `instructor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reserved_appointment`
--
ALTER TABLE `reserved_appointment`
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
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
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
  ADD CONSTRAINT `appointment` FOREIGN KEY (`appointment_id`) REFERENCES `reserved_appointment` (`id`),
  ADD CONSTRAINT `category` FOREIGN KEY (`category_id`) REFERENCES `driving_lesson_category` (`id`),
  ADD CONSTRAINT `payment` FOREIGN KEY (`payment_method`) REFERENCES `payment_methods` (`id`);

--
-- Constraints for table `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `instructor` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`);

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
  ADD CONSTRAINT `student_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`);

--
-- Constraints for table `reserved_appointment`
--
ALTER TABLE `reserved_appointment`
  ADD CONSTRAINT `instructor_id` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `student` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `about_1` FOREIGN KEY (`about_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `about_2` FOREIGN KEY (`about_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `students` (`id`);

--
-- Constraints for table `saved_cards`
--
ALTER TABLE `saved_cards`
  ADD CONSTRAINT `sc_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `school`
--
ALTER TABLE `school`
  ADD CONSTRAINT `admin` FOREIGN KEY (`administrator_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `instructor_2_id` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `school_3` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Constraints for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `owner` FOREIGN KEY (`owner_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `type` FOREIGN KEY (`type_id`) REFERENCES `vehicle_types` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
