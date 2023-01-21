package com.library.database.demo.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="books")
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
  @Id
  @Column(name="id")
  private int id;

  @Column(name="judul")
  private String judul;

  @Column(name="pengarang")
  private String pengarang;

  @Column(name="kategori")
  private String kategori;

  @Column(name="penerbit")
  private String penerbit;

  @Column(name="tahun")
  private int tahun;

  public Book(int id, String judul, String pengarang, String kategori, String penerbit, int tahun) {
    this.id = id;
    this.judul = judul;
    this.pengarang = pengarang;
    this.kategori = kategori;
    this.penerbit = penerbit;
    this.tahun = tahun;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getJudul() {
    return judul;
  }

  public void setJudul(String judul) {
    this.judul = judul;
  }

  public String getPengarang() {
    return pengarang;
  }

  public void setPengarang(String pengarang) {
    this.pengarang = pengarang;
  }

  public String getKategori() {
    return kategori;
  }

  public void setKategori(String kategori) {
    this.kategori = kategori;
  }

  public String getPenerbit() {
    return penerbit;
  }

  public void setPenerbit(String penerbit) {
    this.penerbit = penerbit;
  }

  public int getTahun() {
    return tahun;
  }

  public void setTahun(int tahun) {
    this.tahun = tahun;
  }
}
