package com.library.database.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="members")
@NoArgsConstructor
@EqualsAndHashCode
public class Member {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="nama")
    private String nama;

    @Column(name="nohp")
    private String nohp;

    @Column(name="alamat")
    private String alamat;

    public Member(int id, String nama, String nohp, String alamat) {
        this.id = id;
        this.nama = nama;
        this.nohp = nohp;
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorHP() {
        return nohp;
    }

    public void setNomorHP(String nohp) {
        this.nohp = nohp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
