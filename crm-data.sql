--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-04-26 17:52:02

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4863 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 223 (class 1259 OID 16851)
-- Name: activities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.activities (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    detail character varying(255),
    type character varying(255),
    opportunity_id character varying(255),
    date timestamp(6) without time zone,
    is_done boolean,
    summary character varying(255)
);


ALTER TABLE public.activities OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16887)
-- Name: avatars; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.avatars (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    name character varying(255),
    physical_path character varying(255),
    type character varying(255)
);


ALTER TABLE public.avatars OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16829)
-- Name: contacts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contacts (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    birthday timestamp(6) without time zone,
    email character varying(255),
    firstname character varying(255),
    fullname character varying(255),
    gender character varying(255),
    lastname character varying(255),
    phone character varying(255),
    job_position character varying(255)
);


ALTER TABLE public.contacts OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16800)
-- Name: opportunities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.opportunities (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    address character varying(255),
    email character varying(255),
    is_customer boolean,
    name character varying(255),
    phone character varying(255),
    revenue double precision,
    website character varying(255),
    salesperson_id character varying(255),
    stage_id character varying(255),
    description character varying(255),
    priority smallint,
    company character varying(255),
    lost_reason character varying(255),
    probability real,
    CONSTRAINT opportunities_priority_check CHECK (((priority >= 0) AND (priority <= 2)))
);


ALTER TABLE public.opportunities OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16836)
-- Name: opportunity_contacts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.opportunity_contacts (
    opportunity_id character varying(255) NOT NULL,
    contact_id character varying(255) NOT NULL
);


ALTER TABLE public.opportunity_contacts OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16708)
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(255),
    CONSTRAINT roles_name_check CHECK (((name)::text = ANY ((ARRAY['ROLE_USER'::character varying, 'ROLE_ADMIN'::character varying])::text[])))
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16707)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO postgres;

--
-- TOC entry 4864 (class 0 OID 0)
-- Dependencies: 215
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 220 (class 1259 OID 16817)
-- Name: stages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stages (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    code character varying(255),
    name character varying(255),
    revenue double precision NOT NULL
);


ALTER TABLE public.stages OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16911)
-- Name: template_files; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.template_files (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    is_file boolean,
    name character varying(255),
    physical_path character varying(255),
    type character varying(255),
    opportunity_id character varying(255)
);


ALTER TABLE public.template_files OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16715)
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id character varying(255) NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16720)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    birthday timestamp(6) without time zone,
    email character varying(255),
    firstname character varying(255),
    fullname character varying(255),
    gender character varying(255),
    lastname character varying(255),
    password character varying(255),
    phone character varying(255),
    username character varying(255),
    avatar_id character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4670 (class 2604 OID 16711)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 4855 (class 0 OID 16851)
