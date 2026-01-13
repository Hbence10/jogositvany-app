-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Gép: localhost:3306
-- Létrehozás ideje: 2026. Jan 13. 17:11
-- Kiszolgáló verziója: 5.7.24
-- PHP verzió: 8.1.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `vizsgaremek_10.0`
--

DELIMITER $$
--
-- Eljárások
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLesson` (IN `idIN` INT)   BEGIN
	UPDATE `driving_lesson` SET `is_cancelled`= 1 ,`cancelled_at`= CURRENT_TIMESTAMP() WHERE `driving_lesson`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLessonInstructor` (IN `idIN` INT)   BEGIN
	UPDATE `driving_lesson_instructor` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `driving_lesson_instructor`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLessonRequest` (IN `idIN` INT)   BEGIN
	UPDATE `driving_lesson_request` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `driving_lesson_request`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLessonType` (IN `idIN` INT)   BEGIN
	UPDATE `driving_lesson_type` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `driving_lesson_type`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLicenseCategory` (IN `idIN` INT)   BEGIN
	UPDATE `driving_license_category` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `driving_license_category`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteEducation` (IN `idIN` INT)   BEGIN
	UPDATE `education` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `education`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteExamRequest` (IN `idIN` INT)   BEGIN
    UPDATE `exam_request` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `exam_request`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteFuelType` (IN `idIN` INT)   BEGIN
    UPDATE `fuel_type` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `fuel_type`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteInstructor` (IN `idIN` INT)   BEGIN
    UPDATE `instructor` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `instructor`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteInstructorJoinRequest` (IN `idIN` INT)   BEGIN
    UPDATE `instructor_join_request` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `instructor_join_request`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteMessage` (IN `idIN` INT)   BEGIN
    UPDATE `message` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `message`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteOpeningDetail` (IN `idIN` INT)   BEGIN
    UPDATE `opening_detail` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `opening_detail`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deletePaymentMethod` (IN `idIN` INT)   BEGIN
    UPDATE `payment_method` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `payment_method`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteReservedDate` (IN `idIN` INT)   BEGIN
    UPDATE `reserved_date` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `reserved_date`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteReservedHour` (IN `idIN` INT)   BEGIN
    UPDATE `reserved_hour` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `reserved_hour`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteReview` (IN `idIN` INT)   BEGIN
    UPDATE `review` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `review`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRole` (IN `idIN` INT)   BEGIN
    UPDATE `role` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `role`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteSchool` (IN `idIN` INT)   BEGIN
    UPDATE `school` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `school`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteSchoolJoinRequest` (IN `idIN` INT)   BEGIN
    UPDATE `school_join_request` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `school_join_request`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteStatus` (IN `idIN` INT)   BEGIN
    UPDATE `status` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `status`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteStudent` (IN `idIN` INT)   BEGIN
    UPDATE `student` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `student`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteUser` (IN `idIN` INT)   BEGIN
    UPDATE `user` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `user`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteVehicle` (IN `idIN` INT)   BEGIN
    UPDATE `vehicle` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `vehicle`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteVehicleType` (IN `idIN` INT)   BEGIN
    UPDATE `vehicle_type` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_TIMESTAMP() WHERE `vehicle_type`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllDrivingLesson` ()   BEGIN
	SELECT*FROM `driving_lesson` WHERE `driving_lesson`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllDrivingLessonInstructor` ()   BEGIN
	SELECT*FROM driving_lesson_instructor WHERE `driving_lesson_instructor`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllDrivingLessonRequest` ()   BEGIN
	SELECT*FROM driving_lesson_request WHERE `driving_lesson_request`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllDrivingLessonType` ()   BEGIN
	SELECT*FROM driving_lesson_type WHERE `driving_lesson_type`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllDrivingLicenseCategory` ()   BEGIN
	SELECT*FROM driving_license_category WHERE `driving_license_category`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllEducation` ()   BEGIN
	SELECT*FROM education WHERE `education`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllEmail` ()   BEGIN
	SELECT `user`.`email` FROM `user` WHERE `user`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllExamRequest` ()   BEGIN
	SELECT*FROM exam_request WHERE `exam_request`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllFuelType` ()   BEGIN
	SELECT*FROM fuel_type WHERE `fuel_type`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllInstructor` ()   BEGIN
	SELECT*FROM instructor WHERE `instructor`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllInstructorJoinRequest` ()   BEGIN
	SELECT*FROM instructor_join_request WHERE `instructor_join_request`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllMessage` ()   BEGIN
	SELECT*FROM message WHERE `message`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllOpeningDetail` ()   BEGIN
	SELECT*FROM opening_detail WHERE `opening_detail`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllPaymentMethod` ()   BEGIN
	SELECT*FROM payment_method WHERE `payment_method`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllReservedDate` ()   BEGIN
	SELECT*FROM reserved_date WHERE `reserved_date`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllReservedHour` ()   BEGIN
	SELECT*FROM reserved_hour WHERE `reserved_hour`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllReview` ()   BEGIN
	SELECT*FROM review WHERE `review`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllRole` ()   BEGIN
	SELECT*FROM role WHERE `role`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllSchool` ()   BEGIN
	SELECT*FROM school WHERE `school`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllSchoolJoinRequest` ()   BEGIN
	SELECT*FROM school_join_request WHERE `school_join_request`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllStatus` ()   BEGIN
	SELECT*FROM `status` WHERE `status`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllStudent` ()   BEGIN
	SELECT*FROM student WHERE `student`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllUser` ()   BEGIN
	SELECT*FROM `user` WHERE `user`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllVehicle` ()   BEGIN
	SELECT*FROM vehicle WHERE `vehicle`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getAllVehicleType` ()   BEGIN
	SELECT*FROM vehicle_type WHERE `vehicle_type`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDrivingLesson` (IN `idIN` INT)   BEGIN
	SELECT*FROM `driving_lesson` WHERE driving_lesson.id= idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDrivingLessonInstructor` (IN `idIN` INT)   BEGIN
	SELECT*FROM `driving_lesson_instructor` WHERE
    `driving_lesson_instructor`.id = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDrivingLessonRequest` (IN `idIN` INT)   BEGIN
	SELECT*FROM `driving_lesson_request` WHERE
    `driving_lesson_request`.id = idIN  AND `driving_lesson_request`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDrivingLessonType` (IN `idIN` INT)   BEGIN
	SELECT*FROM `driving_lesson_type` WHERE
    `driving_lesson_type`.id = idIN AND `driving_lesson_type`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDrivingLicenseCategory` (IN `idIN` INT)   BEGIN
	SELECT*FROM `driving_license_category` WHERE
    `driving_license_category`.`id` = idIN AND `driving_license_category`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getEducation` (IN `idIN` INT)   BEGIN
	SELECT*FROM `education` WHERE `education`.id = idIN AND `education`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getExamRequest` (IN `idIN` INT)   BEGIN
	SELECT*FROM `exam_request` WHERE `exam_request`.id = idIN AND `exam_request`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getFuelType` (IN `idIN` INT)   BEGIN
	SELECT*FROM `fuel_type` WHERE `fuel_type`.`id` = idIN AND `fuel_type`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getInstructor` (IN `idIN` INT)   BEGIN
	SELECT*FROM `instructor` WHERE `instructor`.`id` = idIN AND `instructor`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getInstructorBySearch` (IN `fuelTypeIdIN` INT, IN `schoolIdIN` INT)   BEGIN 
    SELECT i.id
    FROM `instructor` i
    INNER JOIN user u ON 
    u.id = i.user_id
    INNER JOIN vehicle v ON 
    v.id = i.vehicle_id
    INNER JOIN fuel_type ft ON 
    ft.id = v.fuel_type_id
    WHERE 
    ft.id = fuelTypeIdIN
    AND 
    i.school_id = schoolIdIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getInstructorJoinRequest` (IN `idIN` INT)   BEGIN
	SELECT*FROM `instructor_join_request` WHERE `instructor_join_request`.`id` = idIN AND `instructor_join_request`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getMessage` (IN `idIN` INT)   BEGIN
	SELECT*FROM `message` WHERE `message`.`id` = idIN AND `message`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getOpeningDetail` (IN `idIN` INT)   BEGIN
	SELECT*FROM `opening_detail` WHERE `opening_detail`.`id` = idIN AND `opening_detail`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getPaymentMethod` (IN `idIN` INT)   BEGIN
	SELECT*FROM `payment_method` WHERE `payment_method`.id = idIN AND `payment_method`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedDate` (IN `idIN` INT)   BEGIN
	SELECT*FROM `reserved_date` WHERE `reserved_date`.`id` = idIN AND `reserved_date`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedDateByDate` (IN `wantedDateIN` DATE)   BEGIN 
	SELECT * FROM reserved_date rd
    WHERE 
    rd.date = wantedDateIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReservedHour` (IN `idIN` INT)   BEGIN
	SELECT*FROM `reserved_hour` WHERE `reserved_hour`.`id` = idIN AND `reserved_hour`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getReview` (IN `idIN` INT)   BEGIN
	SELECT*FROM `review` WHERE `review`.`id` = idIN AND `review`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getRole` (IN `idIN` INT)   BEGIN
	SELECT*FROM `role` WHERE `role`.`id` = idIN AND `role`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getSchool` (IN `idIN` INT)   BEGIN
	SELECT*FROM `school` WHERE `school`.`id` = idIN AND `school`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getSchoolBySearch` (IN `townnameIN` VARCHAR(250))   BEGIN
	SELECT s.id FROM school s
    WHERE 
    s.town LIKE CONCAT(townnameIN, "%");
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getSchoolJoinRequest` (IN `idIN` INT)   BEGIN
	SELECT*FROM `school_join_request` WHERE `school_join_request`.`id` = idIN AND `school_join_request`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getStatus` (IN `idIN` INT)   BEGIN
	SELECT*FROM `status` WHERE `status`.`id` = idIN AND `status`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getStudent` (IN `idIN` INT)   BEGIN
	SELECT*FROM `student` WHERE `student`.`id` = idIN AND `student`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getStudentByUserId` (IN `userIdIN` INT)   BEGIN
	SELECT * FROM student WHERE `student`.user_id = userIdIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUser` (IN `idIN` INT)   BEGIN
	SELECT*FROM `user` WHERE `user`.`id` = idIN AND `user`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserByEmail` (IN `emailIN` VARCHAR(100))   BEGIN
	SELECT * FROM `user` WHERE emailIN = `user`.`email` AND `user`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getVehicle` (IN `idIN` INT)   BEGIN
	SELECT*FROM `vehicle` WHERE `vehicle`.`id` = idIN AND `vehicle`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getVehicleType` (IN `idIN` INT)   BEGIN
	SELECT*FROM `vehicle_type` WHERE `vehicle_type`.`id` = idIN AND `vehicle_type`.`is_deleted` = 0;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `setRoleOfUser` (IN `userId` INT, IN `roleId` INT)   BEGIN 
	UPDATE `user` SET 
    `role_id`= roleId
    WHERE
    user.id = userId;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_lesson`
--

CREATE TABLE `driving_lesson` (
  `id` int(11) NOT NULL,
  `start_km` int(7) DEFAULT NULL,
  `end_km` int(7) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `pick_up_place` varchar(100) DEFAULT NULL,
  `drop_off_place` varchar(100) DEFAULT NULL,
  `lesson_hour_number` int(1) DEFAULT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT '0',
  `payment_method_id` int(10) NOT NULL,
  `hour_id` int(11) NOT NULL,
  `type_id` int(10) NOT NULL,
  `status_id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `is_end` tinyint(1) NOT NULL DEFAULT '0',
  `is_cancelled` tinyint(1) NOT NULL DEFAULT '0',
  `cancelled_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `driving_lesson`
--

INSERT INTO `driving_lesson` (`id`, `start_km`, `end_km`, `location`, `pick_up_place`, `drop_off_place`, `lesson_hour_number`, `is_paid`, `payment_method_id`, `hour_id`, `type_id`, `status_id`, `instructor_id`, `student_id`, `is_end`, `is_cancelled`, `cancelled_at`) VALUES
(1, NULL, NULL, NULL, NULL, NULL, NULL, 0, 2, 2, 2, 1, 1, 6, 0, 0, NULL),
(2, NULL, NULL, 'location1', 'p_u_p1', 'd_o_p1', NULL, 0, 1, 1, 2, 1, 1, 6, 0, 0, NULL),
(3, NULL, NULL, 'location2', 'p_u_p2', 'd_o_p2', NULL, 0, 1, 1, 2, 1, 1, 6, 0, 0, NULL),
(4, NULL, NULL, 'location3', 'p_u_p3', 'd_o_p3', NULL, 0, 3, 2, 1, 2, 1, 6, 0, 0, NULL),
(5, NULL, NULL, 'location4', 'p_u_p4', 'd_o_p4', NULL, 0, 1, 1, 2, 1, 1, 6, 0, 0, NULL),
(6, NULL, NULL, 'location5', 'p_u_p5', 'd_o_p5', NULL, 0, 1, 1, 2, 2, 1, 6, 0, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_lesson_instructor`
--

