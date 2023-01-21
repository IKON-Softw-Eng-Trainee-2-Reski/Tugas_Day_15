CREATE TABLE books (
   id int primary key,
   judul varchar(255),
   pengarang varchar(255),
   kategori varchar(255),
   penerbit varchar(255),
   tahun integer
);

CREATE TABLE members (
   id int primary key,
   nama varchar(255),
   nohp varchar(255),
   alamat varchar(255)
);