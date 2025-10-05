--insert into public.profile (id, created_date, enabled, updated_date, version, address, fathername, firstname, gender,
--                            isactive, lastname, password, phonenumber, username)
--values (nextval('profile_seq'),now(),true,now(),1,'Brussels','Mohammad','Meysam',0,true,'amini','$2a$10$xVDL52o1WStgXzyN1FfTSOT70zj8.ZtsbSmnMmAHu1opEKnIQ1P52','09218364180','meysam');
--
--
--insert into public.role (id, created_date, enabled, updated_date, version, code, name)
--values (nextval('role_seq'),now(),true,now(),1,'01','ADMIN');
--
--insert into public.permission (id, created_date, enabled, updated_date, version, code, enkey, name)
--values (nextval('permission_seq'),now(),true,now(),1,'01','ADD_USER','ADD_USER');
--
--insert into public.permission (id, created_date, enabled, updated_date, version, code, enkey, name)
--values (nextval('permission_seq'),now(),true,now(),1,'02','QUERY_USER','QUERY_USER');
--
--insert into rolepermission (id, created_date, enabled, updated_date, version, permission, role)
--values (nextval('role_permission_seq'),now(),true,now(),1,1,1);
--
--insert into rolepermission (id, created_date, enabled, updated_date, version, permission, role)
--values (nextval('role_permission_seq'),now(),true,now(),1,2,1);
--
--insert into profilerole (id, createdat, createdby, isactive, modifiedat, modifiedby, profile, role)
--values (nextval('profile_role_seq'),now(),1,true,now(),1,1,1);

--backup--
-- =====================================
--  CLEAN FIXED SQL FOR pgAdmin EXECUTION
-- =====================================

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', 'public', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- -------------------------------------
-- ⚠️ Skip creating "postgres" database
-- -------------------------------------
-- You’re already connected to a database.
-- Just create tables and data in the current DB.

SET default_tablespace = '';
SET default_table_access_method = heap;

-- -------------------------
-- Table: coin
-- -------------------------
CREATE TABLE IF NOT EXISTS public.coin (
    id bigint PRIMARY KEY,
    created_date timestamp(6) without time zone,
    enabled boolean NOT NULL,
    updated_date timestamp(6) without time zone,
    version bigint NOT NULL,
    network character varying(255) NOT NULL,
    symbol character varying(255) NOT NULL
);

-- -------------------------
-- Table: failed_notif
-- -------------------------
CREATE SEQUENCE IF NOT EXISTS public.failed_notif_id_seq
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE IF NOT EXISTS public.failed_notif (
    id bigint PRIMARY KEY DEFAULT nextval('public.failed_notif_id_seq'::regclass),
    createddate timestamp(6) without time zone,
    trackingcode integer,
    retrycount integer,
    status smallint,
    destination character varying(255),
    msg character varying(255),
    sender character varying(255),
    type smallint,
    CONSTRAINT failed_notif_status_check CHECK ((status >= 0) AND (status <= 3)),
    CONSTRAINT failed_notif_type_check CHECK ((type >= 0) AND (type <= 1))
);

-- -------------------------
-- Table: general_properties
-- -------------------------
CREATE TABLE IF NOT EXISTS public.general_properties (
    id bigint PRIMARY KEY,
    created_date timestamp(6) without time zone,
    enabled boolean NOT NULL,
    updated_date timestamp(6) without time zone,
    version bigint NOT NULL,
    setting_key character varying(255) UNIQUE,
    setting_value character varying(255)
);

-- -------------------------
-- Table: member
-- -------------------------
CREATE TABLE IF NOT EXISTS public.member (
    id bigint PRIMARY KEY,
    created_date timestamp(6) without time zone,
    enabled boolean NOT NULL,
    updated_date timestamp(6) without time zone,
    version bigint NOT NULL,
    email character varying(50) NOT NULL UNIQUE,
    first_name character varying(50),
    last_name character varying(50),
    phone character varying(11) UNIQUE,
    username character varying(255) NOT NULL UNIQUE
);

-- -------------------------
-- Table: member_wallet
-- -------------------------
CREATE TABLE IF NOT EXISTS public.member_wallet (
    id bigint PRIMARY KEY,
    created_date timestamp(6) without time zone,
    enabled boolean NOT NULL,
    updated_date timestamp(6) without time zone,
    version bigint NOT NULL,
    address character varying(255) NOT NULL,
    coin_unit character varying(255) NOT NULL,
    member_id bigint REFERENCES public.member(id),
    CONSTRAINT memberwallet UNIQUE (member_id, coin_unit)
);

