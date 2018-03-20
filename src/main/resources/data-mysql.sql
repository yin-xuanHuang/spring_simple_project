INSERT IGNORE INTO authority VALUES('ROLE_USER');
INSERT IGNORE INTO authority VALUES('ROLE_ADMIN');

INSERT IGNORE INTO users (username, password, enabled, email) VALUES ('adminx', '$2a$10$E3mPTZb50e7sSW15fDx8Ne7hDZpfDjrmMPTTUp8wVjLTu.G5oPYCO', True, 'adminx@ya4do.io');
INSERT IGNORE INTO users (username, password, enabled, email) VALUES ('marten', '$2a$10$5VWqjwoMYnFRTTmbWCRZT.iY3WW8ny27kQuUL9yPK1/WJcPcBLFWO',True, 'marten@ya4do.io');
INSERT IGNORE INTO users (username, password, enabled, email) VALUES ('jdoe', '$2a$10$cFKh0.XCUOA9L.in5smIiO2QIOT8.6ufQSwIIC.AVz26WctxhSWC6', false, 'jdoe@ya4do.net');

INSERT IGNORE INTO user_authority (username,authority_name) VALUES('adminx', 'ROLE_USER');
INSERT IGNORE INTO user_authority (username,authority_name) VALUES('adminx', 'ROLE_ADMIN');
INSERT IGNORE INTO user_authority (username,authority_name) VALUES('marten', 'ROLE_USER');
INSERT IGNORE INTO user_authority (username,authority_name) VALUES('jdoe', 'ROLE_USER');
