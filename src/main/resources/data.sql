INSERT INTO departments VALUES
('CASEd','College of Arts, Science and Eduction'),
('CBA','College of Business and Accountancy'),
('CCJEF','College of Criminal Justice Education and Forensics'),
('CEA','College of Engineering and Architecture'),
('CHTM','College of Hospitality and Tourism Management'),
('CICT','College of Information and Communications Technology'),
('CNAMS','College of Nursing and Allied Medical Sciences'),
('HS','High School'),
('none','No Department');

INSERT INTO involvement_types VALUES
('Coordinator','Lorem ipsum dolor sit amet, consectetur adipiscing elit.'),
('Donor','Lorem ipsum dolor sit amet, consectetur adipiscing elit.'),
('Facilitator','Lorem ipsum dolor sit amet, consectetur adipiscing elit.'),
('Game Facilitator','Lorem ipsum dolor sit amet, consectetur adipiscing elit.'),
('Lecturer','Lorem ipsum dolor sit amet, consectetur adipiscing elit.'),
('Organizer','Lorem ipsum dolor sit amet, consectetur adipiscing elit.'),
('Participant','Lorem ipsum dolor sit amet, consectetur adipiscing elit.'),
('Resource Person','Lorem ipsum dolor sit amet, consectetur adipiscing elit.');

INSERT INTO members VALUES
(default,'SuperAdmin','SuperAdmin','CBA'),
(default,'John','Doe','CEA'),
(default,'Jane','Doe','HS'),
(default,'Juan','Dela Cruz','CHTM'),
(default,'Juanita','Dela Cruz','CEA'),
(default,'Jean','Dupont','CCJEF'),
(default,'Jeanne','Dupont','CICT'),
(default,'Jan','Jansen','CNAMS'),
(default,'Fulano','De Tal ','CICT');

INSERT INTO users VALUES
('admin','admin',1,'2015-10-02 00:00:00',1),
('officer','officer', 1,'2015-10-02 00:00:00',2),
('member','member',1,'2015-10-02 00:00:00',3);

INSERT INTO user_roles VALUES
(default, 'admin', 'ROLE_ADMIN'),
(default, 'admin', 'ROLE_OFFICER'),
(default, 'admin', 'ROLE_USER'),
(default, 'officer', 'ROLE_OFFICER'),
(default, 'officer', 'ROLE_USER'),
(default, 'member', 'ROLE_USER');

INSERT INTO activities VALUES
(default, 'Activity 1', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 1'),
(default, 'Activity 2', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 2'),
(default, 'Activity 3', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 3'),
(default, 'Activity 4', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 4'),
(default, 'Activity 5', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 5'),
(default, 'Activity 6', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 6'),
(default, 'Activity 7', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 7'),
(default, 'Activity 8', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 8'),
(default, 'Activity 9', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 9'),
(default, 'Activity 10', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 10'),
(default, 'Activity 11', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 11'),
(default, 'Activity 12', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 12'),
(default, 'Activity 13', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 13'),
(default, 'Activity 14', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 14'),
(default, 'Activity 15', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 15'),
(default, 'Activity 16', CURRENT_DATE(), CURRENT_DATE(), 'The members participation to activity 16');


INSERT INTO participations VALUES
(default,1,1,CURRENT_DATE(),'Great'),
(default,1,2,CURRENT_DATE(),'Great'),
(default,1,3,CURRENT_DATE(),'Great'),
(default,1,4,CURRENT_DATE(),'Great'),
(default,1,5,CURRENT_DATE(),'Great');

INSERT INTO participation_involvements VALUES
(1, 'Participant'),
(1, 'Facilitator'),
(2, 'Lecturer'),
(2, 'Organizer'),
(3, 'Coordinator'),
(4, 'Participant'),
(5, 'Participant');