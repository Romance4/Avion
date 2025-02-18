CREATE VIEW vue_vol_details AS
SELECT 
    v.id AS vol_id,
    a.id AS idavion,
    a.modele AS avion_modele,
    vd.nom AS lieu_depart,
    v.dtdepart AS date_depart,
    va.nom AS lieu_arrive,
    v.idlieudepart AS idLieuDepart,
    v.idlieuarrive AS idLieuArrive,
    v.dtarrive AS date_arrivee,
    ts.nom AS type_siege,
    vp.prixvol AS prix_normal,
    vp.idvol AS id_vol_prix,
    vp.idtypesiege AS id_type_siege,
    asieg.nbr AS nbrchaise,
    vpromo.id AS idpromo,
    vpromo.dtchange AS datecommencementPromotion,
    vp.prixvol - (vp.prixvol * COALESCE(vpromo.pourcentage / 100, 0)) AS prix_promotion,
    vpromo.pourcentage AS taux_promotion,
    vpromo.nbrchaisePromotion AS chaises_promotionnelles
FROM vol v
JOIN avion a ON v.idavion = a.id
JOIN ville vd ON v.idlieudepart = vd.id
JOIN ville va ON v.idlieuarrive = va.id
JOIN volprix vp ON v.id = vp.idvol
JOIN typesiege ts ON vp.idtypesiege = ts.id
JOIN avionsiege asieg ON v.idavion=asieg.idavion AND vp.idtypesiege=asieg.nbrchaise
LEFT JOIN volenpromotion vpromo 
    ON v.id = vpromo.idvol 
    AND vp.idtypesiege = vpromo.idtypesiege;


CREATE VIEW vue_vol_details_reservation AS
SELECT 
    v.*,
    COALESCE(SUM(reserv.nbrchaise), 0) AS chaises_reservees
FROM vue_vol_details v
LEFT JOIN reservation reserv 
    ON v.vol_id = reserv.idvol 
    AND v.id_type_siege = reserv.idtypesiege
GROUP BY v.vol_id, v.avion_modele, v.lieu_depart, v.date_depart, v.lieu_arrive, 
         v.date_arrivee, v.type_siege, v.prix_normal, v.id_vol_prix, 
         v.id_type_siege, v.datecommencementPromotion, v.prix_promotion, 
         v.taux_promotion, v.chaises_promotionnelles;
