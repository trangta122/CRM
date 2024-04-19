PGDMP  '    /    
            |            CRM    16.2    16.2 6               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            
           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16397    CRM    DATABASE     }   CREATE DATABASE "CRM" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Vietnamese_Vietnam.1258';
    DROP DATABASE "CRM";
                postgres    false            �            1259    16851 
   activities    TABLE     �  CREATE TABLE public.activities (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    detail character varying(255),
    type character varying(255),
    opportunity_id character varying(255),
    date timestamp(6) without time zone
);
    DROP TABLE public.activities;
       public         heap    postgres    false            �            1259    16887    avatars    TABLE     p  CREATE TABLE public.avatars (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    name character varying(255),
    physical_path character varying(255),
    type character varying(255)
);
    DROP TABLE public.avatars;
       public         heap    postgres    false            �            1259    16829    contacts    TABLE     2  CREATE TABLE public.contacts (
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
    DROP TABLE public.contacts;
       public         heap    postgres    false            �            1259    16932    files    TABLE     n  CREATE TABLE public.files (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    name character varying(255),
    physical_path character varying(255),
    type character varying(255)
);
    DROP TABLE public.files;
       public         heap    postgres    false            �            1259    16800    opportunities    TABLE     �  CREATE TABLE public.opportunities (
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
    lost_note character varying(255),
    lost_reason character varying(255),
    probability real,
    template_file_id character varying(255),
    CONSTRAINT opportunities_priority_check CHECK (((priority >= 0) AND (priority <= 2)))
);
 !   DROP TABLE public.opportunities;
       public         heap    postgres    false            �            1259    16836    opportunity_contacts    TABLE     �   CREATE TABLE public.opportunity_contacts (
    opportunity_id character varying(255) NOT NULL,
    contact_id character varying(255) NOT NULL
);
 (   DROP TABLE public.opportunity_contacts;
       public         heap    postgres    false            �            1259    16708    roles    TABLE     �   CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(255),
    CONSTRAINT roles_name_check CHECK (((name)::text = ANY ((ARRAY['ROLE_USER'::character varying, 'ROLE_ADMIN'::character varying])::text[])))
);
    DROP TABLE public.roles;
       public         heap    postgres    false            �            1259    16707    roles_id_seq    SEQUENCE     �   CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.roles_id_seq;
       public          postgres    false    216                       0    0    roles_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
          public          postgres    false    215            �            1259    16817    stages    TABLE     l  CREATE TABLE public.stages (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp(6) without time zone,
    last_modified_by character varying(255),
    last_modified_date timestamp(6) without time zone,
    code character varying(255),
    name character varying(255),
    revenue double precision NOT NULL
);
    DROP TABLE public.stages;
       public         heap    postgres    false            �            1259    16911    template_files    TABLE     �  CREATE TABLE public.template_files (
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
 "   DROP TABLE public.template_files;
       public         heap    postgres    false            �            1259    16715 
   user_roles    TABLE     n   CREATE TABLE public.user_roles (
    user_id character varying(255) NOT NULL,
    role_id integer NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false            �            1259    16720    users    TABLE     v  CREATE TABLE public.users (
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
    DROP TABLE public.users;
       public         heap    postgres    false            B           2604    16711    roles id    DEFAULT     d   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216                      0    16851 
   activities 
   TABLE DATA           �   COPY public.activities (id, created_by, created_date, last_modified_by, last_modified_date, detail, type, opportunity_id, date) FROM stdin;
    public          postgres    false    223   N                 0    16887    avatars 
   TABLE DATA           �   COPY public.avatars (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) FROM stdin;
    public          postgres    false    224   qO                  0    16829    contacts 
   TABLE DATA           �   COPY public.contacts (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, phone, job_position) FROM stdin;
    public          postgres    false    221   �P                 0    16932    files 
   TABLE DATA           ~   COPY public.files (id, created_by, created_date, last_modified_by, last_modified_date, name, physical_path, type) FROM stdin;
    public          postgres    false    226   �Q       �          0    16800    opportunities 
   TABLE DATA             COPY public.opportunities (id, created_by, created_date, last_modified_by, last_modified_date, address, email, is_customer, name, phone, revenue, website, salesperson_id, stage_id, description, priority, company, lost_note, lost_reason, probability, template_file_id) FROM stdin;
    public          postgres    false    219   �Q                 0    16836    opportunity_contacts 
   TABLE DATA           J   COPY public.opportunity_contacts (opportunity_id, contact_id) FROM stdin;
    public          postgres    false    222   �U       �          0    16708    roles 
   TABLE DATA           )   COPY public.roles (id, name) FROM stdin;
    public          postgres    false    216   =V       �          0    16817    stages 
   TABLE DATA           y   COPY public.stages (id, created_by, created_date, last_modified_by, last_modified_date, code, name, revenue) FROM stdin;
    public          postgres    false    220   nV                 0    16911    template_files 
   TABLE DATA           �   COPY public.template_files (id, created_by, created_date, last_modified_by, last_modified_date, is_file, name, physical_path, type, opportunity_id) FROM stdin;
    public          postgres    false    225   X       �          0    16715 
   user_roles 
   TABLE DATA           6   COPY public.user_roles (user_id, role_id) FROM stdin;
    public          postgres    false    217   �X       �          0    16720    users 
   TABLE DATA           �   COPY public.users (id, created_by, created_date, last_modified_by, last_modified_date, birthday, email, firstname, fullname, gender, lastname, password, phone, username, avatar_id) FROM stdin;
    public          postgres    false    218   hY                  0    0    roles_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.roles_id_seq', 2, true);
          public          postgres    false    215            X           2606    16857    activities activities_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.activities DROP CONSTRAINT activities_pkey;
       public            postgres    false    223            Z           2606    16893    avatars avatars_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.avatars
    ADD CONSTRAINT avatars_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.avatars DROP CONSTRAINT avatars_pkey;
       public            postgres    false    224            V           2606    16835    contacts contacts_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.contacts DROP CONSTRAINT contacts_pkey;
       public            postgres    false    221            `           2606    16938    files files_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.files DROP CONSTRAINT files_pkey;
       public            postgres    false    226            P           2606    16806     opportunities opportunities_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT opportunities_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.opportunities DROP CONSTRAINT opportunities_pkey;
       public            postgres    false    219            F           2606    16714    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public            postgres    false    216            T           2606    16823    stages stages_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.stages
    ADD CONSTRAINT stages_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.stages DROP CONSTRAINT stages_pkey;
       public            postgres    false    220            \           2606    16917 "   template_files template_files_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.template_files
    ADD CONSTRAINT template_files_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.template_files DROP CONSTRAINT template_files_pkey;
       public            postgres    false    225            J           2606    16730 !   users uk6dotkott2kjsp8vw4d0m25fb7 
   CONSTRAINT     ]   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7;
       public            postgres    false    218            R           2606    16926 *   opportunities uk_4yrhfoclq47js1mo500hbvuaf 
   CONSTRAINT     q   ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT uk_4yrhfoclq47js1mo500hbvuaf UNIQUE (template_file_id);
 T   ALTER TABLE ONLY public.opportunities DROP CONSTRAINT uk_4yrhfoclq47js1mo500hbvuaf;
       public            postgres    false    219            ^           2606    16919 +   template_files uk_r9ehkfiu772eoujq159hqmo4a 
   CONSTRAINT     p   ALTER TABLE ONLY public.template_files
    ADD CONSTRAINT uk_r9ehkfiu772eoujq159hqmo4a UNIQUE (opportunity_id);
 U   ALTER TABLE ONLY public.template_files DROP CONSTRAINT uk_r9ehkfiu772eoujq159hqmo4a;
       public            postgres    false    225            L           2606    16728 !   users ukr43af9ap4edm43mmtq01oddj6 
   CONSTRAINT     `   ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT ukr43af9ap4edm43mmtq01oddj6;
       public            postgres    false    218            H           2606    16719    user_roles user_roles_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public            postgres    false    217    217            N           2606    16726    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    218            g           2606    16846 0   opportunity_contacts fk2gybgffh0nbv90xa7wfboxw20    FK CONSTRAINT     �   ALTER TABLE ONLY public.opportunity_contacts
    ADD CONSTRAINT fk2gybgffh0nbv90xa7wfboxw20 FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);
 Z   ALTER TABLE ONLY public.opportunity_contacts DROP CONSTRAINT fk2gybgffh0nbv90xa7wfboxw20;
       public          postgres    false    219    222    4688            h           2606    16841 0   opportunity_contacts fk6nnk49tkgwlldvk0xbrj0v8hc    FK CONSTRAINT     �   ALTER TABLE ONLY public.opportunity_contacts
    ADD CONSTRAINT fk6nnk49tkgwlldvk0xbrj0v8hc FOREIGN KEY (contact_id) REFERENCES public.contacts(id);
 Z   ALTER TABLE ONLY public.opportunity_contacts DROP CONSTRAINT fk6nnk49tkgwlldvk0xbrj0v8hc;
       public          postgres    false    4694    221    222            c           2606    16894 !   users fk7tamg8hd3ubuy97jk242s0kwf    FK CONSTRAINT     �   ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk7tamg8hd3ubuy97jk242s0kwf FOREIGN KEY (avatar_id) REFERENCES public.avatars(id);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT fk7tamg8hd3ubuy97jk242s0kwf;
       public          postgres    false    4698    218    224            j           2606    16920 *   template_files fk935fl0jj0vwc1bpnirmp5gpdb    FK CONSTRAINT     �   ALTER TABLE ONLY public.template_files
    ADD CONSTRAINT fk935fl0jj0vwc1bpnirmp5gpdb FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);
 T   ALTER TABLE ONLY public.template_files DROP CONSTRAINT fk935fl0jj0vwc1bpnirmp5gpdb;
       public          postgres    false    225    219    4688            a           2606    16731 &   user_roles fkh8ciramu9cc9q3qcqiv4ue8a6    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6;
       public          postgres    false    216    4678    217            b           2606    16736 &   user_roles fkhfh9dx7w3ubf1co1vdev94g3f    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f;
       public          postgres    false    217    218    4686            d           2606    16927 )   opportunities fkk26twc07owemy54nr2744g4wf    FK CONSTRAINT     �   ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT fkk26twc07owemy54nr2744g4wf FOREIGN KEY (template_file_id) REFERENCES public.template_files(id);
 S   ALTER TABLE ONLY public.opportunities DROP CONSTRAINT fkk26twc07owemy54nr2744g4wf;
       public          postgres    false    219    4700    225            e           2606    16824 )   opportunities fkn2gf0yjk6elbl41j89m4k0iwh    FK CONSTRAINT     �   ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT fkn2gf0yjk6elbl41j89m4k0iwh FOREIGN KEY (stage_id) REFERENCES public.stages(id);
 S   ALTER TABLE ONLY public.opportunities DROP CONSTRAINT fkn2gf0yjk6elbl41j89m4k0iwh;
       public          postgres    false    219    4692    220            f           2606    16807 )   opportunities fksmyq1mklx0rhrp6v2199tsvbh    FK CONSTRAINT     �   ALTER TABLE ONLY public.opportunities
    ADD CONSTRAINT fksmyq1mklx0rhrp6v2199tsvbh FOREIGN KEY (salesperson_id) REFERENCES public.users(id);
 S   ALTER TABLE ONLY public.opportunities DROP CONSTRAINT fksmyq1mklx0rhrp6v2199tsvbh;
       public          postgres    false    219    4686    218            i           2606    16858 &   activities fktehic8fiattwna4dgrmw35w60    FK CONSTRAINT     �   ALTER TABLE ONLY public.activities
    ADD CONSTRAINT fktehic8fiattwna4dgrmw35w60 FOREIGN KEY (opportunity_id) REFERENCES public.opportunities(id);
 P   ALTER TABLE ONLY public.activities DROP CONSTRAINT fktehic8fiattwna4dgrmw35w60;
       public          postgres    false    219    223    4688               J  x����N%1�뙧�x�x��L�=5M~�`�Ew$�~�jY�V\�"E�|��';K�Zl�M�WH�
�f9`≊t�N�HG���J�b���F�!1�
d��F����?��S:����q��n�V}��#P�
��!If,�
��[u��C�JV��*�*^��
�W��m֫��o됇%`����Ǿ��o87��t����5�mCw6�����@*"����z�|e���p���Z��ķ������C:�7�/��y\p�;��ۈ?40�Q�dM��<��C��������_�{��蝏�����?-$�|����>��f           x���Mn1�יS�L�m�,�FxlGI�I��I����MVH����1EQ��,�Cq鐛zQK�bݰ�XK�)��dq�E�%	�ad\���h�XF�p���������8��/)
!�(_"��ݫo���ж��%@�� �d�F���U��lxўRgp�
���l͖V�x�d��&��H�3��q�w�+2)g������Z_X�:��ńq�j����)��8
�}�w>}�&���Ϸb@Đd�!�&\��i�����n          -  x��һnC!��������ԡC�VꞅkT)�)�_�,]2�DBH����}Śc�8��V ���RE|/�q�*����Yen"��!��c���ׅ���abM��L��iM,F=�f�}B�ci����o�S�9�z9-�`����a�}����3��xA뜵��[�_+FFi����gc=@�v.]�-S����Dl����;}m1�1ݽN�.R*͎�3����}v~���n�O�X�eëO�$�}L���\ɵ&|���F�(BU��bi�eӇ���A�$�[�ߵ=����{���/ƀ�]            x������ � �      �   �  x��UKnG]�N�`�S��*6XdA�������g~,�Ydc#��E`�F�|6�"�Qr�$5�8���C�dwuU�W������+*d�(8	�*iȬ\!&�Y�g���F!���UX��H�&�2I'\w9t�H����0���(HΨ��Qk]t!fQ�	�1�t��
�:���yK���׃�lvI���_V�yW����r�mU&���/ǟ�~p��z�ɡ��ړ�ϟ?%LHi����E��jw�Ϫ��qb�����.p,U���2j��>R�H������ϖ��#r��n�*r���Ƅ͏�]�>&(o���8� k��L،Zx'Cp �� 3��H�V�� 6]	)w�p���x9��"'e3��}v�}�G.�8��l�l��<���@ưU�GU�Q|y')"�V�x\�F;�3�C� ���S`��^3Ks��G�s��>�K��2��_�.��JH4b�\���Y�0� g�`���<����ܟ]C]1��h�@z�2��qr����O���G��*��W��U����d��uUֶ�z���s���QQ�O�@�
��8m�,�{R���J��KRv�L�	)�ͫ�mO��~�@���!���ޠ����ӕ>��7W�x9���'���!on�'3�E�q�֯Q���y������Ŀj�V伽L���}��i�3`�-<'5!bF�q�E�i��f����C�I�.�T���LjO�i�]�o��S��5�.[!U���ӿl��d�5?�	�K�;�6@���ĭw�[،7��f���
���>�~�V[*�e<Qh�?�����W���7�Í4.H�g�
	9�Y�	h`�*h�«���JD+{Y��xt�^摯o�I2-}U����Ztk�h���}�����6�������4�ګw�}���ޚ         �   x����1��V�@���鿄��h�z�+�IkjI��.apXp	Gߊ��ȍ�������	�Rg�Ik��50�(��W^8}֬��u��D19Sh� �ԧ�b�@���坄F(�ϝ��l�n��>����}�nO�      �   !   x�3���q�wt����2�pB�]��b���� s��      �   �  x���Aj�1FמS�4Ȓl����B&mR��ƶ������Z���ɟFu#�;7�ݪUü�v-��Y���*��:�$^�-&��
