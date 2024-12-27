package com.example.bevasarlobeadando;

import android.icu.text.DecimalFormat;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Termek implements Parcelable {
    private static int id;
    private String termekNev;
    private double egysegAr;
    private double mennyiseg;
    private String mertekegyseg;
    private double bruttoAr;


    public Termek(Integer id, String termekNev, double egysegAr, double mennyiseg, String mertekegyseg) {
        this.id = id;
        this.termekNev = termekNev;
        this.egysegAr = egysegAr;
        this.mennyiseg = mennyiseg;
        this.mertekegyseg = mertekegyseg;
        this.bruttoAr = Double.parseDouble(new DecimalFormat("##.##").format(mennyiseg * egysegAr));
    }

    public String getTermekNev() {
        return termekNev;
    }

    public void setTermekNev(String termekNev) {
        this.termekNev = termekNev;
    }

    public double getEgysegAr() {
        return egysegAr;
    }

    public void setEgysegAr(double egysegAr) {
        this.egysegAr = egysegAr;
    }

    public double getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(double mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public String getMertekegyseg() {
        return mertekegyseg;
    }

    public void setMertekegyseg(String mertekegyseg) {
        this.mertekegyseg = mertekegyseg;
    }

    public static int getId() {
        return id;
    }

    public double getBruttoAr() {
        return bruttoAr;
    }

    public int getId(Termek termek) {
        return termek.id;
    }

    public static void setId(int id) {
        Termek.id = id;
    }

    // Parcelable interfaccel jön, kell hogy a put extra egyszerű legyen
    @Override
    public int describeContents() {
        return 0;
    }

    // Parcelable interfaccel jön, kell hogy a put extra egyszerű legyen
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(termekNev);
        parcel.writeDouble(egysegAr);
        parcel.writeDouble(mennyiseg);
        parcel.writeString(mertekegyseg);
        parcel.writeDouble(bruttoAr);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Termek> CREATOR = new Parcelable.Creator<Termek>() {
        public Termek createFromParcel(Parcel in) {
            return new Termek(in);
        }

        public Termek[] newArray(int size) {
            return new Termek[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Termek(Parcel in) {
        id = in.readInt();
        termekNev = in.readString();
        egysegAr = in.readDouble();
        mennyiseg = in.readDouble();
        mertekegyseg = in.readString();
        bruttoAr = in.readDouble();
    }
}
