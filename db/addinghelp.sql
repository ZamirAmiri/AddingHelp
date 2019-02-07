-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Gegenereerd op: 08 dec 2018 om 12:37
-- Serverversie: 10.1.34-MariaDB
-- PHP-versie: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `addinghelp`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `ActiveUsers`
--

CREATE TABLE `ActiveUsers` (
  `userId` int(11) NOT NULL,
  `session` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `ActiveUsers`
--

INSERT INTO `ActiveUsers` (`userId`, `session`) VALUES
(1, 'fd1ad504-faa3-4808-b21d-a9627de45836');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Countries`
--

CREATE TABLE `Countries` (
  `code` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `Countries`
--

INSERT INTO `Countries` (`code`, `name`) VALUES
(89, 'Afghanistan'),
(90, 'Albania'),
(91, 'Algeria'),
(92, 'Andorra'),
(93, 'Angola'),
(94, 'Anguilla'),
(95, 'Antigua & Barbuda'),
(96, 'Argentina'),
(97, 'Armenia'),
(98, 'Australia'),
(99, 'Austria'),
(100, 'Azerbaijan'),
(101, 'Bahamas'),
(102, 'Bahrain'),
(103, 'Bangladesh'),
(104, 'Barbados'),
(105, 'Belarus'),
(106, 'Belgium'),
(107, 'Belize'),
(108, 'Benin'),
(109, 'Bermuda'),
(110, 'Bhutan'),
(111, 'Bolivia'),
(112, 'Bosnia & Herzegovina'),
(113, 'Botswana'),
(114, 'Brazil'),
(115, 'Brunei Darussalam'),
(116, 'Bulgaria'),
(117, 'Burkina Faso'),
(119, 'Burundi'),
(120, 'Cambodia'),
(121, 'Cameroon'),
(122, 'Canada'),
(123, 'Cape Verde'),
(124, 'Cayman Islands'),
(125, 'Central African Republic'),
(126, 'Chad'),
(127, 'Chile'),
(128, 'China'),
(129, 'Colombia'),
(130, 'Comoros'),
(131, 'Congo'),
(132, 'Costa Rica'),
(133, 'Croatia'),
(134, 'Cuba'),
(135, 'Cyprus'),
(136, 'Czech Republic'),
(137, 'Democratic Republic of the Congo'),
(138, 'Denmark'),
(139, 'Djibouti'),
(140, 'Dominica'),
(141, 'Dominican Republic'),
(142, 'Ecuador'),
(143, 'Egypt'),
(144, 'El Salvador'),
(145, 'Equatorial Guinea'),
(146, 'Eritrea'),
(147, 'Estonia'),
(148, 'Ethiopia'),
(149, 'Fiji'),
(150, 'Finland'),
(151, 'France'),
(152, 'French Guiana'),
(153, 'Gabon'),
(154, 'Gambia'),
(155, 'Georgia'),
(156, 'Germany'),
(157, 'Ghana'),
(158, 'Great Britain'),
(159, 'Greece'),
(160, 'Grenada'),
(161, 'Guadeloupe'),
(162, 'Guatemala'),
(163, 'Guinea'),
(164, 'Guinea-Bissau'),
(165, 'Guyana'),
(166, 'Haiti'),
(167, 'Honduras'),
(168, 'Hungary'),
(169, 'Iceland'),
(170, 'India'),
(171, 'Indonesia'),
(172, 'Iran'),
(173, 'Iraq'),
(174, 'Israel and the Occupied Territories'),
(175, 'Italy'),
(176, 'Ivory Coast'),
(177, 'Jamaica'),
(178, 'Japan'),
(179, 'Jordan'),
(180, 'Kazakhstan'),
(181, 'Kenya'),
(182, 'Kosovo'),
(183, 'Kuwait'),
(184, 'Kyrgyz Republic (Kyrgyzstan)'),
(185, 'Laos'),
(186, 'Latvia'),
(187, 'Lebanon'),
(188, 'Lesotho'),
(189, 'Liberia'),
(190, 'Libya'),
(191, 'Liechtenstein'),
(192, 'Lithuania'),
(193, 'Luxembourg'),
(195, 'Madagascar'),
(196, 'Malawi'),
(197, 'Malaysia'),
(198, 'Maldives'),
(199, 'Mali'),
(200, 'Malta'),
(201, 'Martinique'),
(202, 'Mauritania'),
(203, 'Mauritius'),
(204, 'Mayotte'),
(205, 'Mexico'),
(206, 'Moldova'),
(207, 'Monaco'),
(208, 'Mongolia'),
(209, 'Montenegro'),
(210, 'Montserrat'),
(211, 'Morocco'),
(212, 'Mozambique'),
(118, 'Myanmar'),
(213, 'Namibia'),
(214, 'Nepal'),
(215, 'Netherlands'),
(216, 'New Zealand'),
(217, 'Nicaragua'),
(218, 'Niger'),
(219, 'Nigeria'),
(220, 'North Korea'),
(221, 'Norway'),
(222, 'Oman'),
(223, 'Pacific Islands'),
(224, 'Pakistan'),
(225, 'Panama'),
(226, 'Papua New Guinea'),
(227, 'Paraguay'),
(228, 'Peru'),
(229, 'Philippines'),
(230, 'Poland'),
(231, 'Portugal'),
(232, 'Puerto Rico'),
(233, 'Qatar'),
(194, 'Republic of Macedonia'),
(234, 'Reunion'),
(235, 'Romania'),
(236, 'Russian Federation'),
(237, 'Rwanda'),
(238, 'Saint Kitts and Nevis'),
(239, 'Saint Lucia'),
(240, 'Saint Vincent\'s & Grenadines'),
(241, 'Samoa'),
(242, 'Sao Tome and Principe'),
(243, 'Saudi Arabia'),
(244, 'Senegal'),
(245, 'Serbia'),
(246, 'Seychelles'),
(247, 'Sierra Leone'),
(248, 'Singapore'),
(249, 'Slovakia'),
(250, 'Slovenia'),
(251, 'Solomon Islands'),
(252, 'Somalia'),
(253, 'South Africa'),
(254, 'South Korea'),
(255, 'South Sudan'),
(256, 'Spain'),
(257, 'Sri Lanka'),
(258, 'Sudan'),
(259, 'Suriname'),
(260, 'Swaziland'),
(261, 'Sweden'),
(262, 'Switzerland'),
(263, 'Syria'),
(264, 'Tajikistan'),
(265, 'Tanzania'),
(266, 'Thailand'),
(267, 'Timor Leste'),
(268, 'Togo'),
(269, 'Trinidad & Tobago'),
(270, 'Tunisia'),
(271, 'Turkey'),
(272, 'Turkmenistan'),
(273, 'Turks & Caicos Islands'),
(274, 'Uganda'),
(275, 'Ukraine'),
(276, 'United Arab Emirates'),
(277, 'United States of America'),
(278, 'Uruguay'),
(279, 'Uzbekistan'),
(280, 'Venezuela'),
(281, 'Vietnam'),
(282, 'Virgin Islands (UK)'),
(283, 'Virgin Islands (US)'),
(284, 'Yemen'),
(285, 'Zambia'),
(286, 'Zimbabwe');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Donations`
--

CREATE TABLE `Donations` (
  `user` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `helpcoins` int(6) NOT NULL,
  `project` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `FoundationPictures`
--

CREATE TABLE `FoundationPictures` (
  `code` varchar(256) NOT NULL,
  `foundation` int(11) NOT NULL,
  `post` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Foundations`
--

CREATE TABLE `Foundations` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `country` int(11) NOT NULL,
  `gps` varchar(50) NOT NULL,
  `info` text NOT NULL,
  `logo` text NOT NULL,
  `type` varchar(10) NOT NULL,
  `reward` text NOT NULL,
  `website` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Posts`
--

CREATE TABLE `Posts` (
  `id` int(11) NOT NULL,
  `short` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `title` varchar(100) NOT NULL,
  `foundation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Projects`
--

CREATE TABLE `Projects` (
  `id` int(11) NOT NULL,
  `foundation` int(11) NOT NULL,
  `goal` int(11) NOT NULL,
  `helpcoins` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `creationdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `completiondate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Propositions`
--

CREATE TABLE `Propositions` (
  `foundation` int(11) NOT NULL,
  `helpcoins` int(11) NOT NULL,
  `info` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Subscriptions`
--

CREATE TABLE `Subscriptions` (
  `user_one` int(11) NOT NULL,
  `use_two` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `UnverifiedUsers`
--

CREATE TABLE `UnverifiedUsers` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `birthdate` date NOT NULL,
  `registrationdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `activationcode` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `birthdate` date NOT NULL,
  `helpcoins` int(6) NOT NULL DEFAULT '0',
  `accumulated` int(9) NOT NULL DEFAULT '0',
  `picture` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `Users`
--

INSERT INTO `Users` (`id`, `username`, `password`, `email`, `gender`, `birthdate`, `helpcoins`, `accumulated`, `picture`) VALUES
(1, 'zamiri', 'lol123', 'zamirboy88@hotmail.com', 'M', '1994-03-06', 5, 5, NULL),
(2, 'shair', 'lol123', 'shair88@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(3, 'tester', 'lol123', 'tester88@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(4, 'toaster', 'lol123', 'toeaster88@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(5, 'tester0', 'lol123', 'toaster0@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(6, 'tester1', 'lol123', 'toaster1@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(7, 'tester2', 'lol123', 'toaster2@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(8, 'tester3', 'lol123', 'toaster3@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(9, 'tester4', 'lol123', 'toaster4@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(10, 'tester5', 'lol123', 'toaster5@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(11, 'tester6', 'lol123', 'toaster6@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(12, 'tester7', 'lol123', 'toaster7@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(13, 'tester8', 'lol123', 'toaster8@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(14, 'tester9', 'lol123', 'toaster9@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(15, 'tester10', 'lol123', 'toaster10@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(16, 'tester11', 'lol123', 'toaster11@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(17, 'tester12', 'lol123', 'toaster12@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(18, 'tester13', 'lol123', 'toaster13@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(19, 'tester14', 'lol123', 'toaster14@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(20, 'tester15', 'lol123', 'toaster15@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(21, 'tester16', 'lol123', 'toaster16@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(22, 'tester17', 'lol123', 'toaster17@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(23, 'tester18', 'lol123', 'toaster18@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(24, 'tester19', 'lol123', 'toaster19@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(25, 'tester20', 'lol123', 'toaster20@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(26, 'tester21', 'lol123', 'toaster21@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(27, 'tester22', 'lol123', 'toaster22@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(28, 'tester23', 'lol123', 'toaster23@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(29, 'tester24', 'lol123', 'toaster24@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(30, 'tester25', 'lol123', 'toaster25@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(31, 'tester26', 'lol123', 'toaster26@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(32, 'tester27', 'lol123', 'toaster27@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(33, 'tester28', 'lol123', 'toaster28@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(34, 'tester29', 'lol123', 'toaster29@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(35, 'tester30', 'lol123', 'toaster30@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(36, 'tester31', 'lol123', 'toaster31@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(37, 'tester32', 'lol123', 'toaster32@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(38, 'tester33', 'lol123', 'toaster33@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(39, 'tester34', 'lol123', 'toaster34@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(40, 'tester35', 'lol123', 'toaster35@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(41, 'tester36', 'lol123', 'toaster36@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(42, 'tester37', 'lol123', 'toaster37@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(43, 'tester38', 'lol123', 'toaster38@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(44, 'tester39', 'lol123', 'toaster39@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(45, 'tester40', 'lol123', 'toaster40@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(46, 'tester41', 'lol123', 'toaster41@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(47, 'tester42', 'lol123', 'toaster42@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(48, 'tester43', 'lol123', 'toaster43@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(49, 'tester44', 'lol123', 'toaster44@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(50, 'tester45', 'lol123', 'toaster45@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(51, 'tester46', 'lol123', 'toaster46@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(52, 'tester47', 'lol123', 'toaster47@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(53, 'tester48', 'lol123', 'toaster48@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(54, 'tester49', 'lol123', 'toaster49@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(55, 'tester50', 'lol123', 'toaster50@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(56, 'tester51', 'lol123', 'toaster51@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(57, 'tester52', 'lol123', 'toaster52@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(58, 'tester53', 'lol123', 'toaster53@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(59, 'tester54', 'lol123', 'toaster54@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(60, 'tester55', 'lol123', 'toaster55@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(61, 'tester56', 'lol123', 'toaster56@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(62, 'tester57', 'lol123', 'toaster57@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(63, 'tester58', 'lol123', 'toaster58@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(64, 'tester59', 'lol123', 'toaster59@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(65, 'tester60', 'lol123', 'toaster60@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(66, 'tester61', 'lol123', 'toaster61@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(67, 'tester62', 'lol123', 'toaster62@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(68, 'tester63', 'lol123', 'toaster63@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(69, 'tester64', 'lol123', 'toaster64@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(70, 'tester65', 'lol123', 'toaster65@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(71, 'tester66', 'lol123', 'toaster66@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(72, 'tester67', 'lol123', 'toaster67@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(73, 'tester68', 'lol123', 'toaster68@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(74, 'tester69', 'lol123', 'toaster69@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(75, 'tester70', 'lol123', 'toaster70@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(76, 'tester71', 'lol123', 'toaster71@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(77, 'tester72', 'lol123', 'toaster72@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(78, 'tester73', 'lol123', 'toaster73@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(79, 'tester74', 'lol123', 'toaster74@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(80, 'tester75', 'lol123', 'toaster75@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(81, 'tester76', 'lol123', 'toaster76@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(82, 'tester77', 'lol123', 'toaster77@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(83, 'tester78', 'lol123', 'toaster78@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(84, 'tester79', 'lol123', 'toaster79@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(85, 'tester80', 'lol123', 'toaster80@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(86, 'tester81', 'lol123', 'toaster81@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(87, 'tester82', 'lol123', 'toaster82@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(88, 'tester83', 'lol123', 'toaster83@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(89, 'tester84', 'lol123', 'toaster84@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(90, 'tester85', 'lol123', 'toaster85@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(91, 'tester86', 'lol123', 'toaster86@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(92, 'tester87', 'lol123', 'toaster87@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(93, 'tester88', 'lol123', 'toaster88@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(94, 'tester89', 'lol123', 'toaster89@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(95, 'tester90', 'lol123', 'toaster90@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(96, 'tester91', 'lol123', 'toaster91@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(97, 'tester92', 'lol123', 'toaster92@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(98, 'tester93', 'lol123', 'toaster93@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(99, 'tester94', 'lol123', 'toaster94@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(100, 'tester95', 'lol123', 'toaster95@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(101, 'tester96', 'lol123', 'toaster96@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(102, 'tester97', 'lol123', 'toaster97@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(103, 'tester98', 'lol123', 'toaster98@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(104, 'tester99', 'lol123', 'toaster99@hotmail.com', 'M', '1994-03-06', 0, 0, NULL),
(107, 'sdsa', 'asdasd', 'z@w.com', 'M', '2018-10-03', 0, 0, NULL);

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `ActiveUsers`
--
ALTER TABLE `ActiveUsers`
  ADD UNIQUE KEY `session` (`session`),
  ADD UNIQUE KEY `user` (`userId`);

--
-- Indexen voor tabel `Countries`
--
ALTER TABLE `Countries`
  ADD PRIMARY KEY (`code`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexen voor tabel `Donations`
--
ALTER TABLE `Donations`
  ADD UNIQUE KEY `user` (`user`,`date`);

--
-- Indexen voor tabel `FoundationPictures`
--
ALTER TABLE `FoundationPictures`
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexen voor tabel `Foundations`
--
ALTER TABLE `Foundations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`,`gps`);

--
-- Indexen voor tabel `Posts`
--
ALTER TABLE `Posts`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `Projects`
--
ALTER TABLE `Projects`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `Propositions`
--
ALTER TABLE `Propositions`
  ADD UNIQUE KEY `foundation` (`foundation`,`helpcoins`);

--
-- Indexen voor tabel `Subscriptions`
--
ALTER TABLE `Subscriptions`
  ADD UNIQUE KEY `user_one` (`user_one`,`use_two`);

--
-- Indexen voor tabel `UnverifiedUsers`
--
ALTER TABLE `UnverifiedUsers`
  ADD UNIQUE KEY `name` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexen voor tabel `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `name` (`username`);

--
-- AUTO_INCREMENT voor geëxporteerde tabellen
--

--
-- AUTO_INCREMENT voor een tabel `Countries`
--
ALTER TABLE `Countries`
  MODIFY `code` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=287;

--
-- AUTO_INCREMENT voor een tabel `Foundations`
--
ALTER TABLE `Foundations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `Posts`
--
ALTER TABLE `Posts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `Projects`
--
ALTER TABLE `Projects`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT voor een tabel `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
