CREATE DATABASE IF NOT EXISTS verabyte;
USE verabyte;

SET FOREIGN_KEY_CHECKS = 0;

--TABLA: categorias 

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE `categorias` (
  `IdCategoria` tinyint NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(40) NOT NULL,
  `Imagen` varchar(30) DEFAULT 'default.jpg',
  PRIMARY KEY (`IdCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

LOCK TABLES `categorias` WRITE;
INSERT INTO `categorias` VALUES 
(1,'Placas Base','default.jpg'),(2,'Procesadores','default.jpg'),(3,'Discos Duros','default.jpg'),
(4,'Intel Placas','14259451186728691.jpg'),(5,'Amd Placas','14259451301832677.jpg'),(7,'Memoria Ram','10905.jpg'),
(9,'Gráficas','44415-tarjetasgraficas.jpg'),(11,'Sata','14259455771489321.jpg'),(13,'Ssd','14259455894536146.jpg'),
(14,'Externos','default.jpg'),(18,'NVidia','14259458625293893.jpg'),(19,'Intel Socket 1150','14259444204055460.jpg'),
(20,'Amd Socket AM3','14259449062732937.jpg'),(21,'Ddr3','default.jpg'),(22,'Ddr4','default.jpg'),
(23,'Otros','default.jpg'),(24,'Cajas','default.jpg'),(25,'Fuentes','default.jpg'),
(26,'Perifericos','default.jpg'),(27,'Servicios','default.jpg'),(28,'Sobremesa','default.jpg'),
(29,'Amd','14259458772039704.jpg'),(30,'Portatiles','default.jpg');
UNLOCK TABLES;


-- TABLA: usuarios

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `IdUsuario` smallint NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Nombre` varchar(20) NOT NULL,
  `Apellidos` varchar(30) NOT NULL,
  `NIF` char(9) NOT NULL,
  `Telefono` char(9) DEFAULT NULL,
  `Direccion` varchar(40) NOT NULL,
  `CodigoPostal` char(5) NOT NULL,
  `Localidad` varchar(40) NOT NULL,
  `Provincia` varchar(30) NOT NULL,
  `UltimoAcceso` datetime DEFAULT NULL,
  `Avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IdUsuario`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

LOCK TABLES `usuarios` WRITE;
INSERT INTO `usuarios` (`Email`, `Password`, `Nombre`, `Apellidos`, `NIF`, `Telefono`, `Direccion`, `CodigoPostal`, `Localidad`, `Provincia`, `Avatar`) VALUES 
('pablitoUser@test.com', '$argon2id$v=19$m=65536,t=10,p=1$WH2kaqrWkBI+T5UorEcGdA$ICkJiQVhlUZZBzWbbN4Gm7iTy0oT0SGVP9bJWdoFVHE', 'Pablo', 'Perez', '12345678Z', '600123456', 'Calle Mayor 1', '28001', 'Madrid', 'Madrid', 'default.png'),
('albertoUser@test.com', '$argon2id$v=19$m=65536,t=10,p=1$lOjeaNhYSI8UWOgz1UdIDw$c/uoPfu8D2n3NcefwhD/ISwFpEW1tdxQ85/m/W9tliE', 'Alberto', 'Root', '87654321X', '600654321', 'Avenida Principal 2', '08001', 'Barcelona', 'Barcelona', 'default.png');
UNLOCK TABLES;


-- TABLA: productos

DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `IdProducto` smallint NOT NULL AUTO_INCREMENT,
  `IdCategoria` tinyint NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` mediumtext,
  `Precio` decimal(6,2) unsigned NOT NULL,
  `Marca` varchar(40) NOT NULL,
  `Imagen` varchar(30) DEFAULT 'default.jpg',
  PRIMARY KEY (`IdProducto`),
  CONSTRAINT `fk_prod_cat` FOREIGN KEY (`IdCategoria`) REFERENCES `categorias` (`IdCategoria`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

LOCK TABLES `productos` WRITE;
INSERT INTO `productos` VALUES 
(1,19,'Procesador - Intel Celeron G1840 2.8Ghz Box','Gran rendimiento con un coste realmente ajustado',40.00,'Intel','intelsocket/14258595863011386'),
(2,19,'Procesador - Intel Core i3-4160 3.6Ghz','Ventajas: Temperaturas, velocidad, precio...',115.00,'Intel','intelsocket/14258596261238775'),
(3,19,'Procesador - Intel Core i7-4790 3.6Ghz Box','Un gran procesador con el que podrás hacer de todo...',307.00,'Intel','intelsocket/14258596456403026'),
(4,4,'Asrock H81M-DGS R2.0','ASRock materiales de alta calidad...',50.00,'ASRock','intelplacas/14258596788795874'),
(5,4,'Asus Z97-P','Placa base Z97 ATX con M.2...',104.00,'Asus','intelplacas/14258597882993929'),
(6,21,'Corsair Value Select DDR3','Fiabilidad para ordenadores estándar...',23.00,'Corsair','ddr/1425859809743367'),
(7,21,'Kingston HyperX Fury Blue DDR3','Reconoce de forma automática la plataforma...',76.00,'Nullware','ddr/14258598408685564'),
(8,13,'Corsair Force Series GS 240GB SSD','SSD Unformatted Capacity 240 GB...',160.00,'Corsair','ssd/14258598595531097'),
(9,11,'WD Green 2TB SATA3','Equilibrio entre rendimiento y ahorro...',83.00,'WD','sata/14258598806748517'),
(10,11,'Toshiba DT01ACA100 1TB','7200 RPM, optimizado para bajo consumo...',55.00,'Toshiba','sata/14258599051224205'),
(11,11,'Seagate NAS HDD 3TB SATA3','Ideal para pequeños sistemas NAS...',130.00,'Seagate','sata/14258599258789866'),
(12,24,'AeroCool VS-3 Advance Gaming','Diseñada con los jugadores en mente...',50.00,'AeroCool','cajas/14258599593061856'),
(13,24,'Corsair Carbide 500R ','Semitorre ATX con paneles metal mesh...',130.00,'Corsair','cajas/14258599963833145'),
(14,24,'Sharkoon T9 Value','Interior y exterior en varios colores...',52.00,'Sharkoon','cajas/1425860048176368'),
(15,24,'Thermaltake Urban S31','Excelente sistema para ordenar cables...',80.00,'Thermaltake','cajas/14258599593066781'),
(16,25,'AeroCool Strike-X Power 500W','Certificación 80 PLUS BRONZE...',50.00,'AeroCool','fuentes/1425860068533324'),
(17,20,'AMD A6-5400K 3.60Ghz','Gran procesador para juegos en HDMI...',45.00,'Amd','procesadores/14259002582733318'),
(18,20,'AMD A10-6790K 4.0Ghz','Nueva generación de APU Richland...',121.00,'Amd','procesadores/14259004443579828'),
(19,5,'Asrock FM2A78M-HD+','Compatible con APU AMD FM2 y FM2+...',56.00,'Asrock','placasbase/14259007383287952'),
(20,5,'Asus A88X-PLUS','BIOS UEFI intuitiva y GPU Boost...',82.00,'Asus','placasbase/142590093129469'),
(21,5,'MSI A88X-G45 Gaming Assasin´s Creed','Incluye código para Assasin´s Creed...',130.00,'MSI','placasbase/14259011651887716'),
(22,21,'Crucial DDR3 1600 8GB','Memoria DDR3 1600 CL11...',68.00,'Crucial','memoriaram/14259017017189296'),
(23,21,'G.Skill Ripjaws X DDR3 2x4GB','Latencias bajas para rendimiento óptimo...',71.00,'G.Skill','memoriaram/14259018903339060'),
(24,11,'Hitachi Deskstar 7K4000 4TB','Velocidad de rotación 7200 RPM...',190.00,'Hitachi','sata/14259024564658095'),
(25,14,'WD My Passport Ultra 2TB','Copia de seguridad automática...',103.00,'WD','discosduros/14259263687487137'),
(26,14,'Toshiba STOR.E Slim Mac 1TB','Compañero perfecto, elegante y ligero...',73.00,'Toshiba','discosduros/14259265459128167'),
(27,14,'WD My Cloud 6TB','Guárdelo todo en un único sitio...',361.00,'WD','discosduros/14259268460728273'),
(28,14,'WD My Passport AV-TV 1TB','Disfrute de grabaciones de alta calidad...',98.00,'WD','discosduros/14259271214459522'),
(29,18,'Asus GeForce GT730 Silent','Sin ventilador, tarjeta gráfica con DX11...',64.00,'Asus','graficas/14259388993036489'),
(30,29,'MSI Radeon R9 270X Gaming','Pre-overclockeado de fábrica...',200.00,'Msi','graficas/14259392971966117'),
(31,29,'Sapphire R9 280X Vapor-X Tri-X','Extraordinaria potencia gráfica...',253.00,'Sapphire','graficas/14259396172475971'),
(32,18,'Gigabyte GeForce GT 730','Componentes de alta calidad...',69.00,'Gigabyte','graficas/14259400450995737'),
(33,26,'Acer V246HLbmd 24\"','Monitor LED Full HD 5ms...',137.00,'Acer','otros/14259415615327000'),
(34,26,'Corsair Raptor M45 Óptico','Potente sensor óptico de 5.000 DPI...',30.00,'Corsair','otros/14259417302504633'),
(35,26,'Creative Inspire T3150','Tecnología inalámbrica Bluetooth...',52.00,'Creative','otros/14259418824986234'),
(36,26,'Rapoo 8000 Teclado','Compacto, fiable y resistente a salpicaduras...',29.00,'Rapoo','otros/14259421014843590'),
(37,28,'Apple iMac i5','Espectacular pantalla panorámica...',1259.00,'Apple','sobremesa/14259847107819125'),
(38,28,'PcCom Gaming Battle','Ensamblados por nuestros expertos...',460.00,'PcCom','sobremesa/14259855332532378'),
(39,30,'Asus X553MA Intel Celeron N2840','Funciones centradas en el usuario...',269.00,'Asus','portatiles/14260653307209582'),
(40,30,'MSI GP60 2PE-422XES i5','Teclado SteelSeries hecho para gamers...',659.00,'MSI','portatiles/14260656670443987'),
(41,30,'MacBook Pro Retina i7/16GB','Asombrosamente fino y ligero...',1900.00,'Apple','portatiles/14260660787342896'),
(42,27,'Garantía 6 meses','Extensión de garantía NullWare...',100.00,'Nullware','otros/14260675109793995'),
(43,27,'Montaje','Montaje de calidad profesional...',30.00,'Nullware','otros/14260700800476322'),
(44,27,'Análisis PC','Revisión exhaustiva de configuración...',30.00,'Nullware','servicios/14260704823374595'),
(45,22,'G.Skill RipJaws 4 DDR4','Memoria diseñada para Intel X99...',230.00,'G.Skill','ddr/1426158266734276');
UNLOCK TABLES;


-- TABLA: pedidos

DROP TABLE IF EXISTS `pedidos`;
CREATE TABLE `pedidos` (
  `IdPedido` smallint NOT NULL AUTO_INCREMENT,
  `Fecha` date DEFAULT NULL,
  `Estado` enum('c','f') DEFAULT 'c',
  `IdUsuario` smallint DEFAULT NULL,
  `Importe` decimal(6,2) unsigned DEFAULT NULL,
  `Iva` decimal(6,2) unsigned DEFAULT NULL,
  PRIMARY KEY (`IdPedido`),
  CONSTRAINT `fk_ped_usu` FOREIGN KEY (`IdUsuario`) REFERENCES `usuarios` (`IdUsuario`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;


-- TABLA: lineaspedidos

DROP TABLE IF EXISTS `lineaspedidos`;
CREATE TABLE `lineaspedidos` (
  `IdLinea` smallint NOT NULL AUTO_INCREMENT,
  `IdPedido` smallint NOT NULL,
  `IdProducto` smallint NOT NULL,
  `Cantidad` tinyint unsigned NOT NULL,
  PRIMARY KEY (`IdLinea`),
  CONSTRAINT `fk_lin_ped` FOREIGN KEY (`IdPedido`) REFERENCES `pedidos` (`IdPedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_lin_prod` FOREIGN KEY (`IdProducto`) REFERENCES `productos` (`IdProducto`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

DROP TABLE IF EXISTS `carrito`;
CREATE TABLE `carrito` (
  `id_usuario` smallint NOT NULL,
  `id_producto` smallint NOT NULL,
  `cantidad` tinyint unsigned NOT NULL DEFAULT 1,
  PRIMARY KEY (`id_usuario`,`id_producto`),
  FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`IdUsuario`) ON DELETE CASCADE,
  FOREIGN KEY (`id_producto`) REFERENCES `productos` (`IdProducto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Datos para pedidos
LOCK TABLES `pedidos` WRITE;
INSERT INTO `pedidos` (`IdPedido`, `Fecha`, `Estado`, `IdUsuario`, `Importe`, `Iva`) VALUES 
(1, '2024-02-15', 'f', 1, 155.00, 32.55),
(2, '2024-02-20', 'c', 1, 40.00, 8.40),
(3, '2024-03-01', 'f', 2, 307.00, 64.47),
(4, '2024-03-02', 'c', 2, 73.00, 15.33);
UNLOCK TABLES;

-- Datos para lineaspedidos
LOCK TABLES `lineaspedidos` WRITE;
INSERT INTO `lineaspedidos` (`IdPedido`, `IdProducto`, `Cantidad`) VALUES 
(1, 1, 1),
(1, 2, 1),
(2, 1, 1),
(3, 3, 1),
(4, 26, 1);
UNLOCK TABLES;

SET FOREIGN_KEY_CHECKS = 1;