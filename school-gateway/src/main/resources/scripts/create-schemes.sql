-- sudo su -- postgres -c  'psql kyriba'
DROP SCHEMA IF EXISTS school CASCADE;

/* CREATE EXTENTIONS */
CREATE EXTENSION IF NOT EXISTS pgcrypto;

/* CREATE SCHEMAS */
CREATE SCHEMA school AUTHORIZATION kyriba;
