UPDATE creature SET breeding_count=2 WHERE breeding_count=3;
ALTER TABLE creature DROP CHECK creature_chk_2;
ALTER TABLE creature ADD CONSTRAINT creature_chk_2 check (breeding_count between 0 and 2);

drop table shop;
