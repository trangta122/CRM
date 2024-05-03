--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

-- Started on 2024-05-02 17:41:13

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
-- TOC entry 4878 (class 0 OID 0)
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
-- TOC entry 226 (class 1259 OID 33290)
-- Name: opportunity_emails; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.opportunity_emails (
    opportunity_id character varying(255) NOT NULL,
    template_emails_id character varying(255) NOT NULL
);


ALTER TABLE public.opportunity_emails OWNER TO postgres;

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
-- TOC entry 4879 (class 0 OID 0)
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
    name character varying(255),
    revenue double precision,
    "order" integer
);


ALTER TABLE public.stages OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 33295)
-- Name: template_emails; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.template_emails (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    name character varying(255),
    physical_path character varying(255),
    type character varying(255)
);


ALTER TABLE public.template_emails OWNER TO postgres;

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
    name character varying(255),
    physical_path character varying(255),
    type character varying(255),
    opportunity_id character varying(255),
    is_template boolean
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
-- TOC entry 4678 (class 2604 OID 16711)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 4868 (class 0 OID 16851)
-- Dependencies: 223
-- Data for Name: activities; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4869 (class 0 OID 16887)
-- Dependencies: 224
-- Data for Name: avatars; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) VALUES ('a0d6294e-c8a8-4598-b9f1-826b89167694', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-22 14:08:34.557', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-22 14:08:34.557', 'fruit.jpg', 'uploads/avatars/20240422140834514_fruit.jpg', 'image/jpeg');
INSERT INTO public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) VALUES ('06a4f704-abed-4857-9d3e-ed40a891dfc3', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 14:22:40.21', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 14:22:40.21', 'spring.jpg', 'uploads/avatars/20240422142240192_spring.jpg', 'image/jpeg');
INSERT INTO public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) VALUES ('40d21eb1-2887-441c-8b82-6851687bf746', 'f8a05ae0-bd52-4f1b-bbdf-ba6c61322adb', '2024-04-22 14:26:43.819', 'f8a05ae0-bd52-4f1b-bbdf-ba6c61322adb', '2024-04-22 14:26:43.819', 'snowflake.png', 'uploads/avatars/20240422142643816_snowflake.png', 'image/png');
INSERT INTO public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) VALUES ('72076454-1fc0-4cdf-ba4f-8e5ab597976d', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 11:45:24.923', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 11:45:24.923', 'default-avatar.jpg', 'uploads/avatars/20240410114524844_default-avatar.jpg', 'image/jpeg');


--
-- TOC entry 4866 (class 0 OID 16829)
-- Dependencies: 221
-- Data for Name: contacts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) VALUES ('a7c0ca9a-2c0f-43db-8fb7-31efb57eb229', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-26 10:26:30.386', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 16:56:25.671', '2002-10-01 07:00:00', 'duong2@gmail.com', 'Duong', 'Hoang Quang Duong', 'Male', 'Hoang Quang', '0344338244', NULL);
INSERT INTO public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) VALUES ('09205d7c-f6d7-43e8-a93c-f4a9da1de69e', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-26 10:31:12.619', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:03:55.596', '2002-10-01 07:00:00', 'duong4@gmail.com', 'Duong', 'Hoang Quang Duong', 'Male', 'Hoang Quang', '0344338244', NULL);
INSERT INTO public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) VALUES ('5e55bc13-20e2-4fef-b28c-fe7a47596d23', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-26 10:17:53.756', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:05:07.133', '2002-10-01 07:00:00', 'duong5@gmail.com', 'Duong', 'Hoang Quang Duong', 'Male', 'Hoang Quang', '0344338244', NULL);
INSERT INTO public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) VALUES ('2c14dd58-7ec5-4d17-a560-c66c049bd1a5', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-26 10:29:19.02', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:06:19.756', '2002-10-01 07:00:00', 'duong3@gmail.com', 'Duong', 'Hoang Quang Duong', 'Male', 'Hoang Quang', '0344338244', NULL);


