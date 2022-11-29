package com.gecici.gecickayit.Claslar;

public class imei {
    private String imeiNumber;
    private String imeiTime;
    private String kullaniciEmail;
    private String imeiDurum;
    private String sparisNumber;

    public imei(String imeiNumber, String imeiTime, String kullaniciEmail, String imeiDurum, String sparisNumber) {
        this.imeiNumber = imeiNumber;
        this.imeiTime = imeiTime;
        this.kullaniciEmail = kullaniciEmail;
        this.imeiDurum = imeiDurum;
        this.sparisNumber = sparisNumber;
    }

    public imei() {
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getImeiTime() {
        return imeiTime;
    }

    public void setImeiTime(String imeiTime) {
        this.imeiTime = imeiTime;
    }

    public String getKullaniciEmail() {
        return kullaniciEmail;
    }

    public void setKullaniciEmail(String kullaniciEmail) {
        this.kullaniciEmail = kullaniciEmail;
    }

    public String getImeiDurum() {
        return imeiDurum;
    }

    public void setImeiDurum(String imeiDurum) {
        this.imeiDurum = imeiDurum;
    }

    public String getSparisNumber() {
        return sparisNumber;
    }

    public void setSparisNumber(String sparisNumber) {
        this.sparisNumber = sparisNumber;
    }
}
