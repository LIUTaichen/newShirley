in nimbus

select category."ID" as id,"CATEGORY",category."DESCRIPTION", 
category."Type" as JHI_type, category."TRACK_USAGE",
isnull(category.Day_Rate, '')   as "DAILY_RATE",
isnull(category."LOAD_CAPACITY", '') as"LOAD_CAPACITY" , 
isnull(category.Load_Hourly_Rate, '' ) as "HOURLY_RATE",
isnull(category.Earthmoving_Plant,'')  as "IS_EARCH_MOVING_PLANT", 
isnull(category.Monthly_Cost_Report,'') as "IS_TRACKED_FOR_INTERNAL_BILLING", 
isnull(category.Competency_Area, '') as "COMPETENCY_ID", 
'System' as "CREATED_BY", getdate() as "CREATED_DATE",
 'System' as "LAST_MODIFIED_BY", 
 getdate() as "LAST_MODIFIED_DATE", 
 '' as MAINTENANCE_GROUP

from u_assetsubgroup subgroup left join Shirley..access_plant_category category on 
category.Category = subgroup.Description

where category.id is not null




in h2

insert into category   (ID,CATEGORY,DESCRIPTION,JHI_type,TRACK_USAGE,DAILY_RATE,LOAD_CAPACITY,HOURLY_RATE,IS_EARCH_MOVING_PLANT,IS_TRACKED_FOR_INTERNAL_BILLING, CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE)             SELECT ID,CATEGORY,DESCRIPTION,JHI_type,TRACK_USAGE,DAILY_RATE,LOAD_CAPACITY,HOURLY_RATE,IS_EARCH_MOVING_PLANT,IS_TRACKED_FOR_INTERNAL_BILLING, CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE
 FROM CSVREAD('src\test\resources\data\shirley\category.csv');


update category set competency_id=7 where id =1;
update category set competency_id=19 where id =2;
update category set competency_id=19 where id =3;
update category set competency_id=14 where id =4;
update category set competency_id=3 where id =5;
update category set competency_id=17 where id =6;
update category set competency_id=16 where id =7;
update category set competency_id=15 where id =8;
update category set competency_id=13 where id =9;
update category set competency_id=32 where id =10;
update category set competency_id=22 where id =11;
update category set competency_id=18 where id =12;
update category set competency_id=7 where id =18;
update category set competency_id=7 where id =20;
update category set competency_id=4 where id =22;
update category set competency_id=4 where id =23;
update category set competency_id=7 where id =24;
update category set competency_id=0 where id =25;
update category set competency_id=0 where id =26;
update category set competency_id=16 where id =27;
update category set competency_id=0 where id =31;
update category set competency_id=0 where id =32;
update category set competency_id=32 where id =33;
update category set competency_id=1 where id =36;
update category set competency_id=11 where id =37;
update category set competency_id=23 where id =38;
update category set competency_id=2 where id =40;
update category set competency_id=11 where id =42;
update category set competency_id=11 where id =44;
update category set competency_id=26 where id =48;
update category set competency_id=32 where id =49;
update category set competency_id=26 where id =58;
update category set competency_id=27 where id =59;
update category set competency_id=0 where id =64;
update category set competency_id=21 where id =68;
update category set competency_id=0 where id =72;
update category set competency_id=2 where id =73;
update category set competency_id=16 where id =75;
update category set competency_id=0 where id =76;
update category set competency_id=12 where id =77;
update category set competency_id=15 where id =78;
update category set competency_id=18 where id =79;
update category set competency_id=18 where id =80;
update category set competency_id=14 where id =84;
update category set competency_id=19 where id =86;
update category set competency_id=0 where id =100;
update category set competency_id=32 where id =107;
update category set competency_id=12 where id =109;
update category set competency_id=16 where id =110;
update category set competency_id=12 where id =111;
update category set competency_id=0 where id =112;
update category set competency_id=17 where id =114;
update category set competency_id=0 where id =115;
update category set competency_id=0 where id =119;




update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =1;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =2;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =3;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =4;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =5;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =6;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =7;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =8;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =9;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =10;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =11;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =12;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =18;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =20;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =22;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =23;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =24;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =25;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =26;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =27;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =31;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =32;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =33;
update category set MAINTENANCE_GROUP='WHITE_FLEET'  where id =36;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =37;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =38;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =40;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =42;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =44;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =48;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =49;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =58;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =59;
update category set MAINTENANCE_GROUP='WHITE_FLEET'  where id =64;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =68;
update category set MAINTENANCE_GROUP='WHITE_FLEET'  where id =72;
update category set MAINTENANCE_GROUP='WHITE_FLEET'  where id =73;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =75;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =76;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =77;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =78;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =79;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =80;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =84;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =86;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =100;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =107;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =109;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =110;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =111;
update category set MAINTENANCE_GROUP='WHITE_FLEET'  where id =112;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =114;
update category set MAINTENANCE_GROUP='WHITE_FLEET'  where id =115;
update category set MAINTENANCE_GROUP='YELLOW_FLEET'  where id =119;
