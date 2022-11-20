insert into irrigation(id, irrigation_type, amount_of_water, liquid_unit, duration, delay_interval, irrigation_status, next_irrigation_at, created_at, updated_at, version)
values
(-1, 'SPRINKLER', 1000, 'GALLON', 3600 , 21600, 'ACTIVE', now(), now(), now(), 0),
(-2, 'SPRINKLER', 3500, 'GALLON', 7200 , 10800, 'ACTIVE', now(), now(), now(), 0);

insert into land(id, name, description, latitude, longitude, cultivated_area, area_unit, agricultural_crop_type, created_at, updated_at, irrigation_id, version)
values
(-1, 'Corn Land', 'Big corn land in CA', 59.3145508,18.0682806, 2000, 'SQUARE_METER', 'CORN', now(), now(), -1, 0),
(-2, 'Big Cotton Land', 'Big cotton land in LA', 30.3145508,170.0683806, 3050, 'SQUARE_METER', 'COTTON', now(), now(), -2, 0);


insert into irrigation_process_history(id, irrigation_id, process_status, irrigation_type, amount_of_water, liquid_unit, duration, created_at, version)
values
(-1, -1, 'SUCCESS', 'SPRINKLER', 1000, 'GALLON', 3600, now(), 0);
