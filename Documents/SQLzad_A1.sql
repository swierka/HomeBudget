set sql_safe_updates=0r;

Create table pracownicy (
 id INT auto_increment primary key,
 imie  VARCHAR (30),
 nazwisko VARCHAR (40) not null,
 wyplata decimal (20,2),
 data_urodzenia date,
 stanowisko VARCHAR(40)
 );

INSERT into pracownicy values
     (1,'Tomek','Nowak',10450.00,'1983-02-26','windows admin'), 
     (2,'Rafał','Takiadmin',10450.00,'1986-06-26','windows admin'), 
     (3,'Tomek','Bem',12450.00,'1985-06-02','android developer'), 
     (4,'Mariola','Dmowska',12450.00,'1990-04-06','android developer'), 
     (5,'Zofia','Traugutt',7550.00,'1986-07-10','analityk'), 
     (6,'Maria','Ważna',14450.00,'1984-07-26','manager'),
	 (7,'Marek','Bazodanowiec',10450.00,'1963-06-26','db admin');
          
select * from pracownicy order by nazwisko;
select * from pracownicy where stanowisko = 'windows admin';
select * from pracownicy where (datediff(data_urodzenia,now()))/-365 > 30;
Update pracownicy set wyplata = wyplata*1.1 where stanowisko = 'windows admin';
SELECT * from pracownicy where data_urodzenia = (select max(data_urodzenia) from pracownicy);
delete p.* from pracownicy p where data_urodzenia IN (select data_urodzenia from (SELECT * from pracownicy where data_urodzenia = (select max(data_urodzenia) from pracownicy)) najmlodszy);