-- -------------------------
-- Table: permission
-- -------------------------
CREATE TABLE IF NOT EXISTS public.permission (
    id bigint PRIMARY KEY,
    created_date timestamp(6) without time zone,
    enabled boolean NOT NULL,
    updated_date timestamp(6) without time zone,
    version bigint NOT NULL,
    code character varying(255),
    enkey character varying(255),
    in_menu boolean,
    name character varying(255),
    parent bigint
);

-- -------------------------
-- Table: profile
-- -------------------------
CREATE TABLE IF NOT EXISTS public.profile (
    id bigint PRIMARY KEY,
    created_date timestamp(6) without time zone,
    enabled boolean NOT NULL,
    updated_date timestamp(6) without time zone,
    version bigint NOT NULL,
    address character varying(255),
    fathername character varying(255),
    firstname character varying(255),
    gender bigint,
    isactive boolean,
    lastname character varying(255),
    password character varying(255),
    phonenumber character varying(255),
    username character varying(255)
);

-- -------------------------
-- Table: profilerole
-- -------------------------
CREATE TABLE IF NOT EXISTS public.profilerole (
    id bigint PRIMARY KEY,
    createdat timestamp(6) without time zone,
    createdby bigint,
    isactive boolean,
    modifiedat timestamp(6) without time zone,
    modifiedby bigint,
    profile bigint,
    role bigint
);

-- -------------------------
-- Table: role
-- -------------------------
CREATE TABLE IF NOT EXISTS public.role (
    id bigint PRIMARY KEY,
    created_date timestamp(6) without time zone,
    enabled boolean NOT NULL,
    updated_date timestamp(6) without time zone,
    version bigint NOT NULL,
    code character varying(255),
    name character varying(255)
);

-- -------------------------
-- Table: rolepermission
-- -------------------------
CREATE TABLE IF NOT EXISTS public.rolepermission (
    id bigint PRIMARY KEY,
    created_date timestamp(6) without time zone,
    enabled boolean NOT NULL,
    updated_date timestamp(6) without time zone,
    version bigint NOT NULL,
    permission bigint,
    role bigint
);

-- -------------------------
-- Data Inserts
-- -------------------------
INSERT INTO public.permission VALUES
(3, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, '03', 'QUERY_USER', false, 'Query User', 1),
(2, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, '02', 'ADD_USER', false, 'Add User', 1),
(1, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, '01', 'MEMBER_MANAGEMENT', true, 'Member Management', NULL),
(4, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, '04', 'ROLE_MANAGEMENT', true, 'Role Management', NULL),
(5, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, '04', 'PERMISSION_MANAGEMENT', true, 'Permission Management', NULL);

INSERT INTO public.profile VALUES
(1, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, 'Brussels', 'Mohammad', 'Meysam', 0, true, 'amini', '$2a$10$xVDL52o1WStgXzyN1FfTSOT70zj8.ZtsbSmnMmAHu1opEKnIQ1P52', '09218364180', 'meysam');

INSERT INTO public.profilerole VALUES
(1, '2025-08-24 16:47:21.756414', 1, true, '2025-08-24 16:47:21.756414', 1, 1, 1);

INSERT INTO public.role VALUES
(1, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, '01', 'SUPER_ADMIN'),
(2, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, '02', 'ADMIN'),
(4, '2025-08-25 00:37:22.538', true, '2025-08-25 00:37:22.538', 1, '66', 'ACCOUNTS_ADMIN');

INSERT INTO public.rolepermission VALUES
(1, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, 1, 1),
(2, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, 2, 1),
(3, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, 3, 1),
(4, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, 4, 1),
(5, '2025-08-24 16:47:21.756414', true, '2025-08-24 16:47:21.756414', 1, 5, 1),
(7, '2025-08-25 00:36:30.771', true, '2025-08-25 00:36:30.771', 0, 3, 2),
(8, '2025-08-25 00:36:30.778', true, '2025-08-25 00:36:30.778', 0, 1, 2);

-- Sequence value fix
SELECT pg_catalog.setval('public.failed_notif_id_seq', 1, false);

-- ✅ All done!
