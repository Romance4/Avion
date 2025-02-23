INSERT INTO avion (modele, dtfabrication) VALUES ('Airbus A320', '2010-05-15');
INSERT INTO avion (modele, dtfabrication) VALUES ('Boeing 737', '2015-08-20');
INSERT INTO avion (modele, dtfabrication) VALUES ('Embraer E195', '2018-03-10');
INSERT INTO avion (modele, dtfabrication) VALUES ('Bombardier CRJ900', '2012-07-25');
INSERT INTO avion (modele, dtfabrication) VALUES ('Airbus A380', '2009-12-15');

INSERT INTO typesiege (nom) VALUES ('economique');
INSERT INTO typesiege (nom) VALUES ('Affaires');
INSERT INTO typesiege (nom) VALUES ('Première classe');
INSERT INTO typesiege (nom) VALUES ('eco Premium');
INSERT INTO typesiege (nom) VALUES ('Premium Affaires');

INSERT INTO ville (nom) VALUES ('Paris');
INSERT INTO ville (nom) VALUES ('New York');
INSERT INTO ville (nom) VALUES ('Tokyo');
INSERT INTO ville (nom) VALUES ('London');
INSERT INTO ville (nom) VALUES ('Berlin');
INSERT INTO ville (nom) VALUES ('Sydney');
INSERT INTO ville (nom) VALUES ('Moscow');
INSERT INTO ville (nom) VALUES ('Dubai');
INSERT INTO ville (nom) VALUES ('Los Angeles');
INSERT INTO ville (nom) VALUES ('Madrid');

-- Insertion des sièges pour l'Airbus A320
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (1, 1, 150);  -- 150 sièges économiques pour Airbus A320
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (1, 2, 30);   -- 30 sièges Affaires pour Airbus A320
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (1, 3, 10);   -- 10 sièges Première classe pour Airbus A320

-- Insertion des sièges pour le Boeing 737
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (2, 1, 120);  -- 120 sièges économiques pour Boeing 737
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (2, 2, 25);   -- 25 sièges Affaires pour Boeing 737
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (2, 3, 5);    -- 5 sièges Première classe pour Boeing 737

-- Insertion des sièges pour l'Embraer E195
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (3, 1, 100);  -- 100 sièges économiques pour Embraer E195
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (3, 2, 20);   -- 20 sièges Affaires pour Embraer E195

-- Insertion des sièges pour le Bombardier CRJ900
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (4, 1, 80);   -- 80 sièges économiques pour Bombardier CRJ900
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (4, 2, 15);   -- 15 sièges Affaires pour Bombardier CRJ900

-- Insertion des sièges pour l'Airbus A380
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (5, 1, 400);  -- 400 sièges économiques pour Airbus A380
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (5, 2, 50);   -- 50 sièges Affaires pour Airbus A380
INSERT INTO avionsiege (idavion, idtypesiege, nbr) VALUES (5, 3, 20);   -- 20 sièges Première classe pour Airbus A380

INSERT INTO utilisateur (email, mdp, nom) VALUES 
('alice@example.com', 'motdepasse1', 'Alice Dupont'),
('bob@example.com', 'motdepasse2', 'Bob Martin'),
('charlie@example.com', 'motdepasse3', 'Charlie Durand'),
('david@example.com', 'motdepasse4', 'David Morel'),
('eve@example.com', 'motdepasse5', 'Eve Lambert');

INSERT INTO mvtResrvation (idvol, finreservation, finannulation)
VALUES 
(1, '2025-03-01 01:30:00', '2025-03-01 01:00:00');