CREATE TABLE `driving_lesson_instructor` (
  `id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `driving_lesson_type_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `driving_lesson_instructor`
--

INSERT INTO `driving_lesson_instructor` (`id`, `instructor_id`, `driving_lesson_type_id`, `is_deleted`, `deleted_at`) VALUES
(1, 1, 1, 1, '2025-11-30 23:00:00');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_lesson_request`
--

CREATE TABLE `driving_lesson_request` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `msg` longtext,
  `date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `lesson_type_id` int(11) NOT NULL,
  `sent_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_accepted` tinyint(1) DEFAULT NULL,
  `accepted_at` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_lesson_type`
--

CREATE TABLE `driving_lesson_type` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` int(5) NOT NULL,
  `license_category_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `driving_lesson_type`
--

INSERT INTO `driving_lesson_type` (`id`, `name`, `price`, `license_category_id`, `school_id`, `is_deleted`, `deleted_at`) VALUES
(1, 'Vezetési kategória teszt tipus', 6000, 4, 2, 0, NULL),
(2, 'alapoktatas', 10000, 6, 3, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `driving_license_category`
--

CREATE TABLE `driving_license_category` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `min_age` int(2) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `driving_license_category`
--

INSERT INTO `driving_license_category` (`id`, `name`, `min_age`, `is_deleted`, `deleted_at`) VALUES
(1, 'AM', 14, 1, '2025-11-30 23:00:00'),
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
  `deleted_at` timestamp NULL DEFAULT NULL
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
-- Tábla szerkezet ehhez a táblához `exam_request`
--

CREATE TABLE `exam_request` (
  `id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL,
  `requested_date` datetime NOT NULL,
  `student_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `exam_request`
--

INSERT INTO `exam_request` (`id`, `instructor_id`, `school_id`, `requested_date`, `student_id`, `is_deleted`, `deleted_at`) VALUES
(1, 4, 9, '2025-12-03 00:00:00', 7, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `fuel_type`
--

CREATE TABLE `fuel_type` (
  `id` int(11) NOT NULL,
  `name` varchar(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `fuel_type`
--

INSERT INTO `fuel_type` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'Benzin', 0, NULL),
(2, 'Dízel', 0, NULL),
(3, 'Hibrid', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `instructor`
--

CREATE TABLE `instructor` (
  `id` int(11) NOT NULL,
  `user_id` int(10) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `promo_text` longtext,
  `vehicle_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `instructor`
--

INSERT INTO `instructor` (`id`, `user_id`, `school_id`, `promo_text`, `vehicle_id`, `is_deleted`, `deleted_at`) VALUES
(1, 1, 2, 'instructor promo text', 3, 0, NULL),
(2, 13, 2, 'promotext', NULL, 0, NULL),
(3, 14, 3, 'promo', NULL, 0, NULL),
(4, 30, 6, NULL, NULL, 0, NULL),
(5, 31, 6, NULL, NULL, 0, NULL),
(6, 32, 6, NULL, NULL, 0, NULL),
(7, 33, 6, NULL, NULL, 0, NULL),
(8, 34, 6, NULL, NULL, 0, NULL),
(9, 35, 6, NULL, NULL, 0, NULL),
(10, 36, 6, NULL, NULL, 0, NULL),
(11, 37, 7, NULL, NULL, 0, NULL),
(12, 38, 7, NULL, NULL, 0, NULL),
(13, 40, 7, NULL, NULL, 0, NULL),
(14, 41, 6, NULL, NULL, 0, NULL),
(15, 42, 7, NULL, NULL, 0, NULL),
(16, 43, 7, NULL, NULL, 0, NULL),
(17, 44, 7, NULL, NULL, 0, NULL),
(18, 45, 8, NULL, NULL, 0, NULL),
(19, 46, 8, NULL, NULL, 0, NULL),
(20, 47, 8, NULL, NULL, 0, NULL),
(21, 48, 8, NULL, NULL, 0, NULL),
(22, 46, 8, NULL, NULL, 0, NULL),
(23, 47, 8, NULL, NULL, 0, NULL),
(24, 48, 8, NULL, NULL, 0, NULL),
(25, 49, 9, 'a', 6, 0, NULL),
(28, 50, 9, 'a', 4, 0, NULL),
(29, 51, 9, 'a', NULL, 0, NULL),
(30, 52, 9, 'a', 5, 0, NULL),
(31, 53, 9, 'a', 7, 0, NULL),
(32, 54, 9, 'a', 12, 0, NULL),
(33, 55, 9, 'a', 13, 0, NULL),
(35, 56, 9, NULL, 14, 0, NULL),
(36, 57, 9, NULL, 15, 0, NULL),
(37, 59, 10, NULL, NULL, 0, NULL),
(38, 60, 10, NULL, NULL, 0, NULL),
(39, 61, 10, NULL, NULL, 0, NULL),
(40, 62, 10, NULL, NULL, 0, NULL),
(41, 63, 10, NULL, NULL, 0, NULL),
(42, 64, 10, NULL, NULL, 0, NULL),
(43, 65, 10, NULL, NULL, 0, NULL),
(44, 67, 2, NULL, NULL, 0, NULL),
(50, NULL, NULL, NULL, 26, 0, NULL),
(51, 76, NULL, NULL, 27, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `instructor_category`
--

CREATE TABLE `instructor_category` (
  `id` int(11) NOT NULL,
  `driving_license_category_id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `instructor_join_request`
--

CREATE TABLE `instructor_join_request` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `is_accepted` tinyint(1) DEFAULT NULL,
  `accepted_at` timestamp NULL DEFAULT NULL,
  `sent_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `instructor_join_request`
--

INSERT INTO `instructor_join_request` (`id`, `student_id`, `instructor_id`, `is_accepted`, `accepted_at`, `sent_at`, `is_deleted`, `deleted_at`) VALUES
(2, 21, 1, 1, '2025-12-18 12:09:55', '2025-12-18 12:05:53', 0, NULL),
(3, 23, 30, 0, NULL, '2026-01-04 12:20:05', 0, NULL);

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

--
-- A tábla adatainak kiíratása `message`
--

INSERT INTO `message` (`id`, `message_to`, `message_from`, `content`, `created_at`, `is_deleted`, `deleted_at`) VALUES
(1, 1, 3, 'A message táblához tartozó test adat', '2025-12-01 09:13:26', 1, '2025-12-01 00:00:00');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `opening_detail`
--

CREATE TABLE `opening_detail` (
  `id` int(11) NOT NULL,
  `opening_time` time DEFAULT NULL,
  `close_time` time DEFAULT NULL,
  `day` varchar(100) NOT NULL,
  `is_closed` tinyint(1) NOT NULL DEFAULT '0',
  `school_id` int(10) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `opening_detail`
--

INSERT INTO `opening_detail` (`id`, `opening_time`, `close_time`, `day`, `is_closed`, `school_id`, `is_deleted`, `deleted_at`) VALUES
(1, '20:00:00', '23:00:00', 'Hétfő', 0, 9, 0, NULL),
(2, NULL, NULL, 'Kedd', 1, 9, 0, NULL),
(3, NULL, NULL, 'Szerda', 0, 9, 0, NULL),
(4, NULL, NULL, 'Csütörtök', 0, 9, 0, NULL),
(5, NULL, NULL, 'Péntek', 0, 9, 0, NULL),
(6, NULL, NULL, 'Szombat', 0, 9, 0, NULL),
(7, NULL, NULL, 'Vasárnap', 0, 9, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `payment_method`
--

CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `payment_method`
--

INSERT INTO `payment_method` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'Kártya', 1, '2025-11-30 23:00:00'),
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
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `reserved_date`
--

INSERT INTO `reserved_date` (`id`, `date`, `is_full`, `is_deleted`, `deleted_at`) VALUES
(1, '2025-10-14', 0, 1, '2025-11-30 23:00:00'),
(2, '2025-12-04', 0, 0, '2025-12-04 11:24:12'),
(3, '2025-12-03', 0, 0, '2025-12-03 09:34:20'),
(4, '2025-12-02', 0, 0, '2025-12-02 05:34:37'),
(5, '2025-12-01', 0, 0, '2025-12-01 14:34:53');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `reserved_hour`
--

CREATE TABLE `reserved_hour` (
  `id` int(11) NOT NULL,
  `date_id` int(11) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `reserved_hour`
--

INSERT INTO `reserved_hour` (`id`, `date_id`, `start_time`, `end_time`, `is_deleted`, `deleted_at`) VALUES
(1, 1, '00:00:00', '00:00:00', 1, '2025-11-30 23:00:00'),
(2, 2, '00:00:00', '00:00:00', 0, '2025-12-04 12:35:30'),
(3, 2, '00:00:00', '00:00:00', 0, '2025-12-03 09:35:30'),
(4, 4, '00:00:00', '00:00:00', 0, '2025-12-02 09:36:47'),
(5, 5, '00:00:00', '00:00:00', 0, '2025-12-01 09:36:47');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `author_id` int(10) NOT NULL,
  `text` longtext NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `rating` double NOT NULL,
  `is_anonymous` tinyint(1) NOT NULL DEFAULT '0',
  `instructor_id` int(10) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `review`
--

INSERT INTO `review` (`id`, `author_id`, `text`, `created_at`, `rating`, `is_anonymous`, `instructor_id`, `school_id`, `is_deleted`, `deleted_at`) VALUES
(1, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 12:16:22', 5, 0, NULL, 9, 0, NULL),
(5, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:09', 4, 0, NULL, 9, 0, NULL),
(6, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:27', 4, 0, NULL, 9, 0, NULL),
(7, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:29', 4, 0, NULL, 9, 0, NULL),
(8, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:34', 4, 0, NULL, 9, 0, NULL),
(9, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:35', 4, 0, NULL, 9, 0, NULL),
(10, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:36', 4, 0, NULL, 9, 0, NULL),
(11, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:37', 4, 0, NULL, 9, 0, NULL),
(12, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:38', 4, 0, NULL, 9, 0, NULL),
(13, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:39', 4, 0, NULL, 9, 0, NULL),
(14, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:40', 4, 0, NULL, 9, 0, NULL),
(15, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:41', 4, 0, NULL, 9, 0, NULL),
(16, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:42', 4, 0, NULL, 9, 0, NULL),
(17, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:48', 4, 0, NULL, 9, 0, NULL),
(18, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:48', 4, 0, NULL, 9, 0, NULL),
(19, 6, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum', '2025-12-26 13:25:49', 4, 0, NULL, 9, 0, NULL),
(20, 21, 'afs', '2025-12-27 10:50:56', 1.5, 0, 28, NULL, 0, NULL),
(21, 21, 'asfasf', '2025-12-27 10:51:53', 1.5, 0, 28, NULL, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
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
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `school`
--

INSERT INTO `school` (`id`, `name`, `email`, `phone`, `country`, `town`, `address`, `promo_text`, `banner_img_path`, `owner_id`, `is_deleted`, `deleted_at`) VALUES
(2, 'AutosIskola1', 'iskola@gmail.com', 'a1', 'Tolna', 'Dombóvár', 'Cim', 'a', 'a', 6, 0, NULL),
(3, 'Iskola2', 'iskola2@gmail.com', 'a2', 'Orszag', 'Varos', 'Cim', 'promo', 'banner', 12, 0, NULL),
(6, 'school1', 'school1@gmail.com', '0000001', 'Baranya', 'Pécs', 'address1', 'a', 'a', 25, 0, NULL),
(7, 'school2', 'school2@gmail.com', '0000002', 'Somogy', 'Kaposvár', 'address2', 'a', 'a', 26, 0, NULL),
(8, 'school3', 'school3@gmail.com', '0000003', 'Fejér', 'Székesfehérvár', 'address3', 'a', 'a', 27, 0, NULL),
(9, 'school4Update', 'school4@gmail.com', '706285232', 'Buda', 'Budapest', 'address4', 'a', 'a', 28, 0, NULL),
(10, 'school5', 'school5@gmail.com', '0000005', 'Zala', 'Zalaegerszeg', 'address5', 'a', 'a', 29, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `school_category`
--

CREATE TABLE `school_category` (
  `id` int(11) NOT NULL,
  `driving_license_category_id` int(11) NOT NULL,
  `shool_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `school_join_request`
--

CREATE TABLE `school_join_request` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL,
  `driving_license_category_id` int(11) NOT NULL,
  `is_accepted` tinyint(1) DEFAULT NULL,
  `accepted_at` timestamp NULL DEFAULT NULL,
  `sended_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `school_join_request`
--

INSERT INTO `school_join_request` (`id`, `user_id`, `school_id`, `driving_license_category_id`, `is_accepted`, `accepted_at`, `sended_at`, `is_deleted`, `deleted_at`) VALUES
(22, 15, 9, 1, 1, '2026-01-04 11:21:52', '2026-01-04 11:14:03', 0, NULL),
(23, 77, 9, 1, 1, '2026-01-04 12:12:33', '2026-01-04 12:11:33', 0, NULL),
(24, 77, 9, 1, NULL, NULL, '2026-01-04 12:11:38', 1, '2026-01-03 23:00:00');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `status`
--

INSERT INTO `status` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'státusz_tipus1', 1, '2025-11-30 23:00:00'),
(2, 'státusz_tipus2', 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `student`
--

CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `school_id` int(10) NOT NULL,
  `instructor_id` int(10) DEFAULT NULL,
  `user_id` int(10) NOT NULL,
  `driving_license_category_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `student`
--

INSERT INTO `student` (`id`, `school_id`, `instructor_id`, `user_id`, `driving_license_category_id`, `is_deleted`, `deleted_at`) VALUES
(6, 9, 1, 3, 1, 0, NULL),
(7, 9, 1, 4, 1, 0, NULL),
(8, 9, 1, 5, 1, 0, NULL),
(10, 9, 4, 16, 1, 0, NULL),
(11, 9, 4, 17, 1, 0, NULL),
(12, 9, 4, 18, 1, 0, NULL),
(13, 9, 4, 19, 1, 0, NULL),
(14, 9, 4, 20, 1, 0, NULL),
(15, 9, 4, 21, 1, 0, NULL),
(16, 9, 4, 22, 1, 0, NULL),
(17, 9, 4, 23, 1, 0, NULL),
(18, 9, 4, 24, 1, 0, NULL),
(21, 9, 28, 66, 1, 0, NULL),
(23, 9, NULL, 77, 1, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `birth_date` date NOT NULL,
  `gender` varchar(6) NOT NULL,
  `password` longtext NOT NULL,
  `role_id` int(11) DEFAULT '1',
  `pfp_path` longtext NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL,
  `school_admin_id` int(11) DEFAULT NULL,
  `education_id` int(11) NOT NULL,
  `verification_code` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `phone`, `birth_date`, `gender`, `password`, `role_id`, `pfp_path`, `created_at`, `last_login`, `is_deleted`, `deleted_at`, `school_admin_id`, `education_id`, `verification_code`) VALUES
(1, 'Oktató', 'Oktató', 'bzhalmai@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$nZAQi9LzNIfrAf8kZ2U2Yg$zDT3uia/IX+LcyqSRw4zeFHVQ+2FDvhudx9Xk2DPvlg', 3, 'assets\\images\\pfp\\285506.jpg', '2025-10-07 14:06:32', '2026-01-04 11:04:07', 0, NULL, NULL, 1, '$argon2id$v=19$m=4096,t=3,p=1$9sEm/pDkj3C+PB1oVTAayg$sfhSqQPmEsDBntThkwGjuHp/QqVI3ZKt1PY0viRQg6c'),
(2, 'oldalAdmin', 'oldalAdmin', 'oldalAdmin@gmail.com', 'a2', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$+WjzrV34REmyXMe1hy67fA$ojzrAMHfylnhyr+CKoiwZ+pTcQcH8TvPpg8DRUNxDy4', 5, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:06:59', '2026-01-04 10:18:30', 0, NULL, NULL, 1, NULL),
(3, 'Tanuló1', 'Tanuló1', 'bzhalmai1@gmail.com', 'a3', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$Ge1f7T03hPx/S3j/tdh84A$01K36tva/4k4sKm1eoP5ZKtNZrmtS1NdFdh+nMiQq0E', 2, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:07:04', '2026-01-04 12:06:49', 0, NULL, NULL, 1, NULL),
(4, 'Tanuló3', 'Tanuló3', 'bzhalmai3@gmail.com', 'a4', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$TdL1gVgPWH4C8oKzxE4TaQ$nQr1p93ykcnP+7CRcFwG8FyVJt1hzrbwxVotLJ4Qxxw', 2, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:07:09', '2025-12-22 15:26:44', 0, NULL, NULL, 1, NULL),
(5, 'Tanuló4', 'Tanuló4', 'bzhalmai6@gmail.com', 'a5', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$mzu/1vpdf0pCkDW81qN4CQ$8JJGV9sLLOaEuf/jnViXHeuUZMFx/zj2oQX4Wl4IP48', 2, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:07:34', NULL, 0, NULL, NULL, 1, NULL),
(6, 'IskolaTulaj', 'IskolaTulaj', 'bzhalmai4@gmail.com', 'a6', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$D5apy2+dI2lTQW+iK60vGQ$Ta80iOeSgC1bwP9wdH7xbqycZdyBmOwASimmuwDbQYE', 6, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:09:21', NULL, 0, NULL, NULL, 1, NULL),
(7, 'IskolaAdmin1', 'IskolaAdmin1', 'bzhalmai5@gmail.com', 'a7', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$avUr4wjwXvQc6te+mz5EOw$pqOy1ddkcoOL7LBdtvL56aTT48zQdbSVOrGdOKZ2+V8', 4, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:09:52', '2026-01-04 12:12:01', 0, NULL, 2, 1, NULL),
(11, 'iskolaAdmin9', 'iskolaAdmin9', 'iskolaAdmin9@gma.com', '706285232', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$kpyONb+WCWLVH+spf5fIRA$fnT08hEmmWtCSjv+pZuNJd3bDTho0MuqOqQTBidyqSM', 4, 'assets/icons/defaultProfileImg.svg', '2025-11-16 10:34:22', '2026-01-04 12:12:18', 0, NULL, 9, 1, NULL),
(12, 'Iskolatulaj2', 'Iskolatulaj2', 'iskolatulaj2@gmail.com', 'a8', '2000-01-01', 'Gender', 'jelszo', 6, 'assets/icons/defaultProfileImg.svg', '2025-12-04 09:47:44', NULL, 0, NULL, NULL, 1, NULL),
(13, 'oktato2', 'oktato2', 'oktato2@gmail.com', 'a9', '2000-01-01', 'gender', 'password', 3, 'assets/icons/defaultProfileImg.svg', '2025-12-08 09:11:32', NULL, 0, NULL, NULL, 8, NULL),
(14, 'oktato3', 'oktato3', 'oktato3@gmail.com', 'a10', '1990-01-01', 'a', 'jelszo', 3, 'assets/icons/defaultProfileImg.svg', '2025-12-08 09:13:34', NULL, 0, NULL, NULL, 8, NULL),
(15, 'diak1.0', 'diak1.0', 'diak1.0@gmal.com', '706285100', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$0unA9iCWXaKiYoJiwAf6kA$0EqOpuRF28id329sTLbMXDAldYzdar6nOIxh5hZdv5I', 2, 'asd', '2025-12-16 12:15:38', '2026-01-04 12:18:13', 0, NULL, NULL, 1, NULL),
(16, 'diak1.1', 'diak1.1', 'diak1.1@gmal.com', '706285101', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$EaFxZTf+lb6gy08EAULvgQ$KMoappjLlLadU2+NLT1/pI9YDETvhY3O9zU7gAX0Tow', 2, 'asd', '2025-12-16 12:15:49', NULL, 0, NULL, NULL, 1, NULL),
(17, 'diak1.2', 'diak1.2', 'diak1.2@gmal.com', '706285102', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$5FiW6ZO2pmWHvtQ8M/tpTw$OZEKPjCiyPJ068C38AzUo452JVN2Xev2C9PFGs8p/ys', 2, 'asd', '2025-12-16 12:15:59', NULL, 0, NULL, NULL, 1, NULL),
(18, 'diak1.3', 'diak1.3', 'diak1.3@gmal.com', '706285103', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$xwR1zm32Fl8J223HJAF0kg$Ut4ktIw+yfqeUH630VEGyblN68oTSfhK3k/IRb8oFnM', 2, 'asd', '2025-12-16 12:16:06', NULL, 0, NULL, NULL, 1, NULL),
(19, 'diak1.4', 'diak1.4', 'diak1.4@gmal.com', '706285104', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$oCP2iG4BL0XhUjDpYKi4Rw$8w+1MUDOp5ovKnCjqj0HrjtqXRbpqfYL4zYfNEH02so', 2, 'asd', '2025-12-16 12:16:15', NULL, 0, NULL, NULL, 1, NULL),
(20, 'diak1.5', 'diak1.5', 'diak1.5@gmal.com', '706285105', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$JVhUaulm6pAldo1rZF54nA$fwWIt6E+a43RBE8d232hVGdub5x5NJfCn6btGNl5rd8', 2, 'asd', '2025-12-16 12:16:24', NULL, 0, NULL, NULL, 1, NULL),
(21, 'diak1.6', 'diak1.6', 'diak1.6@gmal.com', '706285106', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$4UaUNXwecxF0JxjoH7ezXQ$mk2yttSACD3MNzYD4kgURTnWBrIpzZEWSlW0VZXLj9A', 2, 'asd', '2025-12-16 12:16:32', NULL, 0, NULL, NULL, 1, NULL),
(22, 'diak1.7', 'diak1.7', 'diak1.7@gmal.com', '706285107', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$6A3WiBtchRQQb085yvwF5g$LSkNTxglWDFgvMih7MpCDtypTpheERP0MZWYEBszv9c', 2, 'asd', '2025-12-16 12:16:39', NULL, 0, NULL, NULL, 1, NULL),
(23, 'diak1.8', 'diak1.8', 'diak1.8@gmal.com', '706285108', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$RA4e10YjJpRvjwgWKF+V5Q$v/O97QVpWszqo8HMb6uaDDnDLshvMDf8snkdFCmNqOI', 2, 'asd', '2025-12-16 12:16:48', NULL, 0, NULL, NULL, 1, NULL),
(24, 'diak1.9', 'diak1.9', 'diak1.9@gmal.com', '706285109', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$ZVOHZSY412F2Rt/nTpk3tw$YKRI9kDk8evluEM4boXt6t12gmEF5mhcex8ha76nPkM', 2, 'asd', '2025-12-16 12:16:57', NULL, 0, NULL, NULL, 1, NULL),
(25, 'school_owner_1.0', 'school_owner_1.0', 'school_owner_1.0@gmal.com', '706281000', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$eyihnAPfbrWH2++pG3vhvg$0islaCsSneYg760RiYSKRvyvpzhMk6qtz5YOf2KJwmk', 6, 'asd', '2025-12-16 12:18:00', NULL, 0, NULL, NULL, 1, NULL),
(26, 'school_owner_1.1', 'school_owner_1.1', 'school_owner_1.1@gmal.com', '706281001', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$q49PUdh9mVdHQ10dS0gwug$5sWmV6WdQ4QdJZfdY7ftKt/U/E32ZZiCSTnl5w1QAbM', 6, 'asd', '2025-12-16 12:18:11', NULL, 0, NULL, NULL, 1, NULL),
(27, 'school_owner_1.2', 'school_owner_1.2', 'school_owner_1.2@gmal.com', '706281002', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$rw1ESsDASrKfCfMv5q/O4g$JfJSd1OGyFD1+juOXiUQ6kx4R4bxBFG0zuK2O88GRaM', 6, 'asd', '2025-12-16 12:18:19', NULL, 0, NULL, NULL, 1, NULL),
(28, 'school_owner_1.3', 'school_owner_1.3', 'school_owner_1.3@gmal.com', '706281003', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$oVs/UOVqKhPOin5TGV4CzQ$ui34Co11aj9RQNYCwAN5P63OBSnqKczVQhOUZcXLeUc', 6, 'asd', '2025-12-16 12:18:26', NULL, 0, NULL, NULL, 1, NULL),
(29, 'school_owner_1.4', 'school_owner_1.4', 'school_owner_1.4@gmal.com', '706281004', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$u8Nbb09YOOHU07nA6lPQww$A6YUMs+Hwn8qq9HQxngXRaI4coBZJteh0CkwJhZ9TbI', 6, 'asd', '2025-12-16 12:18:34', NULL, 0, NULL, NULL, 1, NULL),
(30, 'instructor1.0', 'instructor1.0', 'instructor1.0@gmal.com', '706280000', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$PNNmcm12ivaGlCwbjbYTnA$5oY7o5jGSKDDLxckZxr/Sn3H1+qQPWbKGkN0d7N7iy8', 3, 'asd', '2025-12-17 08:56:01', NULL, 0, NULL, NULL, 1, NULL),
(31, 'instructor1.1', 'instructor1.1', 'instructor1.1@gmal.com', '706280001', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$Ru550cH183s3csg47XMpLQ$5zRDQERCeGJV5T1t70Cwci+W54D26yzSaSfKOwe4ieM', 3, 'asd', '2025-12-17 08:56:10', NULL, 0, NULL, NULL, 1, NULL),
(32, 'instructor1.2', 'instructor1.2', 'instructor1.2@gmal.com', '706280002', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$XqbBEgx5BoBDrZM9LU9WnA$yYmux6Jl/Ysb7xPoZzja6clm4mrQGWmi7oH0Y4k99VM', 3, 'asd', '2025-12-17 08:56:18', NULL, 0, NULL, NULL, 1, NULL),
(33, 'instructor1.3', 'instructor1.3', 'instructor1.3@gmal.com', '706280003', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$5GzlKKAslR3riWd+SrTTlA$gf2GoXZWYFxKlowKY6AM8P5mDa1ErmVeHd2tV3fJUMk', 3, 'asd', '2025-12-17 08:56:28', NULL, 0, NULL, NULL, 1, NULL),
(34, 'instructor1.4', 'instructor1.4', 'instructor1.4@gmal.com', '706280004', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$cTSGLLTzBzi2ZAzZkzGR5w$UvMJS403mDFJUybCvfFVssa7Zou0FhIBhJid/C4qnrQ', 3, 'asd', '2025-12-17 08:56:37', NULL, 0, NULL, NULL, 1, NULL),
(35, 'instructor1.5', 'instructor1.5', 'instructor1.5@gmal.com', '706280005', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$xZ5IiLZGmyTQ8KbsSLaR+A$rGRL+yJNveAL2Skx5RAwnI8OuvL7NLOM6qi1RgUTB1I', 3, 'asd', '2025-12-17 08:56:45', NULL, 0, NULL, NULL, 1, NULL),
(36, 'instructor1.6', 'instructor1.6', 'instructor1.6@gmal.com', '706280006', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$+OORfFOiJBIYVlC6jI2qtQ$joyVa3SoPlvytE4HAITT+RjFaAlSsWkhfZ7kPzm4v5w', 3, 'asd', '2025-12-17 08:57:00', NULL, 0, NULL, NULL, 1, NULL),
(37, 'instructor1.7', 'instructor1.7', 'instructor1.7@gmal.com', '706280007', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$6qvrwrNkK31e/pvkLE1IlQ$f+C3ODRNVY4ZCE9f4oZBOn1pq4Naj2yVf1fcviWSfHg', 3, 'asd', '2025-12-17 08:57:11', NULL, 0, NULL, NULL, 1, NULL),
(38, 'instructor1.8', 'instructor1.8', 'instructor1.8@gmal.com', '706280008', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$nHvUo2oZRLzTI0QxEQtmiw$H9AqL/SEv97S9F/1IeL6sWKp+llssYmtgHbUkGa+kvw', 3, 'asd', '2025-12-17 08:57:19', NULL, 0, NULL, NULL, 1, NULL),
(40, 'instructor1.9', 'instructor1.9', 'instructor1.9@gmal.com', '706280009', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$PrzL0H/s3D8J0jSoDyQcqA$o5rAAtfIr9Z+aUQ/6vCg8f64Um0h+HX/ZRJieswrPf0', 3, 'asd', '2025-12-17 08:57:34', NULL, 0, NULL, NULL, 1, NULL),
(41, 'instructor2.0', 'instructor2.0', 'instructor2.0@gmal.com', '706280010', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$tFvPpMhCy72zzEaD+EWBFQ$DCDoMcM7S+DZ8XucF7N/mBhSuL9ojL7B0tvYHxMwg7Q', 3, 'asd', '2025-12-17 08:57:51', NULL, 0, NULL, NULL, 1, NULL),
(42, 'instructor2.1', 'instructor2.1', 'instructor2.1@gmal.com', '706280011', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$l/HFOOPntlHN+GOXEvwVSw$avT+6QvAvTjwcP3f9sFE9/rvre7zYgDRMGatPP5UUUw', 3, 'asd', '2025-12-17 08:57:58', NULL, 0, NULL, NULL, 1, NULL),
(43, 'instructor2.2', 'instructor2.2', 'instructor2.2@gmal.com', '706280012', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$m9D3EF27BrW2mtuS6WgaNA$EUYFHXrlEjsRvb9d89r39dvAy1SkoXu+/M1GJMnLTKA', 3, 'asd', '2025-12-17 08:58:09', NULL, 0, NULL, NULL, 1, NULL),
(44, 'instructor2.3', 'instructor2.3', 'instructor2.3@gmal.com', '706280013', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$mlMbjXa4CWoP7GU21wGdxQ$kixWD7SeJjHo3iXRtiDAtVebnW2KNsweQuX9trO+//g', 3, 'asd', '2025-12-17 08:58:17', NULL, 0, NULL, NULL, 1, NULL),
(45, 'instructor2.4', 'instructor2.4', 'instructor2.4@gmal.com', '706280014', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$coa7GB3N6yohUkbKEqcjjA$sHjaWf5q2F009BmA9R+fAwyTZcbi0YQpjTf+YqJd1xk', 3, 'asd', '2025-12-17 08:58:25', NULL, 0, NULL, NULL, 1, NULL),
(46, 'instructor2.5', 'instructor2.5', 'instructor2.5@gmal.com', '706280015', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$ZoT/Lgq0Yt/3xy/Jc5es2Q$52aG2gnVmZ7roN43lp1eU862Xzo2mnuPrWwdL1+Bx0s', 3, 'asd', '2025-12-17 08:58:33', NULL, 0, NULL, NULL, 1, NULL),
(47, 'instructor2.6', 'instructor2.6', 'instructor2.6@gmal.com', '706280016', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$D2I0QEa4v5HOOdDErqy6Ew$n5aj7HySZTLwNV00NgXwUR/j2+gWySYOjSVoXsAShj0', 3, 'asd', '2025-12-17 08:58:41', NULL, 0, NULL, NULL, 1, NULL),
(48, 'instructor2.7', 'instructor2.7', 'instructor2.7@gmal.com', '706280017', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$h21eN6/cGKInyBhMg9OdJA$JbnBVped+Fnp/3TZY2VSBLHmfD/iX9evJGJycTStW4s', 3, 'asd', '2025-12-17 08:58:53', NULL, 0, NULL, NULL, 1, NULL),
(49, 'instructor2.8', 'instructor2.8', 'instructor2.8@gmal.com', '706280018', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$jtifWw3Zt9sjUBOTwTNGOg$UKoHL6ckrhmfd2dk+tigH4NpNHC+Z2JD4jReWjB3z/Q', 3, 'asd', '2025-12-17 08:59:01', NULL, 0, NULL, NULL, 1, NULL),
(50, 'instructor2.9', 'instructor2.9', 'instructor2.9@gmal.com', '706280019', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$UGyPzs8Y2f2MFAYGW6n9SA$lBogC2av+PrVKzpDQX0yhIfiC8v9Ee2q8MZTFhvsR2o', 3, 'asd', '2025-12-17 08:59:13', NULL, 0, NULL, NULL, 1, NULL),
(51, 'instructor3.0', 'instructor3.0', 'instructor3.0@gmal.com', '706280020', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$0sxOjcwtZ5PBJwbMPDTj9w$kGqSzm3EBSqMJmAX8l97ux7AnpLq8iKu1wWQbBJi3gg', 3, 'asd', '2025-12-17 08:59:33', NULL, 0, NULL, NULL, 1, NULL),
(52, 'instructor3.1', 'instructor3.1', 'instructor3.1@gmal.com', '706280021', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$yPBTabuxKTQg+lAAAG9g9Q$g96Fau6nCmKA3P7RL390IrXMfu0HVZuAglv7Pu2Xo6w', 3, 'asd', '2025-12-17 08:59:45', NULL, 0, NULL, NULL, 1, NULL),
(53, 'instructor3.2', 'instructor3.2', 'instructor3.2@gmal.com', '706280022', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$Een3QfmdkPANOdDG7WaWOw$vFqwkreSHjkuni4mJ0P5dhTyxdDUFRNYgy7gfFj2C/g', 3, 'asd', '2025-12-17 08:59:53', NULL, 0, NULL, NULL, 1, NULL),
(54, 'instructor3.3', 'instructor3.3', 'instructor3.3@gmal.com', '706280023', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$E0Z33BBJRzWeXdLj3+aGWA$/eBlu3dIgNrQI3ZoCaWyozleQ8TWHptjxa4F5uuLweA', 3, 'asd', '2025-12-17 09:00:01', NULL, 0, NULL, NULL, 1, NULL),
(55, 'instructor3.4', 'instructor3.4', 'instructor3.4@gmal.com', '706280024', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$fUIOHCzGnsfc8PNdYOxf8g$lshdHXUVqAkgwaEheUUvQFsO0SR8RX0304z4EU1pCRM', 3, 'asd', '2025-12-17 09:00:09', NULL, 0, NULL, NULL, 1, NULL),
(56, 'instructor3.5', 'instructor3.5', 'instructor3.5@gmal.com', '706280025', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$nX2T8g3R5xPKQFOvw53+Cw$eAeuTWmsk1znXi8+2bddEC6m00bXV8mqX5AqISEyj4U', 3, 'asd', '2025-12-17 09:00:22', NULL, 0, NULL, NULL, 1, NULL),
(57, 'instructor3.6', 'instructor3.6', 'instructor3.6@gmal.com', '706280026', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$NrltaZYToMosVCAYWrzDSg$ylWiZ9cqJKS+JMGOfNtCMj4Vl39f3Qegmvw61a1LzqM', 3, 'asd', '2025-12-17 09:00:32', NULL, 0, NULL, NULL, 1, NULL),
(58, 'instructor3.7', 'instructor3.7', 'instructor3.7@gmal.com', '706280027', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$996KqnKHCewa3NT/gmS9lQ$NzLdBrk0kJhYivZNbby23PMvvJvpS2U4pT9gTBtUh9k', 3, 'asd', '2025-12-17 09:00:41', NULL, 0, NULL, NULL, 1, NULL),
(59, 'instructor3.8', 'instructor3.8', 'instructor3.8@gmal.com', '706280028', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$5tSsDPZvUi+KuUjjIetyGw$LSG82VR4HsZwK4ZWm+ktrDZBtHx7KYvK85TU1lHZIlA', 3, 'asd', '2025-12-17 09:00:50', NULL, 0, NULL, NULL, 1, NULL),
(60, 'instructor3.9', 'instructor3.9', 'instructor3.9@gmal.com', '706280029', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$uMq3Qdk0NaNHjLuhQQRHkw$7XebzKF5X8wqoAAm7NP5yBXD4AUKL3oEaRN798BZfWg', 3, 'asd', '2025-12-17 09:00:57', NULL, 0, NULL, NULL, 1, NULL),
(61, 'instructor4.0', 'instructor4.0', 'instructor4.0@gmal.com', '706280030', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$iQ1yb0yk67c38FkO+KExRA$7jNnvYKFHZYkoyOovG8ZpPnhLM2LBK4y+nrqC/4ZqYE', 3, 'asd', '2025-12-17 09:01:15', NULL, 0, NULL, NULL, 1, NULL),
(62, 'instructor4.1', 'instructor4.1', 'instructor4.1@gmal.com', '706280031', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$4+juZm/maAO/Pz+JeahEZA$oR3zMWr33J9vgwaKg3vlnML1FDXmJcY/f0MQOaV4zgU', 3, 'asd', '2025-12-17 09:01:24', NULL, 0, NULL, NULL, 1, NULL),
(63, 'instructor4.2', 'instructor4.2', 'instructor4.2@gmal.com', '706280032', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$rNgKv8Kbs193M7bNKqdlsQ$6yyT5o8D1ZaXqKpOlk9JyixfbU5e8o+6IUgYD3m/tFk', 3, 'asd', '2025-12-17 09:01:41', NULL, 0, NULL, NULL, 1, NULL),
(64, 'instructor4.3', 'instructor4.3', 'instructor4.3@gmal.com', '706280033', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$820ikLXJEbD+c2FiHlnWfw$+aBfGEpT45f3Yr6BQcyRVCogCoS6/ns/VTlFv3N/JgA', 3, 'asd', '2025-12-17 09:01:55', NULL, 0, NULL, NULL, 1, NULL),
(65, 'instructor4.4', 'instructor4.4', 'instructor4.4@gmal.com', '706280034', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$xn9XxHG4QYbh1+FpmUW7Yw$LrKeNC4AjzPYMklvkBBIqbqyp0hl1eI+lIwgtctY8tM', 3, 'asd', '2025-12-17 09:02:04', NULL, 0, NULL, NULL, 1, NULL),
(66, 'requestTest1', 'requestTest1', 'requestTest1@gmail.com', '206285232', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$AEwlPFrowj7P4whzwTVuGg$cmS5rok7Vxb46QEZm5ugAJ5W1PKdouPvP2D0Uxz0nHo', 2, 'assets/icons/defaultProfileImg.svg', '2025-12-18 09:25:03', '2026-01-03 15:15:29', 0, NULL, NULL, 1, NULL),
(67, 'requestTest2', 'requestTest2', 'requestTest2@gmail.com', '306285232', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$AFzVTJa9FOGUTNk2RnIzhQ$rWlqgw+gy7w+vZN4n2T4ak5Q7QOh+qwO93vJBPqxVQs', 3, 'assets/icons/defaultProfileImg.svg', '2025-12-18 09:25:12', NULL, 0, NULL, NULL, 1, NULL),
(74, 'asd', 'asd', 'asd@gmail.com', '701211818', '2000-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$M5FShDGEtajN78Lm9o7EmA$Ml0A9qVA8Xgo0PKkZjAZH8qM8MdF7rUnFGV5tAiz+6M', 1, '', '2026-01-03 21:05:07', NULL, 0, NULL, NULL, 1, NULL),
(76, 'asd', 'asd', 'asd142a@gmail.com', '701234895', '2026-01-17', 'male', '$argon2id$v=19$m=4096,t=3,p=1$S2bbwCQOpjQhLgLLMiGNxA$EEh9Xi0ASWJC7DrjzBUI5rJIkBtJv6iKR8LxdTY3KkY', 1, '', '2026-01-03 21:11:36', NULL, 0, NULL, NULL, 1, NULL),
(77, 'ujfiok', 'ujfiok', 'ujfiok@gmail.com', '701239876', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$+xrSIWHFjt7dHsZaZbFC6w$XP76wE2Zhmr1+UBuN8h9SY9jCyH4xld9ZVePLq/oAR8', 2, '', '2026-01-04 12:10:53', '2026-01-04 12:22:53', 0, NULL, NULL, 4, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `license_plate` varchar(9) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `fuel_type_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `vehicle`
--

INSERT INTO `vehicle` (`id`, `license_plate`, `name`, `type_id`, `fuel_type_id`, `is_deleted`, `deleted_at`) VALUES
(3, 'AAAA000', 'car_name', 1, 1, 0, NULL),
(4, 'aaa000', 'name', 1, 1, 0, NULL),
(5, 'ABC123', 'Suzuki Ignis', 1, 1, 0, NULL),
(6, 'A', 'oktato_jarmu2', 1, 2, 0, NULL),
(7, 'B', 'oktato_jarmu3', 1, 3, 0, NULL),
(12, 'a2', 'a3', 1, 1, 0, NULL),
(13, 'a1', 'a2', 1, 1, 0, NULL),
(14, 'a3', 'a3', 1, 1, 0, NULL),
(15, 'a5', 'a5', 1, 1, 0, NULL),
(16, NULL, NULL, NULL, NULL, 0, NULL),
(24, NULL, NULL, NULL, NULL, 0, NULL),
(25, NULL, NULL, NULL, NULL, 0, NULL),
(26, NULL, NULL, NULL, NULL, 0, NULL),
(27, NULL, NULL, NULL, NULL, 0, NULL),
(28, 'plate1', 'vehicle_name1', 1, 1, 0, NULL),
(29, 'plate2', 'vehicle_name2', 4, 1, 0, NULL),
(30, 'plate3', 'vehicle_name3', 5, 2, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `vehicle_type`
--

CREATE TABLE `vehicle_type` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `vehicle_type`
--

INSERT INTO `vehicle_type` (`id`, `name`, `is_deleted`, `deleted_at`) VALUES
(1, 'Auto', 1, '2025-11-30 23:00:00'),
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
  ADD KEY `category` (`type_id`),
  ADD KEY `payment` (`payment_method_id`),
  ADD KEY `hour` (`hour_id`),
  ADD KEY `status` (`status_id`),
  ADD KEY `d_instructor` (`instructor_id`),
  ADD KEY `d_students` (`student_id`);

--
-- A tábla indexei `driving_lesson_instructor`
--
ALTER TABLE `driving_lesson_instructor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `lesson_instructor` (`instructor_id`),
  ADD KEY `lesson_type` (`driving_lesson_type_id`);

--
-- A tábla indexei `driving_lesson_request`
--
ALTER TABLE `driving_lesson_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `d_request_student` (`student_id`),
  ADD KEY `d_request_instructor` (`instructor_id`),
  ADD KEY `d_status` (`is_accepted`),
  ADD KEY `d_lesson_type` (`lesson_type_id`);

--
-- A tábla indexei `driving_lesson_type`
--
ALTER TABLE `driving_lesson_type`
  ADD PRIMARY KEY (`id`),
  ADD KEY `d_type` (`license_category_id`),
  ADD KEY `d_school` (`school_id`);

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
-- A tábla indexei `exam_request`
--
ALTER TABLE `exam_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `e_instructor` (`instructor_id`),
  ADD KEY `e_school` (`school_id`),
  ADD KEY `e_student` (`student_id`);

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
  ADD UNIQUE KEY `vehicle_id` (`vehicle_id`),
  ADD KEY `instructor` (`user_id`),
  ADD KEY `school` (`school_id`),
  ADD KEY `vehicle` (`vehicle_id`);

--
-- A tábla indexei `instructor_category`
--
ALTER TABLE `instructor_category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `instructor` (`instructor_id`),
  ADD KEY `category_id` (`driving_license_category_id`);

--
-- A tábla indexei `instructor_join_request`
--
ALTER TABLE `instructor_join_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `instructor_join_student_id` (`student_id`),
  ADD KEY `instructor_join_instructor_id` (`instructor_id`);

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
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD KEY `admin` (`owner_id`);

--
-- A tábla indexei `school_category`
--
ALTER TABLE `school_category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `school_category_id` (`driving_license_category_id`),
  ADD KEY `schoolasd` (`shool_id`);

--
-- A tábla indexei `school_join_request`
--
ALTER TABLE `school_join_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_join_user_id` (`user_id`),
  ADD KEY `student_join_school_id` (`school_id`),
  ADD KEY `school_l_id` (`driving_license_category_id`);

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
  ADD KEY `school_3` (`school_id`),
  ADD KEY `student_license_id` (`driving_license_category_id`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD KEY `role` (`role_id`),
  ADD KEY `school_admin` (`school_admin_id`),
  ADD KEY `education` (`education_id`);

--
-- A tábla indexei `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `license_plate` (`license_plate`),
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT a táblához `driving_lesson_instructor`
--
ALTER TABLE `driving_lesson_instructor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `driving_lesson_request`
--
ALTER TABLE `driving_lesson_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `driving_lesson_type`
--
ALTER TABLE `driving_lesson_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
-- AUTO_INCREMENT a táblához `exam_request`
--
ALTER TABLE `exam_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `fuel_type`
--
ALTER TABLE `fuel_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `instructor`
--
ALTER TABLE `instructor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT a táblához `instructor_category`
--
ALTER TABLE `instructor_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `instructor_join_request`
--
ALTER TABLE `instructor_join_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `opening_detail`
--
ALTER TABLE `opening_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT a táblához `payment_method`
--
ALTER TABLE `payment_method`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `reserved_date`
--
ALTER TABLE `reserved_date`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `reserved_hour`
--
ALTER TABLE `reserved_hour`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT a táblához `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT a táblához `school`
--
ALTER TABLE `school`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT a táblához `school_category`
--
ALTER TABLE `school_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT a táblához `school_join_request`
--
ALTER TABLE `school_join_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT a táblához `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `student`
--
ALTER TABLE `student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;

--
-- AUTO_INCREMENT a táblához `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

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
  ADD CONSTRAINT `category` FOREIGN KEY (`type_id`) REFERENCES `driving_lesson_type` (`id`),
  ADD CONSTRAINT `d_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `d_students` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  ADD CONSTRAINT `hour` FOREIGN KEY (`hour_id`) REFERENCES `reserved_hour` (`id`),
  ADD CONSTRAINT `payment` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
  ADD CONSTRAINT `status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`);

--
-- Megkötések a táblához `driving_lesson_instructor`
--
ALTER TABLE `driving_lesson_instructor`
  ADD CONSTRAINT `lesson_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `lesson_type` FOREIGN KEY (`driving_lesson_type_id`) REFERENCES `driving_lesson_type` (`id`);

--
-- Megkötések a táblához `driving_lesson_request`
--
ALTER TABLE `driving_lesson_request`
  ADD CONSTRAINT `d_lesson_type` FOREIGN KEY (`lesson_type_id`) REFERENCES `driving_lesson_type` (`id`),
  ADD CONSTRAINT `d_request_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `d_request_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

--
-- Megkötések a táblához `driving_lesson_type`
--
ALTER TABLE `driving_lesson_type`
  ADD CONSTRAINT `d_school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `d_type` FOREIGN KEY (`license_category_id`) REFERENCES `driving_license_category` (`id`);

--
-- Megkötések a táblához `exam_request`
--
ALTER TABLE `exam_request`
  ADD CONSTRAINT `e_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `e_school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `e_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

--
-- Megkötések a táblához `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `asd` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `school` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `vehicle` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`);

--
-- Megkötések a táblához `instructor_category`
--
ALTER TABLE `instructor_category`
  ADD CONSTRAINT `category_id` FOREIGN KEY (`driving_license_category_id`) REFERENCES `driving_license_category` (`id`),
  ADD CONSTRAINT `instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`);

--
-- Megkötések a táblához `instructor_join_request`
--
ALTER TABLE `instructor_join_request`
  ADD CONSTRAINT `instructor_join_instructor_id` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `instructor_join_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

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
-- Megkötések a táblához `school_category`
--
ALTER TABLE `school_category`
  ADD CONSTRAINT `school_category_id` FOREIGN KEY (`driving_license_category_id`) REFERENCES `driving_license_category` (`id`),
  ADD CONSTRAINT `schoolasd` FOREIGN KEY (`shool_id`) REFERENCES `school` (`id`);

--
-- Megkötések a táblához `school_join_request`
--
ALTER TABLE `school_join_request`
  ADD CONSTRAINT `school_l_id` FOREIGN KEY (`driving_license_category_id`) REFERENCES `driving_license_category` (`id`),
  ADD CONSTRAINT `student_join_school_id` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `student_join_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `inst` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `school_3` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `student_license_id` FOREIGN KEY (`driving_license_category_id`) REFERENCES `driving_license_category` (`id`),
  ADD CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Megkötések a táblához `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `education` FOREIGN KEY (`education_id`) REFERENCES `education` (`id`),
  ADD CONSTRAINT `role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `school_admin` FOREIGN KEY (`school_admin_id`) REFERENCES `school` (`id`);

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
