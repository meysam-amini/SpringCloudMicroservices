insert into public.profile (id, created_date, enabled, updated_date, version, address, fathername, firstname, gender,
                            isactive, lastname, password, phonenumber, username)
values (nextval('profile_seq'),now(),true,now(),1,'Brussels','Mohammad','Meysam',0,true,'amini','$2a$10$xVDL52o1WStgXzyN1FfTSOT70zj8.ZtsbSmnMmAHu1opEKnIQ1P52','09218364180','meysam');


insert into public.role (id, created_date, enabled, updated_date, version, code, name)
values (nextval('role_seq'),now(),true,now(),1,'01','ADMIN');

insert into public.permission (id, created_date, enabled, updated_date, version, code, enkey, name)
values (nextval('permission_seq'),now(),true,now(),1,'01','ADD_USER','ADD_USER');

insert into public.permission (id, created_date, enabled, updated_date, version, code, enkey, name)
values (nextval('permission_seq'),now(),true,now(),1,'02','QUERY_USER','QUERY_USER');

insert into rolepermission (id, created_date, enabled, updated_date, version, permission, role)
values (nextval('role_permission_seq'),now(),true,now(),1,1,1);

insert into rolepermission (id, created_date, enabled, updated_date, version, permission, role)
values (nextval('role_permission_seq'),now(),true,now(),1,2,1);

insert into profilerole (id, createdat, createdby, isactive, modifiedat, modifiedby, profile, role)
values (nextval('profile_role_seq'),now(),1,true,now(),1,1,1);