--
-- TOC entry 4864 (class 0 OID 16800)
-- Dependencies: 219
-- Data for Name: opportunities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('17379c3b-8100-434d-abd8-2746408850d1', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 16:14:02.588', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 15:40:29.955', '122 Tran Hung Dao, Quy Nhơn', 'thanhcong@gmail.com', false, 'Hoa Hai Group', '0344338244', 0, 'www.haihoa.com.vn', '2c5f77f2-a265-4310-9659-889e9cebe276', '785e4053-9c15-46d1-ad80-809aacf36df2', 'Invention app', NULL, 'Tập đoàn Hòa Hải', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('7c5a7198-7929-469d-b28b-f62a93cc9448', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 09:25:23.825', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-05-02 14:18:51.395', '176 Trần Phú, Quy Nhơn, Bình Định', 'abc@gmail.com', false, 'ABC warehouse upgrade', '0344338244', 100000000, 'www.abc.com', '2c5f77f2-a265-4310-9659-889e9cebe276', '8b307d82-1f19-4da2-a868-26801e72f654', 'Warehouse upgrade', NULL, 'ABC Group', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('64dfe7a5-23f9-4934-852e-cb83215754ce', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 16:14:02.573', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:21:10.297', '66 Châu Thị Vĩnh Tế, Ngũ Hành Sơn, Đà Nẵng', 'yen@gmail.com', false, 'yen coffee sales app upgrade', '034433844', 0, 'www.yencoffee.vn', NULL, '785e4053-9c15-46d1-ad80-809aacf36df2', 'Sales management system', NULL, 'Bình Yên là Nhà coffee and tea', NULL, 40.52);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-25 11:25:35.395', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:22:04.7', '66 Châu Thị Vĩnh Tế, Ngũ Hành Sơn, Đà Nẵng', 'yen@gmail.com', false, 'yen coffee sales app upgrade', '034433844', 0, 'www.yencoffee.vn', '2c5f77f2-a265-4310-9659-889e9cebe276', '785e4053-9c15-46d1-ad80-809aacf36df2', 'Sales management system', NULL, 'Bình Yên là Nhà coffee and tea', NULL, 40.52);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('19322716-1689-4d3a-8b0e-14374e34a6a7', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-05-02 16:36:17.6', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-05-02 16:36:17.6', 'Quy Nhon', 'mbbank@gmail.com', false, 'MbBank', '09912821', 100000, 'www.mb.vn', NULL, '63465e2c-2d72-4e1c-ba1a-644ed8c82b96', NULL, NULL, 'MBbank', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('c653183e-382f-466c-a226-89f4e3705237', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-05-02 16:44:23.129', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-05-02 16:44:23.129', 'QuyNhon', 'mbbank@gmail.com', false, 'MBbank', '0998218218', 1200000, 'www.mb.vn', NULL, '63465e2c-2d72-4e1c-ba1a-644ed8c82b96', NULL, NULL, 'MB', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('03d4c143-a83a-40f8-a608-d929ae0d1919', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 09:31:48.071', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:30:42.834', '66 Tố Hữu, Phú Thiện, Gia Lai', 'dalkomcoffee@gmail.com', false, 'Dalkom Coffee sales app', '0344338244', 0, 'www.dalkom.com.vn', '2c5f77f2-a265-4310-9659-889e9cebe276', '785e4053-9c15-46d1-ad80-809aacf36df2', 'Sales managemnet system', 0, 'Dalkoom coffee', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('c074fa5a-8ef5-4670-9564-d1f16ad2a71c', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 16:48:05.885', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-26 09:23:16.091', 'Quy Nhơn', 'hrm@gmail.com', false, NULL, '0318928322', 0, 'hrm.vn', NULL, '785e4053-9c15-46d1-ad80-809aacf36df2', NULL, NULL, 'HRM', NULL, 0);
INSERT INTO public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_reason, probability) VALUES ('1c793647-e4e5-49b1-beaa-f5060c96477b', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-03-21 09:39:41.515', '2c5f77f2-a265-4310-9659-889e9cebe276', '2024-04-11 17:17:47.721', 'Đồng Phó, Tây Giang, Tây Sơn, Bình Định', 'acbc@gmail.com', false, 'Công ty CP Vĩnh Thạnh', '0344338244', 0, 'www.acbc.com', NULL, '8b307d82-1f19-4da2-a868-26801e72f654', 'Maintain sales app', NULL, 'Công ty CP Vĩnh Thạnh', NULL, 0);


--
-- TOC entry 4867 (class 0 OID 16836)
-- Dependencies: 222
-- Data for Name: opportunity_contacts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.opportunity_contacts (opportunity_id, contact_id) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '5e55bc13-20e2-4fef-b28c-fe7a47596d23');
INSERT INTO public.opportunity_contacts (opportunity_id, contact_id) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', 'a7c0ca9a-2c0f-43db-8fb7-31efb57eb229');
INSERT INTO public.opportunity_contacts (opportunity_id, contact_id) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '2c14dd58-7ec5-4d17-a560-c66c049bd1a5');
INSERT INTO public.opportunity_contacts (opportunity_id, contact_id) VALUES ('ed18d9f0-2f5d-46a4-a5c9-8450354cfbec', '09205d7c-f6d7-43e8-a93c-f4a9da1de69e');


--
-- TOC entry 4871 (class 0 OID 33290)
-- Dependencies: 226
-- Data for Name: opportunity_emails; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4861 (class 0 OID 16708)
-- Dependencies: 216
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.roles (id, name) VALUES (2, 'ROLE_USER');


--
-- TOC entry 4865 (class 0 OID 16817)
-- Dependencies: 220
-- Data for Name: stages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, name, revenue, "order") VALUES ('10a1716a-cd71-488e-8f8a-deb8cb8c6dfc', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-09 18:00:37.135', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 16:59:42.787', 'WON', 0, 4);
INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, name, revenue, "order") VALUES ('63465e2c-2d72-4e1c-ba1a-644ed8c82b96', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 08:31:32.717', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 16:59:20.921', 'PROPOSITION', 0, 3);
INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, name, revenue, "order") VALUES ('785e4053-9c15-46d1-ad80-809aacf36df2', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-20 10:57:47.169', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-05-02 14:18:51.35', 'NEW', 0, 1);
INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, name, revenue, "order") VALUES ('8b307d82-1f19-4da2-a868-26801e72f654', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-03-21 09:24:32.286', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-05-02 14:18:51.362', 'QUALIFIED', 100000000, 2);
INSERT INTO public.stages (id, created_by, created_date, last_modified_by, last_modified_date, name, revenue, "order") VALUES ('d7b104c0-5e95-4d3e-90bb-9d1e2633e04e', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-10 08:32:00.243', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-04-22 17:00:06.04', 'LOST', 0, 5);


