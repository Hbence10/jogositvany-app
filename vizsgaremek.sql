-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Gép: localhost:3306
-- Létrehozás ideje: 2025. Dec 16. 11:46
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLesson` (IN `idIN` INT)   BEGIN
	UPDATE `driving_lesson` SET `is_cancelled`= 1 ,`cancelled_at`= CURRENT_DATE() WHERE `driving_lesson`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLessonInstructor` (IN `idIN` INT)   BEGIN
	UPDATE `driving_lesson_instructor` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `driving_lesson_instructor`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLessonRequest` (IN `idIN` INT)   BEGIN
	UPDATE `driving_lesson_request` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `driving_lesson_request`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLessonType` (IN `idIN` INT)   BEGIN
	UPDATE `driving_lesson_type` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `driving_lesson_type`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDrivingLicenseCategory` (IN `idIN` INT)   BEGIN
	UPDATE `driving_license_category` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `driving_license_category`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteEducation` (IN `idIN` INT)   BEGIN
	UPDATE `education` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `education`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteExamRequest` (IN `idIN` INT)   BEGIN
    UPDATE `exam_request` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `exam_request`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteFuelType` (IN `idIN` INT)   BEGIN
    UPDATE `fuel_type` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `fuel_type`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteInstructor` (IN `idIN` INT)   BEGIN
    UPDATE `instructor` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `instructor`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteInstructorJoinRequest` (IN `idIN` INT)   BEGIN
    UPDATE `instructor_join_request` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `instructor_join_request`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteMessage` (IN `idIN` INT)   BEGIN
    UPDATE `message` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `message`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteOpeningDetail` (IN `idIN` INT)   BEGIN
    UPDATE `opening_detail` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `opening_detail`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deletePaymentMethod` (IN `idIN` INT)   BEGIN
    UPDATE `payment_method` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `payment_method`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteReservedDate` (IN `idIN` INT)   BEGIN
    UPDATE `reserved_date` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `reserved_date`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteReservedHour` (IN `idIN` INT)   BEGIN
    UPDATE `reserved_hour` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `reserved_hour`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteReview` (IN `idIN` INT)   BEGIN
    UPDATE `review` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `review`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteRole` (IN `idIN` INT)   BEGIN
    UPDATE `role` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `role`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteSchool` (IN `idIN` INT)   BEGIN
    UPDATE `school` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `school`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteSchoolJoinRequest` (IN `idIN` INT)   BEGIN
    UPDATE `school_join_request` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `school_join_request`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteStatus` (IN `idIN` INT)   BEGIN
    UPDATE `status` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `status`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteStudent` (IN `idIN` INT)   BEGIN
    UPDATE `student` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `student`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteUser` (IN `idIN` INT)   BEGIN
    UPDATE `user` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `user`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteVehicle` (IN `idIN` INT)   BEGIN
    UPDATE `vehicle` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `vehicle`.`id` = idIN;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteVehicleType` (IN `idIN` INT)   BEGIN
    UPDATE `vehicle_type` SET `is_deleted`= 1 ,`deleted_at`= CURRENT_DATE() WHERE `vehicle_type`.`id` = idIN;
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

CREATE DEFINER=`root`@`localhost` PROCEDURE `getInstructorBySearch` (IN `nameIN` VARCHAR(250), IN `fuelTypeIdIN` INT)   BEGIN 
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
    CONCAT(u.first_name, u.last_name) LIKE CONCAT(nameIN, "%")
    ;
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

CREATE DEFINER=`root`@`localhost` PROCEDURE `getSchoolBySearch` (IN `nameIN` VARCHAR(250), IN `townnameIN` VARCHAR(250))   BEGIN
	SELECT s.id FROM school s
    WHERE 
    s.name LIKE CONCAT(name, "%") 
    AND 
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
(1, 0, 40, 'Dombóvár', 'Dombvóvár, Gyöngyvirág krt. 29', 'Dombvóvár, Gyöngyvirág krt. 29', 1, 1, 2, 1, 1, 1, 1, 5, 1, 1, '2025-11-30 23:00:00');

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
  `status_id` int(11) NOT NULL,
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
  `deleted_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `driving_lesson_type`
--

INSERT INTO `driving_lesson_type` (`id`, `name`, `price`, `license_category_id`, `school_id`, `is_deleted`, `deleted_at`) VALUES
(1, 'Vezetési kategória teszt tipus', 6000, 4, 2, 1, '2025-11-30 23:00:00'),
(2, 'alapoktatas', 10000, 6, 3, 0, '2025-12-04 08:50:48');

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
(1, 'Általános Iskola', 1, '2025-11-30 23:00:00'),
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
  `requested_date` date NOT NULL,
  `student_id` int(11) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `exam_request`
--

INSERT INTO `exam_request` (`id`, `instructor_id`, `school_id`, `requested_date`, `student_id`, `is_deleted`, `deleted_at`) VALUES
(1, 1, 2, '2025-10-14', 5, 1, '2025-11-30 23:00:00');

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
(1, 'Benzin', 1, '2025-11-30 23:00:00'),
(2, 'Dízel', 0, NULL),
(3, 'Hibrid', 0, NULL);

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
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `instructor`
--

INSERT INTO `instructor` (`id`, `user_id`, `school_id`, `promo_text`, `vehicle_id`, `is_deleted`, `deleted_at`) VALUES
(1, 1, 2, 'instructor promo text', 4, 1, '2025-11-30 23:00:00'),
(2, 13, 2, 'promotext', 6, 0, NULL),
(3, 14, 3, 'promo', 7, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `instructor_join_request`
--

CREATE TABLE `instructor_join_request` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `instructor_id` int(11) NOT NULL,
  `is_accepted` tinyint(1) DEFAULT '0',
  `accepted_at` timestamp NULL DEFAULT NULL,
  `sended_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `instructor_join_request`