�DH�@��� 9�.d�1��V�&@{T�6ZS�ަ��Yf���������{ZY����\@�g�n�������/F�sƣ�!zɵ%�e�������� b�6�X��|������BZ� ����Ϫ�Ԁ��^���N/O����g͵�t� fl[_�fL�̯�Ĵs���P����6�؁�%�`�e0&RC,�e���K|Je�e�r���a�s�K<�i�/�D ���|�f}���&�У; bƘ�'U�>�bz�u{��]߯�\GF�e�(��c@�2/��_�(.|!��`�ȽY��� ��0�j��2OϷ��P���N�?��y         �   x���MN�0��ur
.0�?3��Cp�J��̠H��&)���Jlز�����H��A�B�A\��U���1E�4 ���	�RL�2r�G��^]��P2!������ۙϹUí<�Т#G�R�~x��-sy���Mۤ>�E۾��:���U�i����ފ�\?�������_M����}S<      �   v   x���  �s셌�\@���}�-��&�	\Z�����7�)?k�{�7���z^�v��d᱃>8pK�5B�
0�	��7����/k�)y'�|�*�:Oݺ1O���;��zg'c      �   �  x���Io�P���Sp��Yo_8%�J���aS/o3&�&�O�gBR�R��5iF��?��*hQ*S`������J
�B�%�U$�p�����k].���H �MD[T��	�V�;����7@R�K���+A�>S�`U�X�Xp�� �M(Z�V]�
w>��|�e�W�w��6{_4/7��V/|T�ϰ>C��f*��!�n�n"���B���$L�������i��g��|�aڛ�N	U�	�����0���֩f]��i
�g�0%�஑J���p �0��%���3�C�����
0B �_�˲�6C̸�Wgc�6��&p>���e�\�����:�6/ϓn�
�~|�-�(�?�z6������N'E��Nv�8����c\���HLiTTÊ�k��D�O�EA�*V��=T��Ŕ��jW\���)˚j�*�n&���):1��OC�,�vo�����0\���n��o�d��e���<%�H��	}Kx��it��NÃ�ŕ��@1À�VQ-sJ7�e�)s(A(��Rye��X�ώuz�Eq���}��'=T3,��H/�Q\�d���n��o�.�Ap���ȹ���6��f����C8^���;��>^����]ݷ{7���W�ܜG���'L�R����hG��<5�q���	V`g�     