--
-- TOC entry 4872 (class 0 OID 33295)
-- Dependencies: 227
-- Data for Name: template_emails; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4870 (class 0 OID 16911)
-- Dependencies: 225
-- Data for Name: template_files; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type, opportunity_id, is_template) VALUES ('7da6664b-dfda-44f1-a897-a6c2e76707cf', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-05-02 14:01:07.356', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-05-02 14:01:07.356', '20240502140051130_quotation.pdf', 'uploads/20240502140051130_quotation.pdf', 'PDF', '7c5a7198-7929-469d-b28b-f62a93cc9448', NULL);
INSERT INTO public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type, opportunity_id, is_template) VALUES ('e68e9afb-52eb-4e90-8a2b-7a9d4832dd21', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-05-02 15:33:32.304', '2986dbf3-5770-4b6b-84a2-5f4844e9743a', '2024-05-02 15:33:32.304', '20240502153332241_quotation.docx', 'uploads/20240502153332241_quotation.docx', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', NULL, true);


--
-- TOC entry 4862 (class 0 OID 16715)
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
-- TOC entry 4863 (class 0 OID 16720)
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
-- TOC entry 4880 (class 0 OID 0)
-- Dependencies: 215
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- TOC entry 4698 (class 2606 OID 16857)
-- Name: activities activities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (id);


--
-- TOC entry 4700 (class 2606 OID 16893)
-- Name: avatars avatars_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avatars
    ADD CONSTRAINT avatars_pkey PRIMARY KEY (id);