--

INSERT INTO `instructor_join_request` (`id`, `student_id`, `instructor_id`, `is_accepted`, `accepted_at`, `sended_at`, `is_deleted`, `deleted_at`) VALUES
(1, 5, 1, NULL, NULL, '2025-11-16 13:49:13', 0, NULL);

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
  `opening_time` int(2) NOT NULL,
  `close_time` int(2) NOT NULL,
  `day` varchar(100) NOT NULL,
  `school_id` int(10) NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `opening_detail`
--

INSERT INTO `opening_detail` (`id`, `opening_time`, `close_time`, `day`, `school_id`, `is_deleted`, `deleted_at`) VALUES
(2, 10, 17, 'Hétfő', 2, 0, NULL);

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
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `rating` float NOT NULL,
  `instructor_id` int(10) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `review`
--

INSERT INTO `review` (`id`, `author_id`, `text`, `created_at`, `rating`, `instructor_id`, `school_id`, `is_deleted`, `deleted_at`) VALUES
(1, 5, 'Oktató1-ről való tesztadat vélemény', '2025-12-01 09:17:26', 4, 1, NULL, 1, '2025-11-30 23:00:00'),
(2, 5, 'Autósiskola1-ről való teszt vélemény', '2025-10-13 18:24:38', 1, NULL, 2, 0, NULL);

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
(1, 'ROLE_user', 1, '2025-11-30 23:00:00'),
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
(3, 'Iskola2', 'iskola2@gmail.com', 'a2', 'Orszag', 'Varos', 'Cim', 'promo', 'banner', 12, 0, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `school_join_request`
--

CREATE TABLE `school_join_request` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL,
  `requested_role` varchar(10) NOT NULL,
  `is_accepted` tinyint(1) DEFAULT NULL,
  `accepted_at` timestamp NULL DEFAULT NULL,
  `sended_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `school_join_request`
--

INSERT INTO `school_join_request` (`id`, `user_id`, `school_id`, `requested_role`, `is_accepted`, `accepted_at`, `sended_at`, `is_deleted`, `deleted_at`) VALUES
(1, 1, 2, 'student', 0, '2025-11-17 13:43:41', '2025-11-16 13:44:19', 1, '2025-11-30 23:00:00');

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
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `student`
--

INSERT INTO `student` (`id`, `school_id`, `instructor_id`, `user_id`, `is_deleted`, `deleted_at`) VALUES
(5, 2, 1, 2, 0, NULL),
(6, 2, 1, 3, 0, NULL),
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
  `school_administrator_id` int(11) DEFAULT NULL,
  `education_id` int(11) NOT NULL,
  `verification_code` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `phone`, `birth_date`, `gender`, `password`, `role_id`, `pfp_path`, `created_at`, `last_login`, `is_deleted`, `deleted_at`, `school_administrator_id`, `education_id`, `verification_code`) VALUES
(1, 'Oktató', 'Oktató', 'bzhalmai@gmail.com', 'a', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$nZAQi9LzNIfrAf8kZ2U2Yg$zDT3uia/IX+LcyqSRw4zeFHVQ+2FDvhudx9Xk2DPvlg', 3, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:06:32', '2025-11-16 19:34:58', 1, '2025-11-30 23:00:00', NULL, 1, '$argon2id$v=19$m=4096,t=3,p=1$9sEm/pDkj3C+PB1oVTAayg$sfhSqQPmEsDBntThkwGjuHp/QqVI3ZKt1PY0viRQg6c'),
(2, 'Tanuló2', 'Tanuló2', 'bzhalmai2@gmail.com', 'a2', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$+WjzrV34REmyXMe1hy67fA$ojzrAMHfylnhyr+CKoiwZ+pTcQcH8TvPpg8DRUNxDy4', 2, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:06:59', '2025-11-16 19:49:42', 0, NULL, NULL, 1, NULL),
(3, 'Tanuló1', 'Tanuló1', 'bzhalmai1@gmail.com', 'a3', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$Ge1f7T03hPx/S3j/tdh84A$01K36tva/4k4sKm1eoP5ZKtNZrmtS1NdFdh+nMiQq0E', 2, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:07:04', NULL, 0, NULL, NULL, 1, NULL),
(4, 'Tanuló3', 'Tanuló3', 'bzhalmai3@gmail.com', 'a4', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$TdL1gVgPWH4C8oKzxE4TaQ$nQr1p93ykcnP+7CRcFwG8FyVJt1hzrbwxVotLJ4Qxxw', 2, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:07:09', NULL, 0, NULL, NULL, 1, NULL),
(5, 'Tanuló4', 'Tanuló4', 'bzhalmai6@gmail.com', 'a5', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$mzu/1vpdf0pCkDW81qN4CQ$8JJGV9sLLOaEuf/jnViXHeuUZMFx/zj2oQX4Wl4IP48', 2, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:07:34', NULL, 0, NULL, NULL, 1, NULL),
(6, 'IskolaTulaj', 'IskolaTulaj', 'bzhalmai4@gmail.com', 'a6', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$D5apy2+dI2lTQW+iK60vGQ$Ta80iOeSgC1bwP9wdH7xbqycZdyBmOwASimmuwDbQYE', 6, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:09:21', NULL, 0, NULL, NULL, 1, NULL),
(7, 'IskolaAdmin1', 'IskolaAdmin1', 'bzhalmai5@gmail.com', 'a7', '2006-08-02', 'a', '$argon2id$v=19$m=4096,t=3,p=1$avUr4wjwXvQc6te+mz5EOw$pqOy1ddkcoOL7LBdtvL56aTT48zQdbSVOrGdOKZ2+V8', 4, 'assets/icons/defaultProfileImg.svg', '2025-10-07 14:09:52', '2025-11-16 19:18:46', 0, NULL, 2, 1, NULL),
(11, 'firstName', 'lastName', 'sulisdolgok8@gma.com', '706285232', '2006-08-02', 'male', '$argon2id$v=19$m=4096,t=3,p=1$kpyONb+WCWLVH+spf5fIRA$fnT08hEmmWtCSjv+pZuNJd3bDTho0MuqOqQTBidyqSM', 1, 'assets/icons/defaultProfileImg.svg', '2025-11-16 10:34:22', NULL, 1, '2025-11-16 12:12:05', NULL, 1, NULL),
(12, 'Iskolatulaj2', 'Iskolatulaj2', 'iskolatulaj2@gmail.com', 'a8', '2000-01-01', 'Gender', 'jelszo', 6, 'assets/icons/defaultProfileImg.svg', '2025-12-04 09:47:44', NULL, 0, NULL, NULL, 1, NULL),
(13, 'oktato2', 'oktato2', 'oktato2@gmail.com', 'a9', '2000-01-01', 'gender', 'password', 3, 'assets/icons/defaultProfileImg.svg', '2025-12-08 09:11:32', NULL, 0, NULL, NULL, 8, NULL),
(14, 'oktato3', 'oktato3', 'oktato3@gmail.com', 'a10', '1990-01-01', 'a', 'jelszo', 3, 'assets/icons/defaultProfileImg.svg', '2025-12-08 09:13:34', NULL, 0, NULL, NULL, 8, NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `vehicle`
--

CREATE TABLE `vehicle` (
  `id` int(11) NOT NULL,
  `license_plate` varchar(9) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type_id` int(11) NOT NULL,
  `fuel_type_id` int(11) NOT NULL,
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
(7, 'B', 'oktato_jarmu3', 1, 3, 0, NULL);

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
  ADD KEY `d_status` (`status_id`);

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
-- A tábla indexei `school_join_request`
--
ALTER TABLE `school_join_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `student_join_user_id` (`user_id`),
  ADD KEY `student_join_school_id` (`school_id`);

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
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD KEY `role` (`role_id`),
  ADD KEY `school_admin` (`school_administrator_id`),
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `instructor_join_request`
--
ALTER TABLE `instructor_join_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `opening_detail`
--
ALTER TABLE `opening_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT a táblához `school`
--
ALTER TABLE `school`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT a táblához `school_join_request`
--
ALTER TABLE `school_join_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `student`
--
ALTER TABLE `student`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT a táblához `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

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
  ADD CONSTRAINT `d_request_instructor` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
  ADD CONSTRAINT `d_request_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  ADD CONSTRAINT `d_status` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`);

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
-- Megkötések a táblához `school_join_request`
--
ALTER TABLE `school_join_request`
  ADD CONSTRAINT `student_join_school_id` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`),
  ADD CONSTRAINT `student_join_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

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
  ADD CONSTRAINT `education` FOREIGN KEY (`education_id`) REFERENCES `education` (`id`),
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
