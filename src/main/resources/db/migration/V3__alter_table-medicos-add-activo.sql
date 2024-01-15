alter table medicos add activo tinyint;
update medicos set medicos.activo = 1;
