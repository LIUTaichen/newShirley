select 
'w' as 'w',
assetitemid as "ID", 

u_plantid as "FLEET_ID",
plant.Item_Name as "NAME",
plant.Notes as "NOTES",
plant.Purchase_Date as    "PURCHASE_DATE",
 1 as "IS_ACTIVE",
assetitems."DESCRIPTION",
 U_VinNumber as "VIN",
 U_RegoNumber  as "REGO",
 (case
   when u_year is null
   then null
   else  u_year +'-01-01'
   end) as  "DATE_OF_MANUFACTURE",
null as "IMAGE",
null as "IMAGE_CONTENT_TYPE",
 plant.Tank_Size as  "TANK_SIZE",
U_NextServiceKM  as "MAINTENANCE_DUE_AT",
 U_MeterType  as "METER_UNIT",
 U_WOFDueDate   as  "CERTIFICATE_DUE_DATE",
 U_RUCDueAtKM as "RUC_DUE_AT_KM",
 HubboReading as   "HUBBO_READING",
 plant.Load_Capacity as "LOAD_CAPACITY",   
  U_ChargeRateToCivilOverride as "HOURLY_RATE",
  
   U_RegoDueDate as "REGISTRATION_DUE_DATE",
  null as "HIRE_STATUS",
  U_DeviceSerialNo   as "GPS_DEVICE_SERIAL",
 null as "LOCATION",
 null as "LAST_LOCATION_UPDATE_TIME",
category.id as "CATEGORY_ID",
null as"OWNER_ID",
null as "ASSIGNED_CONTRACTOR_ID",
null as "PROJECT_ID",
'System' as "CREATED_BY", getdate() as "CREATED_DATE",
 'System' as "LAST_MODIFIED_BY", 
 getdate() as "LAST_MODIFIED_DATE"


from assetitems 
left join u_assetsubgroup sub on assetitems.U_AssetSubGroup = sub.AssetSubGroup
left join Shirley..access_plant_category category on category.Category = sub.Description
left join Shirley..access_plant plant on assetitems.u_plantid = plant.Fleet_ID
where u_plantid is not null
and closed = 0
and plant.active = 1
and  plant.category is not null
order by id



in h2
insert into plant (ID,FLEET_ID,NAME,NOTES,PURCHASE_DATE,IS_ACTIVE,DESCRIPTION,VIN,REGO,DATE_OF_MANUFACTURE,TANK_SIZE,MAINTENANCE_DUE_AT,METER_UNIT,CERTIFICATE_DUE_DATE,RUC_DUE_AT_KM,HUBBO_READING,LOAD_CAPACITY,HOURLY_RATE,REGISTRATION_DUE_DATE,GPS_DEVICE_SERIAL,CATEGORY_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE)
select ID,FLEET_ID,NAME,NOTES,PURCHASE_DATE,IS_ACTIVE,DESCRIPTION,VIN,REGO,DATE_OF_MANUFACTURE,TANK_SIZE,MAINTENANCE_DUE_AT,METER_UNIT,CERTIFICATE_DUE_DATE,RUC_DUE_AT_KM,HUBBO_READING,LOAD_CAPACITY,HOURLY_RATE,REGISTRATION_DUE_DATE,GPS_DEVICE_SERIAL,CATEGORY_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE from
CSVREAD('src\test\resources\data\nimbus\plant.csv');

update plant set meter_unit = 'HOUR' where meter_unit='Hrs';
update plant set meter_unit = 'KM' where meter_unit='Km';
