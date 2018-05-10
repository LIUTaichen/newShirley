CALL CSVWRITE('src\test\resources\data\company.csv', 'SELECT * FROM company');
CALL CSVWRITE('src\test\resources\data\category.csv', 'SELECT * FROM category');
CALL CSVWRITE('src\test\resources\data\plant.csv', 'SELECT * FROM plant');
CALL CSVWRITE('src\test\resources\data\niggle.csv', 'SELECT * FROM niggle');
CALL CSVWRITE('src\test\resources\data\MAINTENANCE_CONTRACTOR.csv', 'SELECT * FROM MAINTENANCE_CONTRACTOR ');
CALL CSVWRITE('src\test\resources\data\people.csv', 'SELECT * FROM people');
CALL CSVWRITE('src\test\resources\data\project.csv', 'SELECT * FROM project');


delete from niggle;
delete from plant;
delete from people;
delete from project;
delete from MAINTENANCE_CONTRACTOR;
delete from category;
delete from company;

insert into company SELECT * FROM CSVREAD('src\test\resources\data\company.csv');
insert into category SELECT * FROM CSVREAD('src\test\resources\data\category.csv');
insert into MAINTENANCE_CONTRACTOR SELECT * FROM CSVREAD('src\test\resources\data\MAINTENANCE_CONTRACTOR.csv');
insert into project SELECT * FROM CSVREAD('src\test\resources\data\project.csv');
insert into people SELECT * FROM CSVREAD('src\test\resources\data\people.csv');
insert into plant SELECT * FROM CSVREAD('src\test\resources\data\plant.csv');
insert into niggle SELECT * FROM CSVREAD('src\test\resources\data\niggle.csv');