package com.gecici.gecickayit.Claslar;

public class Kullanici {
    private String KullaniciIsmi, KullaniciEmail, KullaniciId, KrediMiktarı,KullaniciPassword;

    public Kullanici(String kullaniciIsmi, String kullaniciEmail, String kullaniciId, String krediMiktarı, String kullaniciPassword) {
        KullaniciIsmi = kullaniciIsmi;
        KullaniciEmail = kullaniciEmail;
        KullaniciId = kullaniciId;
        KrediMiktarı = krediMiktarı;
        KullaniciPassword = kullaniciPassword;
    }

    public Kullanici() {
    }

    public String getKullaniciIsmi() {
        return KullaniciIsmi;
    }

    public void setKullaniciIsmi(String kullaniciIsmi) {
        KullaniciIsmi = kullaniciIsmi;
    }

    public String getKullaniciEmail() {
        return KullaniciEmail;
    }

    public void setKullaniciEmail(String kullaniciEmail) {
        KullaniciEmail = kullaniciEmail;
    }

    public String getKullaniciId() {
        return KullaniciId;
    }

    public void setKullaniciId(String kullaniciId) {
        KullaniciId = kullaniciId;
    }

    public String getKrediMiktarı() {
        return KrediMiktarı;
    }

    public void setKrediMiktarı(String krediMiktarı) {
        KrediMiktarı = krediMiktarı;
    }

    public String getKullaniciPassword() {
        return KullaniciPassword;
    }

    public void setKullaniciPassword(String kullaniciPassword) {
        KullaniciPassword = kullaniciPassword;
    }
}