-- Dependencies: 223
-- Data for Name: activities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('b5fd6c73-7be5-4a4d-ab06-47c4ee7b80a4', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-27 09:36:24.247', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-27 09:36:24.247', 'Company tour', NULL, 'ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', NULL, NULL, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('1fd48c4b-627c-4d66-9d50-1534d6d5ac22', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-27 09:47:29.46', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-27 11:39:48.809', 'company summer tour ', 'manual', 'ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '2024-03-29 07:00:00', NULL, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('060753a7-edfb-4444-b865-4697c7356ee7', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-28 17:06:41.605', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-28 17:06:41.605', 'Revenue change from 20.0 to 22.0', 'auto', 'ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '2024-03-28 17:06:41.595', NULL, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('70f58b62-67ea-484c-ac55-ee0c9092f94b', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-28 17:04:09.696', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-28 17:04:09.696', 'Revenue change from 10.0 to 20.0', 'auto', 'ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '2024-03-28 17:04:09.672', NULL, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('34a55194-b623-4eff-9c94-e2fba440c224', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 15:38:16.59', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 15:38:16.59', 'Planned activites| to-do: making proposal on: Sat Apr 27 07:00:00 ICT 2024', 'to-do', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-27 07:00:00', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('049cd70e-3869-4164-9fea-f42453c3ce61', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:18:42.808', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:18:42.808', 'Planned activites | Meeting: CEO Smith on: Apr 28, 2024', 'Meeting', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-28 07:00:00', false, 'CEO Smith');
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('0da822e9-6b3e-4dfd-914b-677b5a534211', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:20:35.326', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:20:35.326', 'Stage changed: NEW -> NEW', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:20:35.325', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('edef27f2-fdd1-4aa8-914b-9e63f86e172d', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:20:35.336', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:20:35.336', 'Expected revenue changed 0.0 -> 2.0E7VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:20:35.333', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('7c1436db-89ad-45be-849d-e24df152ae65', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:20:35.341', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:20:35.341', 'Probability changed: 0.0 -> 15.0', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:20:35.34', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('1d32dcdb-dc7d-41e6-ad6d-95cd186f8b84', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:20:35.346', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:20:35.346', 'Salesperson changed: Nam Phan Huy -> Nam Phan Huy', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:20:35.345', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('ea469b58-c437-4543-850b-792f502f3a33', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:24:21.783', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:24:21.783', 'Stage changed: NEW -> NEW', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:24:21.764', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('0a617cfa-a82f-4563-b863-ccdd66562ac8', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:24:21.829', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:24:21.829', 'Expected revenue changed 20000000 -> 22000000VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:24:21.826', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('52a73cca-da85-4895-85bd-cb8f2805ea8a', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:24:21.836', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:24:21.836', 'Probability changed: 15.0 -> 10.0', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:24:21.835', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('5aabcafb-b016-452f-a06d-0812df622cda', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:24:21.846', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:24:21.846', 'Salesperson changed: Nam Phan Huy -> Nam Phan Huy', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:24:21.845', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('2edab170-f364-448b-a9d8-d71eeee91162', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:44:00.041', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:44:00.041', 'Stage changed: NEW -> NEW', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:43:59.995', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('72e3b423-bbf2-4548-860e-3ca92393fbf3', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:44:00.074', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:44:00.074', 'Expected revenue changed 22000000VND -> 22300000VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:44:00.073', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('085d14c9-89de-4a63-a993-b75d55b4fe03', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:44:00.08', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:44:00.08', 'Probability changed: 10.0 -> 10.5', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:44:00.079', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('eb3f1c52-6a9b-4a21-8860-05104ca85b2c', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:44:00.089', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:44:00.089', 'Salesperson changed: Nam Phan Huy -> Nam Phan Huy', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:44:00.088', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('d3ddcb00-a3b4-4833-adbc-0bab29267a5b', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:50:38.761', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:50:38.761', 'Expected revenue changed 22300000VND -> 12300000VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:50:38.747', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('711083ed-7535-4a97-9ebe-0b09f7fb2c63', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:51:22.274', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 16:51:22.274', 'Probability changed: 10.5 -> 10.58', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 16:51:22.273', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('c3fb68b6-1cd0-4e10-9c19-faa09d168013', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:15:45.895', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:15:45.895', 'Salesperson changed: Nhung Nguyen Hong -> Nam Phan Huy', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:15:45.802', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('3a1159ea-9ae8-4c48-8628-5811d7ea73a0', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:18:13.11', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:18:13.11', 'Stage changed: NEW -> NEW', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:18:13.109', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('8674ec3b-a392-4919-8208-83293f68405b', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:18:13.159', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:18:13.159', 'Expected revenue changed 12300000 VND -> 12300000 VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:18:13.158', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('39013e23-b926-4306-9804-6ed194cf90c6', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:18:13.169', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:18:13.169', 'Probability changed: 10.58 % -> 10.58 %', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:18:13.168', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('61814308-33d7-4b4c-948f-aad14b5e9b0b', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:23:53.386', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:23:53.386', 'Stage changed: NEW -> NEW', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:23:53.346', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('6a772ff3-399b-4c8c-9395-c8634740c97d', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:27:06.972', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:27:06.972', 'Expected revenue changed 12300000 VND -> 12300000 VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:27:06.939', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('6b91d288-6f69-4d77-826f-5e9d1afc4433', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:27:07.011', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:27:07.011', 'Probability changed: 10.58 % -> 10.58 %', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:27:07.009', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('f096a3d4-5427-43a5-accc-9b1d38ee5c65', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:27:29.495', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:27:29.495', 'Expected revenue changed 12300000 VND -> 0 VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:27:29.494', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('21e37d90-b5e0-4383-a6c4-fb7b9bcd82c9', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:27:29.499', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:27:29.499', 'Probability changed: 10.58 % -> 0.0 %', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:27:29.498', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('1b7f8285-2aaf-4a64-baaf-554d890156f9', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:28:44.967', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:28:44.967', 'Expected revenue changed 0 VND -> 55840520 VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:28:44.965', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('2fd4bcbc-b469-4eb1-aadc-fae221e02e72', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:28:44.974', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:28:44.974', 'Probability changed: 0.0 % -> 0.0 %', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:28:44.972', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('fa06988a-6b64-48f9-81a8-c36dcf0f0de8', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:31:30.662', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:31:30.662', 'Stage changed: NEW -> NEW', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:31:30.617', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('a8a19471-d8d5-4b43-bb64-e098f6deae4d', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:42:48.949', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:42:48.949', 'Expected revenue changed 55840520 VND -> 0 VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:42:48.917', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('8bff1572-7b60-43e9-a817-b6636890eaac', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:43:22.078', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:43:22.078', 'Expected revenue changed 0 VND -> 50198520 VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:43:22.077', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('eafcd690-1020-4cb4-b506-fb49cb5a1901', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:45:26.745', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:45:26.745', 'Expected revenue changed 50198520 VND -> 0 VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:45:26.744', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('e1a1db14-1d21-4276-8b42-d289bed5aacf', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:45:52.931', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:45:52.931', 'Expected revenue changed 0 VND -> 65865550 VND', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:45:52.93', false, NULL);
INSERT INTO public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date, is_done, summary) VALUES ('f14f2503-0308-4f77-b152-507979a3ad9c', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:46:35.915', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:46:35.915', 'Stage changed: QUALIFIED -> NEW', 'Auto-log', '7c5a7198-7929-469d-b28b-f62a93cc9448', '2024-04-26 17:46:35.914', false, NULL);


--
-- TOC entry 4856 (class 0 OID 16887)
-- Dependencies: 224
-- Data for Name: avatars; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) VALUES ('06a4f704-abed-4857-9d3e-ed40a891dfc3', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 14:22:40.21', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 14:22:40.21', 'spring.jpg', '/uploads/avatars/20240422142240192_spring.jpg', 'image/jpeg');
INSERT INTO public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) VALUES ('40d21eb1-2887-441c-8b82-6851687bf746', 'f8a05ae0-bd52-4f1b-bbdf-ba6c61322adb', '2024-04-22 14:26:43.819', 'f8a05ae0-bd52-4f1b-bbdf-ba6c61322adb', '2024-04-22 14:26:43.819', 'snowflake.png', '/uploads/avatars/20240422142643816_snowflake.png', 'image/png');
INSERT INTO public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) VALUES ('72076454-1fc0-4cdf-ba4f-8e5ab597976d', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 11:45:24.923', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 11:45:24.923', 'default-avatar.jpg', '/uploads/avatars/20240410114524844_default-avatar.jpg', 'image/jpeg');
INSERT INTO public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) VALUES ('a0d6294e-c8a8-4598-b9f1-826b89167694', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-22 14:08:34.557', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-22 14:08:34.557', 'fruit.jpg', '/uploads/avatars/20240422140834514_fruit.jpg', 'image/jpeg');


--
-- TOC entry 4853 (class 0 OID 16829)
-- Dependencies: 221
-- Data for Name: contacts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) VALUES ('a7c0ca9a-2c0f-43db-8fb7-31efb57eb229', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-26 10:26:30.386', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 16:56:25.671', '2002-10-01 07:00:00', 'duong2@gmail.com', 'Duong', 'Hoang Quang Duong', 'Male', 'Hoang Quang', '0344338244', NULL);
INSERT INTO public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) VALUES ('09205d7c-f6d7-43e8-a93c-f4a9da1de69e', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-26 10:31:12.619', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:03:55.596', '2002-10-01 07:00:00', 'duong4@gmail.com', 'Duong', 'Hoang Quang Duong', 'Male', 'Hoang Quang', '0344338244', NULL);
INSERT INTO public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) VALUES ('5e55bc13-20e2-4fef-b28c-fe7a47596d23', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-26 10:17:53.756', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:05:07.133', '2002-10-01 07:00:00', 'duong5@gmail.com', 'Duong', 'Hoang Quang Duong', 'Male', 'Hoang Quang', '0344338244', NULL);
INSERT INTO public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) VALUES ('2c14dd58-7ec5-4d17-a560-c66c049bd1a5', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-26 10:29:19.02', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:06:19.756', '2002-10-01 07:00:00', 'duong3@gmail.com', 'Duong', 'Hoang Quang Duong', 'Male', 'Hoang Quang', '0344338244', NULL);


--
-- TOC entry 4851 (class 0 OID 16800)
-- Dependencies: 219
-- Data for Name: opportunities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('7c5a7198-7929-469d-b28b-f62a93cc9448', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 09:25:23.825', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:46:35.959', '176 Trần Phú, Quy Nhơn, Bình Định', 'abc@gmail.com', false, 'ABC warehouse upgrade', '0344338244', 65865550, 'www.abc.com', '2c5f77f2-a265-4310-9659-889e9cebe276', '785e4053-9c15-46d1-ad80-809aacf36df2', 'Warehouse upgrade', NULL, 'ABC Group', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('17379c3b-8100-434d-abd8-2746408850d1', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 16:14:02.588', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 15:40:29.955', '122 Tran Hung Dao, Quy Nhơn', 'thanhcong@gmail.com', false, 'Hoa Hai Group', '0344338244', 0, 'www.haihoa.com.vn', '2c5f77f2-a265-4310-9659-889e9cebe276', '785e4053-9c15-46d1-ad80-809aacf36df2', 'Invention app', NULL, 'Tập đoàn Hòa Hải', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('64dfe7a5-23f9-4934-852e-cb83215754ce', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 16:14:02.573', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:21:10.297', '66 Châu Thị Vĩnh Tế, Ngũ Hành Sơn, Đà Nẵng', 'yen@gmail.com', false, 'yen coffee sales app upgrade', '034433844', 0, 'www.yencoffee.vn', NULL, '785e4053-9c15-46d1-ad80-809aacf36df2', 'Sales management system', NULL, 'Bình Yên là Nhà coffee and tea', NULL, 40.52);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-25 11:25:35.395', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:22:04.7', '66 Châu Thị Vĩnh Tế, Ngũ Hành Sơn, Đà Nẵng', 'yen@gmail.com', false, 'yen coffee sales app upgrade', '034433844', 0, 'www.yencoffee.vn', '2c5f77f2-a265-4310-9659-889e9cebe276', '785e4053-9c15-46d1-ad80-809aacf36df2', 'Sales management system', NULL, 'Bình Yên là Nhà coffee and tea', NULL, 40.52);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('03d4c143-a83a-40f8-a608-d929ae0d1919', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 09:31:48.071', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:30:42.834', '66 Tố Hữu, Phú Thiện, Gia Lai', 'dalkomcoffee@gmail.com', false, 'Dalkom Coffee sales app', '0344338244', 0, 'www.dalkom.com.vn', '2c5f77f2-a265-4310-9659-889e9cebe276', '785e4053-9c15-46d1-ad80-809aacf36df2', 'Sales managemnet system', 0, 'Dalkoom coffee', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('c074fa5a-8ef5-4670-9564-d1f16ad2a71c', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 16:48:05.885', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:23:16.091', 'Quy Nhơn', 'hrm@gmail.com', false, NULL, '0318928322', 0, 'hrm.vn', NULL, '785e4053-9c15-46d1-ad80-809aacf36df2', NULL, NULL, 'HRM', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('1c793647-e4e5-49b1-beaa-f5060c96477b', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-21 09:39:41.515', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:17:47.721', 'Đồng Phó, Tây Giang, Tây Sơn, Bình Định', 'acbc@gmail.com', false, 'Công ty CP Vĩnh Thạnh', '0344338244', 0, 'www.acbc.com', NULL, '8b307d82-1f19-4da2-a868-26801e72f654', 'Maintain sales app', NULL, 'Công ty CP Vĩnh Thạnh', NULL, 0);


--
-- TOC entry 4854 (class 0 OID 16836)
-- Dependencies: 222
-- Data for Name: opportunity_contacts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.opportunity_contacts (opportunity_id, contact_id) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '5e55bc13-20e2-4fef-b28c-fe7a47596d23');
INSERT INTO public.opportunity_contacts (opportunity_id, contact_id) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', 'a7c0ca9a-2c0f-43db-8fb7-31efb57eb229');
INSERT INTO public.opportunity_contacts (opportunity_id, contact_id) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '2c14dd58-7ec5-4d17-a560-c66c049bd1a5');
INSERT INTO public.opportunity_contacts (opportunity_id, contact_id) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '09205d7c-f6d7-43e8-a93c-f4a9da1de69e');


--
-- TOC entry 4848 (class 0 OID 16708)
-- Dependencies: 216
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles (id, name) VALUES (2, 'ROLE_USER');


--
-- TOC entry 4852 (class 0 OID 16817)
-- Dependencies: 220
-- Data for Name: stages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, code, name, revenue) VALUES ('8b307d82-1f19-4da2-a868-26801e72f654', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 09:24:32.286', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:46:35.921', 'dff78894-2fb6-49b7-997d-31d9c7de0c5c', 'QUALIFIED', -50198520);
INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, code, name, revenue) VALUES ('785e4053-9c15-46d1-ad80-809aacf36df2', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-20 10:57:47.169', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 17:46:35.954', 'b2dbd0f5-d836-4e6c-b469-8c8aa557848f', 'NEW', 65865550);
INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, code, name, revenue) VALUES ('63465e2c-2d72-4e1c-ba1a-644ed8c82b96', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 08:31:32.717', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 16:59:20.921', 'cca19447-a5e4-4486-bbca-8dc264cdb170', 'PROPOSITION', 0);
INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, code, name, revenue) VALUES ('10a1716a-cd71-488e-8f8a-deb8cb8c6dfc', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-09 18:00:37.135', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 16:59:42.787', 'ebf8df0a-27d4-438b-8fe8-bc029005ad4b', 'WON', 0);
INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, code, name, revenue) VALUES ('d7b104c0-5e95-4d3e-90bb-9d1e2633e04e', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 08:32:00.243', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 17:00:06.04', 'd464cf37-8ad8-4039-843f-78304e7ab28d', 'LOST', 0);


--
-- TOC entry 4857 (class 0 OID 16911)
-- Dependencies: 225
-- Data for Name: template_files; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) VALUES ('53d1cec0-d3ee-446f-8af6-e18d63ddfe19', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-15 15:00:58.383', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-15 15:00:58.383', false, 'quotation.docx', '20240415150058227_quotation.docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL);
INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) VALUES ('279ab0d8-968d-4bc2-b242-d75e2f08e514', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-25 17:34:09.771', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-25 17:34:09.771', true, 'quotation', '/uploads/20240425173408588_quotation.docx', 'docx', 'ed18d9f0-2f5d-46a4-a5c9-8450354cfbec');
INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) VALUES ('be555ee6-7a67-496f-9e7e-b457664080f6', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 08:51:54.276', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 08:51:54.276', true, 'quotation', '/uploads/20240426085152187_quotation.docx', 'docx', 'ed18d9f0-2f5d-46a4-a5c9-8450354cfbec');
INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) VALUES ('ef1eff58-5336-46c9-8750-3860935ff5fb', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:00:09.267', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:00:09.267', true, 'quotation', '/uploads/20240426090009131_quotation.docx', 'docx', 'ed18d9f0-2f5d-46a4-a5c9-8450354cfbec');
INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) VALUES ('3b62ee1d-1209-427a-9bc3-1b0d852924d8', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:05:05.661', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:05:05.661', true, 'quotation', '/uploads/20240426090503969_quotation.docx', 'docx', 'ed18d9f0-2f5d-46a4-a5c9-8450354cfbec');
INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) VALUES ('f5edd91d-90dc-4f94-a3cb-b994443029e3', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 14:26:42.456', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 14:26:42.456', false, 'bean.jpg', '/uploads/20240426142642359_bean.jpg', 'image/jpeg', NULL);
INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) VALUES ('9bd42745-d5c1-47e9-b32b-c4cd44a958f3', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 14:33:55.282', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 14:33:55.282', true, '20240426143355170_fruit.jpg', '/uploads/20240426143355170_fruit.jpg', 'image/jpeg', NULL);
INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) VALUES ('e8049ba8-9165-45cf-9fb3-2bbbbf3375cc', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 15:14:15.605', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 15:14:15.605', true, '20240426151415517_peach.png', '/uploads/20240426151415517_peach.png', 'image/png', NULL);


