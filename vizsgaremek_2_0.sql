-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Gép: localhost:3306
-- Létrehozás ideje: 2025. Sze 07. 16:49
-- Kiszolgáló verziója: 5.7.24
-- PHP verzió: 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `vizsgaremek`
--

DELIMITER $$
--
-- Eljárások
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddInstructor` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `AddReview` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `AddSchool` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `AddVehicle` (IN `license_plate` VARCHAR(7), IN `fuel` VARCHAR(20), IN `name` VARCHAR(50), IN `owner_id` INT(11), IN `type_id` INT(11))   BEGIN
	INSERT INTO `vehicle`(
        `vehicle`.`license_plate`,
    	`vehicle`.`fuel`,
    	`vehicle`.`name`,
    	`vehicle`.`owner_id`,
    	`vehicle`.`type_id`)
     VALUES(
     	 license_plate,
         fuel,
         name,
         owner_id,
         type_id
        );
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `FriendRequest` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetInstructorsBySchoolID` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetReviewByAboutID` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetSchoolByID` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetStudentsByInstructorID` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetStudentsBySchoolID` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Login` (IN `emailIN` VARCHAR(100), IN `passwordIN` VARCHAR(64))   BEGIN
	SELECT * FROM `users` 
    WHERE 
    `users`.`email` = emailIN 
    AND 
    `users`.`password` = passwordIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `RegisterUser` (IN `firstNameIN` VARCHAR(100), IN `lastNameIN` VARCHAR(100), IN `emailIN` VARCHAR(100), IN `phoneIN` VARCHAR(50), IN `passwordIN` VARCHAR(64), IN `birthDateIN` DATE, IN `genderIN` VARCHAR(50), IN `educationQualificationIN` VARCHAR(150), OUT `result` VARCHAR(100))   BEGIN
	INSERT INTO `users`(
        `users`.`first_name`,
        `users`.`last_name`,
        `users`.`email`,
        `users`.`phone`,
        `users`.`password`,
        `users`.`birth_date`,
        `users`.`gender`,
        `users`.`education_qualification`)
     VALUES(
     	 firstNameIN,
         lastNameIN,
         emailIN,
         phoneIN,
         SHA2(passwordIN,256),
         birthDateIN,
         genderIN,
         educationQualificationIN
        );
        
	SET result = "successfull registration";
            
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `SendMessage` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateInstructor` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateSchool` ()   BEGIN
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateUser` ()   BEGIN
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_lessons`
--

CREATE TABLE `driving_lessons` (
  `id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `start_km` int(7) NOT NULL,
  `location` varchar(100) NOT NULL,
  `pick_up_place` varchar(100) NOT NULL,
  `drop_off_place` varchar(100) NOT NULL,
  `category_id` int(10) NOT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT '0',
  `payment_method` int(10) NOT NULL,
  `end_km` int(7) NOT NULL,
  `hour_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_license_category`
--

CREATE TABLE `driving_license_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `min_age` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `driving_license_category`
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
-- Tábla szerkezet ehhez a táblához `fuel_types`
--

CREATE TABLE `fuel_types` (
  `id` int(11) NOT NULL,
  `name` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `fuel_types`
--

INSERT INTO `fuel_types` (`id`, `name`) VALUES
(1, 'asd');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `instructor`
--

CREATE TABLE `instructor` (
  `id` int(11) NOT NULL,
  `user_id` int(10) NOT NULL,
  `school_id` int(10) NOT NULL,
  `promo_text` longtext NOT NULL,
  `vehicle_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `instructor`
--

INSERT INTO `instructor` (`id`, `user_id`, `school_id`, `promo_text`, `vehicle_id`) VALUES
(1, 7, 1, ' bbn nb', 4);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `message`
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
-- Tábla szerkezet ehhez a táblához `opening_details`
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
-- Tábla szerkezet ehhez a táblához `payment_methods`
--

CREATE TABLE `payment_methods` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `payment_methods`
--

INSERT INTO `payment_methods` (`id`, `name`) VALUES
(1, 'card'),
(2, 'cash'),
(3, 'revolut');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `request`
--

