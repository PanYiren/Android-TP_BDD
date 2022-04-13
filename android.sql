-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 13 avr. 2022 à 07:03
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `android`
--

-- --------------------------------------------------------

--
-- Structure de la table `association`
--

DROP TABLE IF EXISTS `association`;
CREATE TABLE IF NOT EXISTS `association` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `association` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `association`
--

INSERT INTO `association` (`id`, `association`) VALUES
(1, 'association x'),
(2, 'association y'),
(3, 'association z'),
(4, 'test');

-- --------------------------------------------------------

--
-- Structure de la table `sport`
--

DROP TABLE IF EXISTS `sport`;
CREATE TABLE IF NOT EXISTS `sport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `disponibilite` varchar(150) NOT NULL,
  `niveau` varchar(50) NOT NULL,
  `commentaire` varchar(500) DEFAULT NULL,
  `idAsso` int(11) NOT NULL,
  `idUtili` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idAsso` (`idAsso`),
  KEY `idUtili` (`idUtili`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `sport`
--

INSERT INTO `sport` (`id`, `disponibilite`, `niveau`, `commentaire`, `idAsso`, `idUtili`) VALUES
(2, 'Lundi', 'Debutant', '', 1, 10),
(7, 'Mardi Jeudi', 'HautNiveau', '', 3, 15),
(8, ' Mardi Mercredi Jeudi ', 'HautNiveau', 'ksqd', 4, 16),
(10, 'Lundi Mardi null null null', 'HautNiveau', '', 1, 26),
(11, '  Mardi Mercredi    ', 'Intermediaire', '', 1, 27),
(12, '  Mardi   Jeudi  ', 'HautNiveau', '', 1, 28),
(15, '  Mardi Mercredi    ', 'Intermediaire', '', 1, 31),
(16, '    Mercredi Jeudi  ', 'HautNiveau', '', 1, 32);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(100) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `pseudo`, `mdp`) VALUES
(10, 'admin', 'admin'),
(15, 'a', 'a'),
(16, 'z', 'z'),
(26, 'il', 'il'),
(27, 'le', 'le'),
(28, 'ga', 'ga'),
(31, 'geo', 'geo'),
(32, 'lk', 'lk');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `sport`
--
ALTER TABLE `sport`
  ADD CONSTRAINT `sport_ibfk_1` FOREIGN KEY (`idAsso`) REFERENCES `association` (`id`),
  ADD CONSTRAINT `sport_ibfk_2` FOREIGN KEY (`idUtili`) REFERENCES `utilisateur` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
