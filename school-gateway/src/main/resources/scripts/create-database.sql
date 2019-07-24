-- sudo mkdir -p /hub/ssd01/postgres/kyriba/data
-- sudo chown -R postgres:postgres /hub/ssd01/postgres/kyriba
-- sudo su -- postgres -c 'psql postgres'

-- ALTER USER kyriba WITH ENCRYPTED PASSWORD 'zaq12wsx';
-- ALTER USER postgres WITH ENCRYPTED PASSWORD 'zaq12wsx';

DROP DATABASE kyriba;
DROP TABLESPACE kyriba_data;
DROP ROLE kyriba;

CREATE ROLE kyriba LOGIN PASSWORD 'zaq12wsx' SUPERUSER NOINHERIT NOCREATEDB NOCREATEROLE;
CREATE TABLESPACE kyriba_data
  OWNER kyriba LOCATION '/hub/ssd01/postgres/kyriba/data';
CREATE DATABASE kyriba WITH OWNER kyriba ENCODING 'UTF8' TABLESPACE kyriba_data;