CREATE TABLE `request` (
  `id` int(11) NOT NULL,
  `sender_id` int(10) NOT NULL,
  `picker_id` int(10) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `requested_date` datetime NOT NULL,
  `msg` longtext NOT NULL,
  `status` varchar(100) NOT NULL,
  `is_exam` tinyint(1) NOT NULL,
  `student_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_date`
--

CREATE TABLE `reserved_date` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `is_full` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_hour`
--

CREATE TABLE `reserved_hour` (
  `id` int(11) NOT NULL,
  `start` int(2) NOT NULL,
  `end` int(2) NOT NULL,
  `date_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `author_id` int(10) NOT NULL,
  `text` longtext NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `rating` float NOT NULL,
  `about_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'user'),
(2, 'student'),
(3, 'instructor'),
(4, 'school_admin'),
(5, 'administrator');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `school`
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
  `administrator_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `school`
--

INSERT INTO `school` (`id`, `name`, `email`, `phone`, `country`, `town`, `address`, `promo_text`, `banner_img_path`, `administrator_id`) VALUES
(1, 'suli_name1', 'suli_email1', 'suli_tel1', 'suli_country1', 'suli_town1', 'suli_addy1', 'suli_promo1', 'suli_banner1', 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `school_id` int(10) NOT NULL,
  `instructor_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `students`
--

INSERT INTO `students` (`id`, `school_id`, `instructor_id`, `user_id`) VALUES
(1, 1, 2, 4),
(2, 1, 3, 5),
(3, 1, 4, 6);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `users`
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
  `pfp_path` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `users`
--

INSERT INTO `users` (`id`, `first_name`, `last_name`, `email`, `phone`, `birth_date`, `gender`, `education_qualification`, `password`, `role_id`, `pfp_path`) VALUES
(1, 'elonev', 'utonev', 'emailcim', '0630303030', '2000-01-01', 'nembinaris', 'ovoda', 'test', NULL, NULL),
(2, 'Elonev2', 'Utonev2', 'Email2', 'Teloszam2', '2000-01-02', 'gender2', 'eq2', '6a05b9c2f026724d6f8ee83bdca46d8b5de9752600802900df61b6ae87f50260', NULL, NULL),
(3, 'instructor_fn1', 'instructor_ln1', 'instructor_email1', 'instructor_phone1', '2000-01-03', 'gender1', 'instructor__eq1', 'oktato_pass1', 3, 'instructor_pfp1'),
(4, 'student_fn1', 'student_ln1', 'student_email1', 'student_phone1', '2000-01-01', 'gender', 'student_eq1', '0f7b162b18a1c966e61a7ff729388ceb85e36112b6e670901bda4d21abb8b142', 2, NULL),
(5, 'student_fn2', 'student_ln2', 'student_email2', 'student_phone2', '2000-01-01', 'gender', 'student_eq2', 'c0cd37c8058e3378757d0e5e51378721e92a987a296d0e69c365632e0c701d1f', 2, NULL),
(6, 'student_fn3', 'student_ln3', 'student_email3', 'student_phone3', '2000-01-01', 'gender', 'student_eq3', 'f17becbf178a5ffe74257a596866dd821083793257db537e5dbbdc9325ec177f', 2, NULL),
(7, 'instructor_fn2', 'instructor_ln2', 'instructor_email2', 'instructor_phone2', '2000-01-01', 'gender', 'intructor_eq2', '69ba1bff07ab778655deb4e6b22ca1262c0de90d40ffbb38d45f57c5f28a89b6', 3, NULL),
(8, 'instructor_fn3', 'instructor_ln3', 'instructor_email3', 'instructor_phone3', '2000-01-01', 'gender', 'intructor_eq3', '5bd317fb1c75de595d41878198893bdcc3ff7dfc06c8cdd2f209e00e06218521', 3, NULL),
(9, 'admin', 'admin', 'admin', 'admin', '2000-01-01', 'admin', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 5, NULL),
(10, 'user', 'user', 'user', 'user', '2000-01-01', 'user', 'user', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 1, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `license_plate` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type_id` int(10) NOT NULL,
  `fuel_type_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `vehicle`
--

INSERT INTO `vehicle` (`id`, `license_plate`, `name`, `type_id`, `fuel_type_id`) VALUES
(3, 'AAAA000', 'car_name', 1, 1),
(4, 'aaa000', 'name', 1, 1),
(5, 'ABC123', 'Suzuki Ignis', 1, 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `vehicle_types`
--

CREATE TABLE `vehicle_types` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `vehicle_types`
--

INSERT INTO `vehicle_types` (`id`, `name`) VALUES
(1, 'Auto'),
(2, 'Robogó'),
(3, 'Nagy motor'),
(4, 'Busz'),
(5, 'Kamion');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `driving_lessons`
--
ALTER TABLE `driving_lessons`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category` (`category_id`),
  ADD KEY `payment` (`payment_method`),
  ADD KEY `hour` (`hour_id`),
  ADD KEY `status` (`status_id`);

--
-- A tábla indexei `driving_license_category`
--
ALTER TABLE `driving_license_category`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `fuel_types`
--
ALTER TABLE `fuel_types`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `instructor` (`user_id`),
  ADD KEY `school` (`school_id`),
  ADD KEY `vehicle` (`vehicle_id`);

--
-- A tábla indexei `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `message_to` (`message_to`),
  ADD KEY `message_from` (`message_from`);

--
-- A tábla indexei `opening_details`
--
ALTER TABLE `opening_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `school_2` (`school_id`);

--
-- A tábla indexei `payment_methods`
--
ALTER TABLE `payment_methods`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender` (`sender_id`),
  ADD KEY `picker_2` (`picker_id`),
  ADD KEY `student_2` (`student_id`);

--
-- A tábla indexei `reserved_date`
--
ALTER TABLE `reserved_date`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `reserved_hour`
--
ALTER TABLE `reserved_hour`
  ADD PRIMARY KEY (`id`),
  ADD KEY `date` (`date_id`);

--
-- A tábla indexei `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `author` (`author_id`),
  ADD KEY `about_2` (`about_id`);

--
-- A tábla indexei `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`id`),
  ADD KEY `admin` (`administrator_id`);

--
-- A tábla indexei `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user_id`),
  ADD KEY `instructor_2_id` (`instructor_id`),
  ADD KEY `school_3` (`school_id`);

--
-- A tábla indexei `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role` (`role_id`);

--
-- A tábla indexei `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type` (`type_id`),
  ADD KEY `fuel` (`fuel_type_id`);

--
-- A tábla indexei `vehicle_types`
--
ALTER TABLE `vehicle_types`
  ADD PRIMARY KEY (`id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `driving_lessons`
--
ALTER TABLE `driving_lessons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `driving_license_category`
--
ALTER TABLE `driving_license_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT a táblához `fuel_types`
--
ALTER TABLE `fuel_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `instructor`
--
ALTER TABLE `instructor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `opening_details`
--
ALTER TABLE `opening_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `payment_methods`
--
ALTER TABLE `payment_methods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `request`
--
ALTER TABLE `request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `reserved_date`
--
ALTER TABLE `reserved_date`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `reserved_hour`
--
ALTER TABLE `reserved_hour`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `school`
--
ALTER TABLE `school`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `vehicle_types`
--
ALTER TABLE `vehicle_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `driving_lessons`
--
ALTER TABLE `driving_lessons`
  ADD CONSTRAINT `category` FOREIGN KEY (`category_id`) REFERENCES `driving_license_category` (`id`),
  ADD CONSTRAINT `hour` FOREIGN KEY (`hour_id`) REFERENCES `reserved_hour` (`id`),
  ADD CONSTRAINT `payment` FOREIGN KEY (`payment_method`) REFERENCES `payment_methods` (`id`),
  ADD CONSTRAINT `status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`);

--
-- Megkötések a táblához `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `asd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `vehicle` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`);

--
-- Megkötések a táblához `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_from` FOREIGN KEY (`message_from`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `message_to` FOREIGN KEY (`message_to`) REFERENCES `users` (`id`);

--
-- Megkötések a táblához `opening_details`
--
ALTER TABLE `opening_details`
  ADD CONSTRAINT `school_2` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`);

--
-- Megkötések a táblához `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `picker` FOREIGN KEY (`picker_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `picker_2` FOREIGN KEY (`picker_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `sender` FOREIGN KEY (`sender_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `student_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`);

--
-- Megkötések a táblához `reserved_hour`
--
ALTER TABLE `reserved_hour`
  ADD CONSTRAINT `date` FOREIGN KEY (`date_id`) REFERENCES `reserved_date` (`id`);

--
-- Megkötések a táblához `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `about` FOREIGN KEY (`about_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `about_1` FOREIGN KEY (`about_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `students` (`id`);

--
-- Megkötések a táblához `school`
--
ALTER TABLE `school`
  ADD CONSTRAINT `admin` FOREIGN KEY (`administrator_id`) REFERENCES `users` (`id`);

--
-- Megkötések a táblához `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `school_3` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Megkötések a táblához `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Megkötések a táblához `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `fuel` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_types` (`id`),
  ADD CONSTRAINT `type` FOREIGN KEY (`type_id`) REFERENCES `vehicle_types` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
