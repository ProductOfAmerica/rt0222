INSERT INTO TOOL_TYPE(ID, TOOL_NAME, DAILY_CHARGE, WEEKLY_CHARGE, WEEKEND_CHARGE, HOLIDAY_CHARGE)
VALUES (1, 'Ladder', 1.99, true, true, false),
       (2, 'Chainsaw', 1.49, true, false, true),
       (3, 'Jackhammer', 2.99, true, false, false);

INSERT INTO TOOL(ID, TOOL_CODE, TOOL_TYPE_ID, TOOL_BRAND)
VALUES (1, 'CHNS', 2, 'Stihl'),
       (2, 'LADW', 1, 'Werner'),
       (3, 'JAKD', 3, 'DeWalkt'),
       (4, 'JAKR', 3, 'Ridgid');

INSERT INTO CLERK(ID)
values (1);

INSERT INTO CUSTOMER(ID)
values (1);