--
-- TOC entry 4849 (class 0 OID 16715)
-- Dependencies: 217
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_roles (user_id, role_id) VALUES ('2986dbf3-5770-4b6b-84a2-5f4844e9743a', 1);
INSERT INTO public.user_roles (user_id, role_id) VALUES ('c90c1f8f-b880-4f0e-af84-691837989c93', 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES ('2c5f77f2-a265-4310-9659-889e9cebe276', 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES ('f8a05ae0-bd52-4f1b-bbdf-ba6c61322adb', 1);
INSERT INTO public.user_roles (user_id, role_id) VALUES ('714f4814-19e7-4a3a-9999-6308ecefb51d', 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES ('bc8be15d-5ae7-481e-b08d-0d943016fb32', 1);


--
-- TOC entry 4850 (class 0 OID 16720)
-- Dependencies: 218
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, password, phone, username, avatar_id) VALUES ('f8a05ae0-bd52-4f1b-bbdf-ba6c61322adb', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 14:44:41.768', '11111111-1111-1111-1111-11111111111', '2024-04-23 14:52:46.176', '2002-11-01 07:00:00', 'ttt-batch15bd@sdc.edu.vn', 'Nhung', 'Nhung Nguyen Hong', 'Female', 'Nguyen Hong', '$2a$10$IZBqZqCpS1YrTn3JosmiEu.Tqy2jEr6gVrpk.gYZsB2CWRIKTGEry', '0344338244', 'nhungnh', '40d21eb1-2887-441c-8b82-6851687bf746');
INSERT INTO public.users (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, password, phone, username, avatar_id) VALUES ('c90c1f8f-b880-4f0e-af84-691837989c93', 'Administrator', '2024-03-18 14:47:30.316', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 14:15:25.764', '2002-05-30 07:00:00', 'quangnd@gmail.com', 'Quang', 'Quang Nguyen Duy', 'Male', 'Nguyen Duy', '$2a$10$GZ8bzRfhGdwY7EmCl83FAe180TrXr7h7sEVjv.BzLga6weHRZIi4C', '0349857122', 'quangnd', '72076454-1fc0-4cdf-ba4f-8e5ab597976d');
INSERT INTO public.users (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, password, phone, username, avatar_id) VALUES ('2986dbf3-5770-4b6b-84a2-5f4844e9743a', 'Administrator', '2024-03-18 14:17:39.994', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-23 15:00:07.678', '2002-02-12 07:00:00', 'trangtt@gmail.com', 'Trang', 'Trang Ta Thi', 'Female', 'Ta Thi', '$2a$10$w5rPavUrfunlDTw9zojSMuIRhFaTM7qK0oo85c9sp5CefNS5.ui5W', '0344338244', 'trangtt', '06a4f704-abed-4857-9d3e-ed40a891dfc3');
INSERT INTO public.users (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, password, phone, username, avatar_id) VALUES ('714f4814-19e7-4a3a-9999-6308ecefb51d', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-24 16:26:17.631', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-24 16:26:17.631', '2002-08-07 07:00:00', 'sanght@gmail.com', 'Sang', 'Sang Huynh Thanh', 'string', 'Huynh Thanh', '$2a$10$smNJbOUaa9hpSWvIEkwYuep7816M2hj07HQRYfRJ4o6G5srpPDJk2', 'string', 'sanght', '72076454-1fc0-4cdf-ba4f-8e5ab597976d');
INSERT INTO public.users (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, password, phone, username, avatar_id) VALUES ('bc8be15d-5ae7-481e-b08d-0d943016fb32', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-24 16:31:04.714', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-24 16:31:04.714', '2002-07-08 07:00:00', 'sang@gmail.com', 'Thanh Sang', 'Thanh Sang Huỳnh Thị', 'female', 'Huỳnh Thị', '$2a$10$GIwwvhx4XJ0AHSVSoiOage6S3O4thFsmbKmZ.5lcDJ2sExSwqgCQC', '0326917348', 'thanhsang', '72076454-1fc0-4cdf-ba4f-8e5ab597976d');
INSERT INTO public.users (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, password, phone, username, avatar_id) VALUES ('2c5f77f2-a265-4310-9659-889e9cebe276', 'trangtt', '2024-03-18 16:42:45.675', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-22 14:08:34.611', '2001-09-21 07:00:00', 'namph@gmail.com', 'Nam', 'Nam Phan Huy', 'Male', 'Phan Huy', '$2a$10$dp.BxVNnZKuwh2z6Rh5Rqua59lv.1XCqyymVAeVJh1N2rKHqQBIGW', '0344338244', 'namph', 'a0d6294e-c8a8-4598-b9f1-826b89167694');


--
-- TOC entry 4865 (class 0 OID 0)
-- Dependencies: 215
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- TOC entry 4690 (class 2606 OID 16857)
-- Name: activities activities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (id);


--
-- TOC entry 4692 (class 2606 OID 16893)
-- Name: avatars avatars_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avatars
    ADD CONSTRAINT avatars_pkey PRIMARY KEY (id);


--
-- TOC entry 4688 (class 2606 OID 16835)
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- TOC entry 4684 (class 2606 OID 16806)
-- Name: opportunities opportunities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT opportunities_pkey PRIMARY KEY (id);


--
-- TOC entry 4674 (class 2606 OID 16714)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4686 (class 2606 OID 16823)
-- Name: stages stages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stages
    ADD CONSTRAINT stages_pkey PRIMARY KEY (id);


--
-- TOC entry 4694 (class 2606 OID 16917)
-- Name: template_files template_files_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.template_files
    ADD CONSTRAINT template_files_pkey PRIMARY KEY (id);


--
-- TOC entry 4678 (class 2606 OID 16730)
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 4680 (class 2606 OID 16728)
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- TOC entry 4676 (class 2606 OID 16719)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 4682 (class 2606 OID 16726)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4700 (class 2606 OID 16846)
-- Name: opportunity_contacts fk2gybgffh0nbv90xa7wfboxw20; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunity_contacts
    ADD CONSTRAINT fk2gybgffh0nbv90xa7wfboxw20 FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);


--
-- TOC entry 4701 (class 2606 OID 16841)
-- Name: opportunity_contacts fk6nnk49tkgwlldvk0xbrj0v8hc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunity_contacts
    ADD CONSTRAINT fk6nnk49tkgwlldvk0xbrj0v8hc FOREIGN KEY (contact_id) REFERENCES public.contacts(id);


--
-- TOC entry 4697 (class 2606 OID 16894)
-- Name: users fk7tamg8hd3ubuy97jk242s0kwf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk7tamg8hd3ubuy97jk242s0kwf FOREIGN KEY (avatar_id) REFERENCES public.avatars(id);


--
-- TOC entry 4703 (class 2606 OID 25233)
-- Name: template_files fk935fl0jj0vwc1bpnirmp5gpdb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.template_files
    ADD CONSTRAINT fk935fl0jj0vwc1bpnirmp5gpdb FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);


--
-- TOC entry 4695 (class 2606 OID 16731)
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 4696 (class 2606 OID 16736)
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4698 (class 2606 OID 16824)
-- Name: opportunities fkn2gf0yjk6elbl41j89m4k0iwh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT fkn2gf0yjk6elbl41j89m4k0iwh FOREIGN KEY (stage_id) REFERENCES public.stages(id);


--
-- TOC entry 4699 (class 2606 OID 16807)
-- Name: opportunities fksmyq1mklx0rhrp6v2199tsvbh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT fksmyq1mklx0rhrp6v2199tsvbh FOREIGN KEY (salesperson_id) REFERENCES public.users(id);


--
-- TOC entry 4702 (class 2606 OID 16858)
-- Name: activities fktehic8fiattwna4dgrmw35w60; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activities
    ADD CONSTRAINT fktehic8fiattwna4dgrmw35w60 FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);


-- Completed on 2024-04-26 17:52:02

--
-- PostgreSQL database dump complete
--

