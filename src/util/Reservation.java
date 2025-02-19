package util;

import java.sql.*;

public class Reservation {
    private int id;
    private int idUser;
    private Vol vol;
    private int nbrChaise;
    private int idTypeSiege;
    private Timestamp dtReservation;
    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public int getNbrChaise() {
        return nbrChaise;
    }

    public void setNbrChaise(int nbrChaise) {
        this.nbrChaise = nbrChaise;
    }

    public int getIdTypeSiege() {
        return idTypeSiege;
    }

    public void setIdTypeSiege(int idTypeSiege) {
        this.idTypeSiege = idTypeSiege;
    }

    public Timestamp getDtReservation() {
        return dtReservation;
    }

    public void setDtReservation(Timestamp dtReservation) {
        this.dtReservation = dtReservation;
    }

    // Constructeur, getters et setters
    public Reservation(int id, int idUser, Vol vol, int nbrChaise, int idTypeSiege, Timestamp dtReservation) {
        this.id = id;
        this.idUser = idUser;
        this.vol = vol;
        this.nbrChaise = nbrChaise;
        this.idTypeSiege = idTypeSiege;
        this.dtReservation = dtReservation;
    }

    // Getters et setters (ou autres méthodes si nécessaires)
}
