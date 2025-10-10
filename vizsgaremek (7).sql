-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Gép: localhost:3306
-- Létrehozás ideje: 2025. Okt 10. 10:33
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllEmail` ()   BEGIN
	SELECT `users`.`email` FROM `users`;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getStudentByUserId` (IN `userIdIN` INT)   BEGIN
	SELECT * FROM students WHERE students.user_id = userIdIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserByEmail` (IN `emailIN` VARCHAR(100))   BEGIN
	SELECT * FROM `users` WHERE emailIN = `users`.`email`;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_lesson`
--

CREATE TABLE `driving_lesson` (
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
  `student_id` int(11) NOT NULL,
  `is_end` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_license_category`
--

CREATE TABLE `driving_license_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `min_age` int(2) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `driving_license_category`
--

INSERT INTO `driving_license_category` (`id`, `name`, `min_age`, `is_deleted`, `deleted_at`) VALUES
(1, 'AM', 14, 0, NULL),
(2, 'A1', 16, 0, NULL),
(3, 'A2', 18, 0, NULL),
(4, 'A', 24, 0, NULL),
(5, 'B1', 17, 0, NULL),
(6, 'B', 17, 0, NULL),
(7, 'C1', 18, 0, NULL),
(8, 'C', 18, 0, NULL),
(9, 'D1', 18, 0, NULL),
(10, 'D', 18, 0, NULL),
(11, 'BE', 17, 0, NULL),
(12, 'C1E', 18, 0, NULL),
(13, 'CE', 18, 0, NULL),
(14, 'D1E', 18, 0, NULL),
(15, 'DE', 18, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `education`
--

CREATE TABLE `education` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `education`
--

INSERT INTO `education` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'Általános Iskola', 0, NULL),
(2, 'Szakiskola / Szakképző iskola', 0, NULL),
(3, 'Szakközépiskola', 0, NULL),
(4, 'Gimnázium', 0, NULL),
(5, 'Felsőfokú szakképzés', 0, NULL),
(6, 'Egyetem', 0, NULL),
(7, 'Főiskola', 0, NULL),
(8, 'Doktori képzés', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `fuel_type`
--

CREATE TABLE `fuel_type` (
  `id` int(11) NOT NULL,
  `name` varchar(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `fuel_type`
--

INSERT INTO `fuel_type` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'Benzin', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `instructor`
--

CREATE TABLE `instructor` (
  `id` int(11) NOT NULL,
  `user_id` int(10) NOT NULL,
  `school_id` int(10) NOT NULL,
  `promo_text` longtext NOT NULL,
  `vehicle_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `instructor`
--

INSERT INTO `instructor` (`id`, `user_id`, `school_id`, `promo_text`, `vehicle_id`, `is_deleted`, `deleted_at`) VALUES
(1, 1, 2, 'instructor promo text', 4, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `message_to` int(10) NOT NULL,
  `message_from` int(10) NOT NULL,
  `content` longtext NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `opening_detail`
--

CREATE TABLE `opening_detail` (
  `id` int(11) NOT NULL,
  `opening_time` time NOT NULL,
  `close_time` time NOT NULL,
  `day` varchar(100) NOT NULL,
  `school_id` int(10) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `payment_method`
--

CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `payment_method`
--

INSERT INTO `payment_method` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'Kártya', 0, NULL),
(2, 'Készpénz', 0, NULL),
(3, 'Revolut', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_date`
--

CREATE TABLE `reserved_date` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `is_full` tinyint(1) NOT NULL DEFAULT '0',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_hour`
--

CREATE TABLE `reserved_hour` (
  `id` int(11) NOT NULL,
  `start` int(2) NOT NULL,
  `end` int(2) NOT NULL,
  `date_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
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
  `instructor_id` int(10) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `role`
--

INSERT INTO `role` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'ROLE_user', 0, NULL),
(2, 'ROLE_student', 0, NULL),
(3, 'ROLE_instructor', 0, NULL),
(4, 'ROLE_school_admin', 0, NULL),
(5, 'ROLE_administrator', 0, NULL),
(6, 'ROLE_school_owner', 0, NULL);

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
  `owner_id` int(10) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `school`
--

INSERT INTO `school` (`id`, `name`, `email`, `phone`, `country`, `town`, `address`, `promo_text`, `banner_img_path`, `owner_id`, `is_deleted`, `deleted_at`) VALUES
(2, 'AutosIskola1', 'iskola@gmail.com', 'a', 'Tolna', 'Dombóvár', 'Cim', 'a', 'a', 6, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `student`
--

CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `school_id` int(10) NOT NULL,
  `instructor_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `student`
--

INSERT INTO `student` (`id`, `school_id`, `instructor_id`, `user_id`, `is_deleted`, `deleted_at`) VALUES
(5, 2, 1, 3, 0, NULL),
(6, 2, 1, 2, 0, NULL),
(7, 2, 1, 4, 0, NULL),
(8, 2, 1, 5, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `birth_date` date NOT NULL,
  `gender` varchar(50) NOT NULL,
  `password` longtext NOT NULL,
  `role_id` int(11) DEFAULT '1',
  `pfp_path` longtext,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL,
  `school_administrator_id` int(11) DEFAULT NULL,
  `education_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `phone`, `birth_date`, `gender`, `password`, `role_id`, `pfp_path`, `created_at`, `last_login`, `is_deleted`, `deleted_at`, `school_administrator_id`, `education_id`) VALUES
(1, 'Oktató', 'Oktató', 'bzhalmai@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$RKrOMjLlUohVtc1bMDYqIg$SpIcGMy0yLKv1prK//IIMK9x6o2tGse2Nw/Yq7q8Vfw', NULL, NULL, '2025-10-07 14:06:32', NULL, 0, NULL, NULL, 1),
(2, 'Tanuló2', 'Tanuló2', 'bzhalmai2@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$x8ZCOKm/NXEMCiY+K2J5xw$IFLOP3wL/S26aywbMGY8v3DfO1VdgCSBOOkqM9gc5CE', NULL, NULL, '2025-10-07 14:06:59', NULL, 0, NULL, NULL, 1),
(3, 'Tanuló1', 'Tanuló1', 'bzhalmai1@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$Ge1f7T03hPx/S3j/tdh84A$01K36tva/4k4sKm1eoP5ZKtNZrmtS1NdFdh+nMiQq0E', NULL, NULL, '2025-10-07 14:07:04', NULL, 0, NULL, NULL, 1),
(4, 'Tanuló3', 'Tanuló3', 'bzhalmai3@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$TdL1gVgPWH4C8oKzxE4TaQ$nQr1p93ykcnP+7CRcFwG8FyVJt1hzrbwxVotLJ4Qxxw', NULL, NULL, '2025-10-07 14:07:09', NULL, 0, NULL, NULL, 1),
(5, 'Tanuló4', 'Tanuló4', 'bzhalmai3@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$mzu/1vpdf0pCkDW81qN4CQ$8JJGV9sLLOaEuf/jnViXHeuUZMFx/zj2oQX4Wl4IP48', NULL, NULL, '2025-10-07 14:07:34', NULL, 0, NULL, NULL, 1),
(6, 'IskolaTulaj', 'IskolaTulaj', 'bzhalmai4@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$D5apy2+dI2lTQW+iK60vGQ$Ta80iOeSgC1bwP9wdH7xbqycZdyBmOwASimmuwDbQYE', NULL, NULL, '2025-10-07 14:09:21', NULL, 0, NULL, NULL, 1),
(7, 'IskolaAdmin1', 'IskolaAdmin1', 'bzhalmai4@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$LDYkFEW00svoUYrEv4c5Vw$iBjM7m0CD3CaiMhFqaviiyxbAEsLH2sK1aCaN7jYZMU', NULL, NULL, '2025-10-07 14:09:52', NULL, 0, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `license_plate` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type_id` int(10) NOT NULL,
  `fuel_type_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `vehicle`
--

INSERT INTO `vehicle` (`id`, `license_plate`, `name`, `type_id`, `fuel_type_id`, `is_deleted`, `deleted_at`) VALUES
(3, 'AAAA000', 'car_name', 1, 1, 0, NULL),
(4, 'aaa000', 'name', 1, 1, 0, NULL),
(5, 'ABC123', 'Suzuki Ignis', 1, 1, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `vehicle_type`
--

CREATE TABLE `vehicle_type` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `vehicle_type`
--

INSERT INTO `vehicle_type` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'Auto', 0, NULL),
(2, 'Robogó', 0, NULL),
(3, 'Nagy motor', 0, NULL),
(4, 'Busz', 0, NULL),
(5, 'Kamion', 0, NULL);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `driving_lesson`
--
ALTER TABLE `driving_lesson`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category` (`category_id`),
  ADD KEY `payment` (`payment_method_id`),
  ADD KEY `hour` (`hour_id`),
  ADD KEY `status` (`status_id`),
  ADD KEY `d_instructor` (`instructor_id`),
  ADD KEY `d_students` (`student_id`);

--
-- A tábla indexei `driving_license_category`
--
ALTER TABLE `driving_license_category`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `education`
--
ALTER TABLE `education`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `fuel_type`
--
ALTER TABLE `fuel_type`
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
-- A tábla indexei `opening_detail`
--
ALTER TABLE `opening_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `school_2` (`school_id`);

--
-- A tábla indexei `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`id`);

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
  ADD KEY `about_2` (`instructor_id`),
  ADD KEY `about_school` (`school_id`);

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
  ADD KEY `admin` (`owner_id`);

--
-- A tábla indexei `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user_id`),
  ADD KEY `instructor_2_id` (`instructor_id`),
  ADD KEY `school_3` (`school_id`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role` (`role_id`),
  ADD KEY `school_admin` (`school_administrator_id`);

--
-- A tábla indexei `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type` (`type_id`),
  ADD KEY `fuel` (`fuel_type_id`);

--
-- A tábla indexei `vehicle_type`
--
ALTER TABLE `vehicle_type`
  ADD PRIMARY KEY (`id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `driving_lesson`
--
ALTER TABLE `driving_lesson`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `driving_license_category`
--
ALTER TABLE `driving_license_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT a táblához `education`
--
ALTER TABLE `education`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT a táblához `fuel_type`
--
ALTER TABLE `fuel_type`
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
-- AUTO_INCREMENT a táblához `opening_detail`
--
ALTER TABLE `opening_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `payment_method`
--
ALTER TABLE `payment_method`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT a táblához `school`
--
ALTER TABLE `school`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `student`
--
ALTER TABLE `student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT a táblához `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `vehicle_type`
--
ALTER TABLE `vehicle_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `driving_lesson`
--
ALTER TABLE `driving_lesson`
  ADD CONSTRAINT `category` FOREIGN KEY (`category_id`) REFERENCES `driving_license_category` (`id`),
  ADD CONSTRAINT `d_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `d_students` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  ADD CONSTRAINT `hour` FOREIGN KEY (`hour_id`) REFERENCES `reserved_hour` (`id`),
  ADD CONSTRAINT `payment` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
  ADD CONSTRAINT `status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`);

--
-- Megkötések a táblához `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `asd` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `vehicle` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`);

--
-- Megkötések a táblához `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_from` FOREIGN KEY (`message_from`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `message_to` FOREIGN KEY (`message_to`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `opening_detail`
--
ALTER TABLE `opening_detail`
  ADD CONSTRAINT `school_2` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`);

--
-- Megkötések a táblához `reserved_hour`
--
ALTER TABLE `reserved_hour`
  ADD CONSTRAINT `date` FOREIGN KEY (`date_id`) REFERENCES `reserved_date` (`id`);

--
-- Megkötések a táblához `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `about_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `about_school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `student` (`id`);

--
-- Megkötések a táblához `school`
--
ALTER TABLE `school`
  ADD CONSTRAINT `admin` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `inst` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `school_3` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `school_admin` FOREIGN KEY (`school_administrator_id`) REFERENCES `school` (`id`);

--
-- Megkötések a táblához `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `fuel` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_type` (`id`),
  ADD CONSTRAINT `type` FOREIGN KEY (`type_id`) REFERENCES `vehicle_type` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
