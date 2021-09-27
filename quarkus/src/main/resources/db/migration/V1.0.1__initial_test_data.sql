INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('1', 'image1', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('2', 'image2', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('3', 'image3', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('4', 'image4', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('5', 'image5', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('6', 'image6', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('7', 'image7', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('8', 'image8', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('9', 'image9', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE VALUES ('10', 'image10', 'https://via.placeholder.com/200.png');

INSERT INTO ${flyway:defaultSchema}.PRODUCER VALUES ('1', '8');
INSERT INTO ${flyway:defaultSchema}.PRODUCER VALUES ('2', '9');
INSERT INTO ${flyway:defaultSchema}.PRODUCER VALUES ('3', '10');

INSERT INTO ${flyway:defaultSchema}.PRODUCER_DETAILS VALUES ('1', 'testProducer','pl-PL', 'Producer1_PL', '1');
INSERT INTO ${flyway:defaultSchema}.PRODUCER_DETAILS VALUES ('2', 'testProducer','pl-PL', 'Producer2_PL', '2');
INSERT INTO ${flyway:defaultSchema}.PRODUCER_DETAILS VALUES ('3', 'testProducer','pl-PL', 'Producer3_PL', '3');
INSERT INTO ${flyway:defaultSchema}.PRODUCER_DETAILS VALUES ('4', 'testProducer','en-US', 'Producer3_US', '3');

INSERT INTO ${flyway:defaultSchema}.CATEGORY VALUES ('1');
INSERT INTO ${flyway:defaultSchema}.CATEGORY VALUES ('2');
INSERT INTO ${flyway:defaultSchema}.CATEGORY VALUES ('3');
INSERT INTO ${flyway:defaultSchema}.CATEGORY VALUES ('4');

INSERt INTO ${flyway:defaultSchema}.CATEGORY_DETAILS VALUES ('1', 'Shipping', 'pl-PL', 'shipping', '1');
INSERt INTO ${flyway:defaultSchema}.CATEGORY_DETAILS VALUES ('2', 'CATEGORY_DETAILS', 'pl-PL', 'category2_PL', '2');
INSERt INTO ${flyway:defaultSchema}.CATEGORY_DETAILS VALUES ('3', 'CATEGORY_DETAILS', 'pl-PL', 'category3_PL', '3');
INSERt INTO ${flyway:defaultSchema}.CATEGORY_DETAILS VALUES ('4', 'CATEGORY_DETAILS', 'pl-PL', 'category4_PL', '4');
INSERt INTO ${flyway:defaultSchema}.CATEGORY_DETAILS VALUES ('5', 'CATEGORY_DETAILS', 'en-US', 'category4_US', '4');

INSERT INTO ${flyway:defaultSchema}.ITEM VALUES ('1', '1', '0.0', '0.23', '1', '1');

INSERT INTO ${flyway:defaultSchema}.ITEM VALUES ('2', '333', '11.56', '0.23', '2', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM VALUES ('3', '0', '12.33', '0.23', '3', '3');
INSERT INTO ${flyway:defaultSchema}.ITEM VALUES ('4', '221', '1.45', '0.23', '4', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM VALUES ('5', '2', '0.99', '0.23', '5', '3');
INSERT INTO ${flyway:defaultSchema}.ITEM VALUES ('6', '2', '2.45', '0.23', '6', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM VALUES ('7', '2', '1.45', '0.23', '7', '3');

INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('1', 'shippingMethod', 'pl-PL', 'shipping', '1');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('2', 'itemDescription', 'pl-PL', 'Bardzo fajny item', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('3', 'itemDescription', 'pl-PL', 'Troche inny item', '3');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('4', 'itemDescription', 'pl-PL', 'Testowy item', '4');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('5', 'itemDescription', 'pl-PL', 'Analogiczny item', '5');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('6', 'itemDescription', 'pl-PL', 'Zabawny item', '6');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('7', 'PL_itemDescription', 'pl-PL', 'Polski Item', '7');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('8', 'ENG_itemDescription', 'en-US', 'American item', '7');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS VALUES('9', 'FR_itemDescription', 'fr-FR', 'Sujet francais', '7');


INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('1', '1');
--INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('1', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('2', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('3', '4');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('4', '3');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('5', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('6', '4');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('7', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY VALUES ('7', '3');


INSERT INTO ${flyway:defaultSchema}.USERS VALUES ('1','testmail@test.com','TestUser','TestName');


INSERT INTO ${flyway:defaultSchema}.ADDRESS VALUES ('1', 'TestCity', '666', 'TestStreet', '00-000', '1');

