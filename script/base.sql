CREATE database gestionvol;
\c gestionvol;
CREATE TABLE avion (
    id SERIAL PRIMARY KEY,
    modele VARCHAR(100) NOT NULL,
    dtfabrication TIMESTAMP NOT NULL
);
CREATE TABLE typesiege (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE avionsiege (
    id SERIAL PRIMARY KEY,
    idavion INT NOT NULL,
    idtypesiege INT NOT NULL,
    nbr INT NOT NULL CHECK (nbr > 0),
    FOREIGN KEY (idavion) REFERENCES avion(id) ON DELETE CASCADE,
    FOREIGN KEY (idtypesiege) REFERENCES typesiege(id) ON DELETE CASCADE
);

CREATE TABLE ville (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE vol (
    id SERIAL PRIMARY KEY,
    idavion INT NOT NULL,
    dtdepart TIMESTAMP NOT NULL,
    idlieudepart INT NOT NULL,
    dtarrive TIMESTAMP NOT NULL,
    idlieuarrive INT NOT NULL,
    FOREIGN KEY (idavion) REFERENCES avion(id) ON DELETE CASCADE,
    FOREIGN KEY (idlieudepart) REFERENCES ville(id) ON DELETE RESTRICT,
    FOREIGN KEY (idlieuarrive) REFERENCES ville(id) ON DELETE RESTRICT,
    CHECK (dtdepart <= dtarrive)
    -- CHECK (idlieudepart <> idlieuarrive) -- Empêche un vol d'avoir le même départ et arrivée
);


CREATE TABLE volprix (
    idvol INT NOT NULL,
    idtypesiege INT NOT NULL,
    prixvol DECIMAL(10,2) NOT NULL CHECK (prixvol >= 0),
    PRIMARY KEY (idvol, idtypesiege),
    FOREIGN KEY (idvol) REFERENCES vol(id) ON DELETE CASCADE,
    FOREIGN KEY (idtypesiege) REFERENCES typesiege(id) ON DELETE CASCADE
);

CREATE TABLE volenpromotion (
    id SERIAL PRIMARY KEY,
    idvol INT NOT NULL,
    dtchange TIMESTAMP NOT NULL,
    pourcentage DECIMAL(5,2) NOT NULL CHECK (pourcentage BETWEEN 0 AND 100),
    nbrchaisePromotion INT NOT NULL CHECK (nbrchaisePromotion >= 0),
    idtypesiege INT NOT NULL,
    FOREIGN KEY (idvol) REFERENCES vol(id) ON DELETE CASCADE,
    FOREIGN KEY (idtypesiege) REFERENCES typesiege(id) ON DELETE CASCADE
);

CREATE TABLE utilisateur (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    mdp VARCHAR(255) NOT NULL,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    idutilisateur INT NOT NULL,
    idvol INT NOT NULL,
    nbrchaise INT NOT NULL CHECK (nbrchaise > 0),
    idtypesiege INT NOT NULL,
    dtreservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idtypesiege) REFERENCES typesiege(id) ON DELETE CASCADE,
    FOREIGN KEY (idutilisateur) REFERENCES utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (idvol) REFERENCES vol(id) ON DELETE CASCADE
);
CREATE Table mvtResrvation(
    id SERIAL PRIMARY KEY,
    idvol INT NOT NULL,
    finreservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    finannulation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idvol) REFERENCES vol(id) ON DELETE CASCADE
);
