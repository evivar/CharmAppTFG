-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 20-04-2020 a las 12:42:19
-- Versión del servidor: 10.1.44-MariaDB-0ubuntu0.18.04.1
-- Versión de PHP: 7.2.24-0ubuntu0.18.04.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cluster_devel`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `stations`
--

CREATE TABLE `stations` (
  `station_id` varchar(9) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `station_url_id` varchar(9) CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `city` varchar(20) CHARACTER SET latin1 COLLATE latin1_spanish_ci DEFAULT NULL,
  `country` varchar(2) CHARACTER SET latin1 COLLATE latin1_spanish_ci DEFAULT NULL,
  `web_source` enum('OWM','CAM','AQICN') CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `type` enum('meteo','pollution') CHARACTER SET latin1 COLLATE latin1_spanish_ci NOT NULL,
  `longitude` decimal(8,5) NOT NULL,
  `lat` decimal(7,5) NOT NULL,
  `altitude` int(4) DEFAULT NULL,
  `comment` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `stations`
--

INSERT INTO `stations` (`station_id`, `station_url_id`, `city`, `country`, `web_source`, `type`, `longitude`, `lat`, `altitude`, `comment`) VALUES
('10529', '10529', 'Fernández Ladreda', 'ES', 'AQICN', 'pollution', '-3.71861', '40.38472', NULL, 'https://aqicn.org/city/spain/madrid/fernandez-ladreda'),
('28005002', '3', 'Alcalá de Henares', 'ES', 'CAM', 'pollution', '-3.37795', '40.47933', 595, 'TIPO ZONA:Tráfico'),
('28006004', '4', 'Alcobendas', 'ES', 'CAM', 'pollution', '-3.64646', '40.53952', 688, 'TIPO ZONA:Tráfico'),
('28007004', '8', 'Alcorcón', 'ES', 'CAM', 'pollution', '-3.83574', '40.34191', 693, 'TIPO ZONA:Fondo urbano'),
('28009001', '20', 'Algete', 'ES', 'CAM', 'pollution', '-3.50328', '40.59981', 800, 'TIPO ZONA:Fondo urbano'),
('28013002', '13', 'Aranjuez', 'ES', 'CAM', 'pollution', '-3.59164', '40.03328', 501, 'TIPO ZONA:Fondo urbano'),
('28014002', '15', 'Arganda del Rey', 'ES', 'CAM', 'pollution', '-3.45883', '40.30069', 586, 'TIPO ZONA:Industrial'),
('28016001', '22', 'El Atazar', 'ES', 'CAM', 'pollution', '-3.46790', '40.90901', 995, 'TIPO ZONA:Rural de fondo'),
('28045002', '11', 'Colmenar Viejo', 'ES', 'CAM', 'pollution', '-3.77386', '40.66465', 905, 'TIPO ZONA:Tráfico'),
('28047002', '14', 'Collado Villalba', 'ES', 'CAM', 'pollution', '-4.01425', '40.63309', 873, 'TIPO ZONA:Tráfico'),
('28049003', '9', 'Coslada', 'ES', 'CAM', 'pollution', '-3.54280', '40.43099', 621, 'TIPO ZONA:Fondo urbano'),
('28058004', '5', 'Fuenlabrada', 'ES', 'CAM', 'pollution', '-3.80095', '40.28151', 699, 'TIPO ZONA:Industrial'),
('28065014', '1', 'Getafe', 'ES', 'CAM', 'pollution', '-3.71687', '40.31452', 667, 'TIPO ZONA:Tráfico'),
('28067001', '19', 'Guadalix de la Sierr', 'ES', 'CAM', 'pollution', '-3.70214', '40.78063', 853, 'TIPO ZONA:Rural'),
('28074007', '2', 'Leganés', 'ES', 'CAM', 'pollution', '-3.75451', '40.33976', 676, 'TIPO ZONA:Tráfico'),
('28080003', '12', 'Majadahonda', 'ES', 'CAM', 'pollution', '-3.86899', '40.44610', 360, 'TIPO ZONA:Fondo urbano'),
('28092005', '6', 'Móstoles', 'ES', 'CAM', 'pollution', '-3.87677', '40.32422', 660, 'TIPO ZONA:Fondo urbano'),
('28102001', '24', 'Orusco de Tajuña', 'ES', 'CAM', 'pollution', '-3.22109', '40.28756', 800, 'TIPO ZONA:Rural de fondo'),
('28120001', '110', 'Rascafría', 'ES', 'CAM', 'pollution', '-3.96120', '40.82510', 1870, 'TIPO ZONA:Rural de fondo'),
('28123002', '18', 'Rivas Vaciamadrid', 'ES', 'CAM', 'pollution', '-3.54290', '40.35970', 608, 'TIPO ZONA:Fondo urbano'),
('28133002', '17', 'San Martín de Valdei', 'ES', 'CAM', 'pollution', '-4.39811', '40.36776', 549, 'TIPO ZONA:Rural'),
('28148004', '7', 'Torrejón de Ardoz', 'ES', 'CAM', 'pollution', '-3.47764', '40.44954', 597, 'TIPO ZONA:Fondo urbano'),
('28161001', '21', 'Valdemoro', 'ES', 'CAM', 'pollution', '-3.68026', '40.18524', 615, 'TIPO ZONA:Fondo urbano'),
('28171001', '23', 'Villa del Prado', 'ES', 'CAM', 'pollution', '-4.27515', '40.24794', 510, 'TIPO ZONA:Rural de fondo'),
('28180001', '16', 'Villarejo de Salvané', 'ES', 'CAM', 'pollution', '-3.27656', '40.16722', 764, 'TIPO ZONA:Tráfico'),
('3104703', '3104703', 'Villaviciosa de Odon', 'ES', 'OWM', 'meteo', '-3.90000', '40.36000', 0, ''),
('3106054', '3106054', 'Vicalvaro', 'ES', 'OWM', 'meteo', '-3.60000', '40.40000', 0, ''),
('3106601', '3106601', 'Vallecas', 'ES', 'OWM', 'meteo', '-3.62000', '40.38000', 0, ''),
('3106868', '3106868', 'Valdemoro', 'ES', 'OWM', 'meteo', '-3.68000', '40.19000', 0, ''),
('3107112', '3107112', 'Vaciamadrid', 'ES', 'OWM', 'meteo', '-3.51000', '40.33000', 0, ''),
('3107320', '3107320', 'Ugena', 'ES', 'OWM', 'meteo', '-3.88000', '40.16000', 0, ''),
('3107781', '3107781', 'Torrejon de Velasco', 'ES', 'OWM', 'meteo', '-3.78000', '40.19000', 0, ''),
('3107783', '3107783', 'Torrejon de la Calz', 'ES', 'OWM', 'meteo', '-3.80000', '40.20000', 0, ''),
('3108725', '3108725', 'Somosaguas', 'ES', 'OWM', 'meteo', '-3.79000', '40.42000', 0, ''),
('3109018', '3109018', 'Sevilla La Nueva', 'ES', 'OWM', 'meteo', '-4.03000', '40.35000', 0, ''),
('3109077', '3109077', 'Serranillos del Val', 'ES', 'OWM', 'meteo', '-3.88000', '40.20000', 0, ''),
('3110040', '3110040', 'San Sebastian de lo', 'ES', 'OWM', 'meteo', '-3.62000', '40.54000', 0, ''),
('3110627', '3110627', 'San Fernando de Hena', 'ES', 'OWM', 'meteo', '-3.53000', '40.42000', 0, ''),
('3111394', '3111394', 'Romanillos', 'ES', 'OWM', 'meteo', '-3.93000', '40.44000', 0, ''),
('3112772', '3112772', 'Pueblo Nuevo-Ventas', 'ES', 'OWM', 'meteo', '-3.65000', '40.44000', 0, ''),
('3112989', '3112989', 'Pozuelo de Alarcon', 'ES', 'OWM', 'meteo', '-3.81000', '40.43000', 0, ''),
('3113415', '3113415', 'Pinto', 'ES', 'OWM', 'meteo', '-3.70000', '40.24000', 0, ''),
('3113803', '3113803', 'Perales del Río', 'ES', 'OWM', 'meteo', '-3.64000', '40.32000', 0, ''),
('3113943', '3113943', 'Peña Grande', 'ES', 'OWM', 'meteo', '-3.72000', '40.48000', 0, ''),
('3114256', '3114256', 'Parla', 'ES', 'OWM', 'meteo', '-3.77000', '40.24000', 0, ''),
('3115659', '3115659', 'Navalcarnero', 'ES', 'OWM', 'meteo', '-4.01000', '40.29000', 0, ''),
('3116025', '3116025', 'Mostoles', 'ES', 'OWM', 'meteo', '-3.87000', '40.32000', 0, ''),
('3116156', '3116156', 'Moratalaz', 'ES', 'OWM', 'meteo', '-3.65000', '40.42000', 0, ''),
('3116191', '3116191', 'Moraleja de Enmedio', 'ES', 'OWM', 'meteo', '-3.86000', '40.26000', 0, ''),
('3117667', '3117667', 'Majadahonda', 'ES', 'OWM', 'meteo', '-3.87000', '40.47000', 0, ''),
('3117732', '3117732', 'Comunidad de Madrid', 'ES', 'OWM', 'meteo', '-3.69000', '40.43000', 0, ''),
('3117735', '3117735', 'Madrid', 'ES', 'OWM', 'meteo', '-3.70000', '40.42000', 0, ''),
('3118594', '3118594', 'Leganes', 'ES', 'OWM', 'meteo', '-3.76000', '40.33000', 0, ''),
('3118848', '3118848', 'Las Rozas de Madrid', 'ES', 'OWM', 'meteo', '-3.87000', '40.49000', 0, ''),
('3119765', '3119765', 'La Estación', 'ES', 'OWM', 'meteo', '-3.80000', '40.45000', 0, ''),
('3119903', '3119903', 'La Cañada', 'ES', 'OWM', 'meteo', '-3.53000', '40.42000', 0, ''),
('3120019', '3120019', 'La Aldehuela', 'ES', 'OWM', 'meteo', '-3.59000', '40.30000', 0, ''),
('3120030', '3120030', 'La Alameda de Osuna', 'ES', 'OWM', 'meteo', '-3.58000', '40.45000', 0, ''),
('3120499', '3120499', 'Húmera', 'ES', 'OWM', 'meteo', '-3.78000', '40.43000', 0, ''),
('3120501', '3120501', 'Humanes de Madrid', 'ES', 'OWM', 'meteo', '-3.83000', '40.25000', 0, ''),
('3121105', '3121105', 'Grinon', 'ES', 'OWM', 'meteo', '-3.85000', '40.21000', 0, ''),
('3121437', '3121437', 'Getafe', 'ES', 'OWM', 'meteo', '-3.73000', '40.31000', 0, ''),
('3121960', '3121960', 'Fuenlabrada', 'ES', 'OWM', 'meteo', '-3.79000', '40.28000', 0, ''),
('3123115', '3123115', 'Entrevías', 'ES', 'OWM', 'meteo', '-3.68000', '40.40000', 0, ''),
('3123381', '3123381', 'El Pardo', 'ES', 'OWM', 'meteo', '-3.77000', '40.51000', 0, ''),
('3123682', '3123682', 'El Alamo', 'ES', 'OWM', 'meteo', '-3.99000', '40.23000', 0, ''),
('3124209', '3124209', 'Cubas', 'ES', 'OWM', 'meteo', '-3.84000', '40.19000', 0, ''),
('3124408', '3124408', 'Coslada', 'ES', 'OWM', 'meteo', '-3.56000', '40.42000', 0, ''),
('3126082', '3126082', 'Casarrubuelos', 'ES', 'OWM', 'meteo', '-3.83000', '40.17000', 0, ''),
('3126214', '3126214', 'Carranque', 'ES', 'OWM', 'meteo', '-3.90000', '40.17000', 0, ''),
('3127588', '3127588', 'Brunete', 'ES', 'OWM', 'meteo', '-4.00000', '40.41000', 0, ''),
('3127958', '3127958', 'Boadilla del Monte', 'ES', 'OWM', 'meteo', '-3.88000', '40.41000', 0, ''),
('3128477', '3128477', 'Batres', 'ES', 'OWM', 'meteo', '-3.92000', '40.21000', 0, ''),
('3128832', '3128832', 'Barajas de Madrid', 'ES', 'OWM', 'meteo', '-3.58000', '40.47000', 0, ''),
('3129356', '3129356', 'Arroyomolinos', 'ES', 'OWM', 'meteo', '-3.92000', '40.27000', 0, ''),
('3129827', '3129827', 'Aravaca', 'ES', 'OWM', 'meteo', '-3.78000', '40.46000', 0, ''),
('3130564', '3130564', 'Alcorcon', 'ES', 'OWM', 'meteo', '-3.82000', '40.35000', 0, ''),
('3130583', '3130583', 'Alcobendas', 'ES', 'OWM', 'meteo', '-3.64000', '40.55000', 0, ''),
('3227', '3227', 'Escuelas Aguirre', 'ES', 'AQICN', 'pollution', '-3.68222', '40.42139', NULL, 'https://aqicn.org/city/spain/madrid/escuelas-aguirre'),
('3228', '3228', 'Casa de Campo', 'ES', 'AQICN', 'pollution', '-3.74722', '40.41917', NULL, 'https://aqicn.org/city/spain/madrid/casa-de-campo'),
('3229', '3229', 'Cuatro Caminos', 'ES', 'AQICN', 'pollution', '-3.70694', '40.44528', NULL, 'https://aqicn.org/city/spain/madrid/cuatro-caminos'),
('3230', '3230', 'Méndez Álvaro', 'ES', 'AQICN', 'pollution', '-3.68667', '40.39806', NULL, 'https://aqicn.org/city/spain/madrid/mendez-alvaro'),
('3231', '3231', 'Castellana', 'ES', 'AQICN', 'pollution', '-3.69028', '40.43972', NULL, 'https://aqicn.org/city/spain/madrid/castellana'),
('3232', '3232', 'Plaza de Castilla', 'ES', 'AQICN', 'pollution', '-3.68861', '40.46556', NULL, 'https://aqicn.org/city/spain/madrid/plaza-de-castilla'),
('5725', '5725', 'Madrid', 'ES', 'AQICN', 'pollution', '-3.70379', '40.41678', NULL, 'https://aqicn.org/city/madrid'),
('6324376', '6324376', 'Pinar de Chamartin', 'ES', 'OWM', 'meteo', '-3.67000', '40.48000', 0, ''),
('6324402', '6324402', 'Las Tablas', 'ES', 'OWM', 'meteo', '-3.67000', '40.51000', 0, ''),
('6355233', '6355233', 'Provincia de Madrid', 'ES', 'OWM', 'meteo', '-3.71000', '40.40000', 0, ''),
('6359230', '6359230', 'Álamo (El)', 'ES', 'OWM', 'meteo', '-3.99000', '40.23000', 0, ''),
('6359232', '6359232', 'Alcobendas', 'ES', 'OWM', 'meteo', '-3.63000', '40.54000', 0, ''),
('6359233', '6359233', 'Alcorcón', 'ES', 'OWM', 'meteo', '-3.83000', '40.35000', 0, ''),
('6359243', '6359243', 'Batres', 'ES', 'OWM', 'meteo', '-3.93000', '40.23000', 0, ''),
('6359248', '6359248', 'Boadilla del Monte', 'ES', 'OWM', 'meteo', '-3.88000', '40.40000', 0, ''),
('6359252', '6359252', 'Brunete', 'ES', 'OWM', 'meteo', '-4.00000', '40.40000', 0, ''),
('6359262', '6359262', 'Casarrubuelos', 'ES', 'OWM', 'meteo', '-3.83000', '40.17000', 0, ''),
('6359275', '6359275', 'Coslada', 'ES', 'OWM', 'meteo', '-3.55000', '40.43000', 0, ''),
('6359276', '6359276', 'Cubas de la Sagra', 'ES', 'OWM', 'meteo', '-3.83000', '40.19000', 0, ''),
('6359284', '6359284', 'Fuenlabrada', 'ES', 'OWM', 'meteo', '-3.80000', '40.29000', 0, ''),
('6359291', '6359291', 'Getafe', 'ES', 'OWM', 'meteo', '-3.66000', '40.29000', 0, ''),
('6359292', '6359292', 'Griñón', 'ES', 'OWM', 'meteo', '-3.84000', '40.22000', 0, ''),
('6359299', '6359299', 'Humanes de Madrid', 'ES', 'OWM', 'meteo', '-3.82000', '40.25000', 0, ''),
('6359300', '6359300', 'Leganés', 'ES', 'OWM', 'meteo', '-3.78000', '40.33000', 0, ''),
('6359304', '6359304', 'Madrid', 'ES', 'OWM', 'meteo', '-3.68000', '40.49000', 0, ''),
('6359305', '6359305', 'Majadahonda', 'ES', 'OWM', 'meteo', '-3.87000', '40.47000', 0, ''),
('6359313', '6359313', 'Moraleja de Enmedio', 'ES', 'OWM', 'meteo', '-3.89000', '40.26000', 0, ''),
('6359316', '6359316', 'Móstoles', 'ES', 'OWM', 'meteo', '-3.90000', '40.31000', 0, ''),
('6359320', '6359320', 'Navalcarnero', 'ES', 'OWM', 'meteo', '-4.00000', '40.28000', 0, ''),
('6359327', '6359327', 'Parla', 'ES', 'OWM', 'meteo', '-3.77000', '40.23000', 0, ''),
('6359334', '6359334', 'Pinto', 'ES', 'OWM', 'meteo', '-3.68000', '40.26000', 0, ''),
('6359336', '6359336', 'Pozuelo de Alarcón', 'ES', 'OWM', 'meteo', '-3.82000', '40.43000', 0, ''),
('6359344', '6359344', 'Rivas-Vaciamadrid', 'ES', 'OWM', 'meteo', '-3.52000', '40.35000', 0, ''),
('6359353', '6359353', 'San Martín de la Veg', 'ES', 'OWM', 'meteo', '-3.55000', '40.25000', 0, ''),
('6359360', '6359360', 'Serranillos del Val', 'ES', 'OWM', 'meteo', '-3.91000', '40.21000', 0, ''),
('6359361', '6359361', 'Sevilla la Nueva', 'ES', 'OWM', 'meteo', '-4.04000', '40.36000', 0, ''),
('6359368', '6359368', 'Torrejón de la Calz', 'ES', 'OWM', 'meteo', '-3.80000', '40.20000', 0, ''),
('6359369', '6359369', 'Torrejón de Velasco', 'ES', 'OWM', 'meteo', '-3.75000', '40.18000', 0, ''),
('6359380', '6359380', 'Valdemoro', 'ES', 'OWM', 'meteo', '-3.65000', '40.18000', 0, ''),
('6359400', '6359400', 'Villaviciosa de Odón', 'ES', 'OWM', 'meteo', '-3.91000', '40.36000', 0, ''),
('6361699', '6361699', 'Carranque', 'ES', 'OWM', 'meteo', '-3.90000', '40.17000', 0, ''),
('6361741', '6361741', 'Illescas', 'ES', 'OWM', 'meteo', '-3.86000', '40.14000', 0, ''),
('6361836', '6361836', 'Ugena', 'ES', 'OWM', 'meteo', '-3.87000', '40.16000', 0, ''),
('6544483', '6544483', 'El Bercial', 'ES', 'OWM', 'meteo', '-3.74000', '40.32000', 0, ''),
('6544490', '6544490', 'Usera', 'ES', 'OWM', 'meteo', '-3.70000', '40.39000', 0, ''),
('6544494', '6544494', 'City Center', 'ES', 'OWM', 'meteo', '-3.70000', '40.42000', 0, ''),
('6545083', '6545083', 'Sol', 'ES', 'OWM', 'meteo', '-3.70000', '40.42000', 0, ''),
('6545095', '6545095', 'Bilbao', 'ES', 'OWM', 'meteo', '-3.70000', '40.43000', 0, '');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `stations`
--
ALTER TABLE `stations`
  ADD PRIMARY KEY (`station_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