--
-- TOC entry 4696 (class 2606 OID 16835)
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- TOC entry 4692 (class 2606 OID 16806)
-- Name: opportunities opportunities_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT opportunities_pkey PRIMARY KEY (id);


--
-- TOC entry 4682 (class 2606 OID 16714)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 4694 (class 2606 OID 16823)
-- Name: stages stages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stages
    ADD CONSTRAINT stages_pkey PRIMARY KEY (id);


--
-- TOC entry 4704 (class 2606 OID 33301)
-- Name: template_emails template_emails_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.template_emails
    ADD CONSTRAINT template_emails_pkey PRIMARY KEY (id);


--
-- TOC entry 4702 (class 2606 OID 16917)
-- Name: template_files template_files_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.template_files
    ADD CONSTRAINT template_files_pkey PRIMARY KEY (id);


--
-- TOC entry 4686 (class 2606 OID 16730)
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 4688 (class 2606 OID 16728)
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- TOC entry 4684 (class 2606 OID 16719)
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 4690 (class 2606 OID 16726)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4710 (class 2606 OID 16846)
-- Name: opportunity_contacts fk2gybgffh0nbv90xa7wfboxw20; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunity_contacts
    ADD CONSTRAINT fk2gybgffh0nbv90xa7wfboxw20 FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);


--
-- TOC entry 4714 (class 2606 OID 33312)
-- Name: opportunity_emails fk5swu3v3u5r6akp75jnyn493au; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunity_emails
    ADD CONSTRAINT fk5swu3v3u5r6akp75jnyn493au FOREIGN KEY (template_emails_id) REFERENCES public.opportunities(id);


--
-- TOC entry 4711 (class 2606 OID 16841)
-- Name: opportunity_contacts fk6nnk49tkgwlldvk0xbrj0v8hc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunity_contacts
    ADD CONSTRAINT fk6nnk49tkgwlldvk0xbrj0v8hc FOREIGN KEY (contact_id) REFERENCES public.contacts(id);


--
-- TOC entry 4707 (class 2606 OID 16894)
-- Name: users fk7tamg8hd3ubuy97jk242s0kwf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk7tamg8hd3ubuy97jk242s0kwf FOREIGN KEY (avatar_id) REFERENCES public.avatars(id);


--
-- TOC entry 4713 (class 2606 OID 25233)
-- Name: template_files fk935fl0jj0vwc1bpnirmp5gpdb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.template_files
    ADD CONSTRAINT fk935fl0jj0vwc1bpnirmp5gpdb FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);


--
-- TOC entry 4705 (class 2606 OID 16731)
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 4706 (class 2606 OID 16736)
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4715 (class 2606 OID 33302)
-- Name: opportunity_emails fkhybv94o6sxldwfqxqf3qhonvu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunity_emails
    ADD CONSTRAINT fkhybv94o6sxldwfqxqf3qhonvu FOREIGN KEY (template_emails_id) REFERENCES public.template_emails(id);


--
-- TOC entry 4708 (class 2606 OID 16824)
-- Name: opportunities fkn2gf0yjk6elbl41j89m4k0iwh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT fkn2gf0yjk6elbl41j89m4k0iwh FOREIGN KEY (stage_id) REFERENCES public.stages(id);


--
-- TOC entry 4716 (class 2606 OID 33307)
-- Name: opportunity_emails fkpf5tjexn2kepskxe2m19gw9q; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunity_emails
    ADD CONSTRAINT fkpf5tjexn2kepskxe2m19gw9q FOREIGN KEY (opportunity_id) REFERENCES public.template_emails(id);


--
-- TOC entry 4709 (class 2606 OID 16807)
-- Name: opportunities fksmyq1mklx0rhrp6v2199tsvbh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT fksmyq1mklx0rhrp6v2199tsvbh FOREIGN KEY (salesperson_id) REFERENCES public.users(id);


--
-- TOC entry 4712 (class 2606 OID 16858)
-- Name: activities fktehic8fiattwna4dgrmw35w60; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activities
    ADD CONSTRAINT fktehic8fiattwna4dgrmw35w60 FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);


-- Completed on 2024-05-02 17:41:14

--
-- PostgreSQL database dump complete
--

