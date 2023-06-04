INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('1', 'image1', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('2', 'image2', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('3', 'image3', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('4', 'image4', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('5', 'image5', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('6', 'image6', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('7', 'image7', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('8', 'image8', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('9', 'image9', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.IMAGE
VALUES ('10', 'image10', 'https://via.placeholder.com/200.png');

INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('1', '8');
INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('2', '9');
INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('3', '10');

INSERT INTO ${flyway:defaultSchema}.PRODUCER_DETAILS
VALUES ('1', 'testProducer', 'pl-PL', 'Producer1_PL', '1');
INSERT INTO ${flyway:defaultSchema}.PRODUCER_DETAILS
VALUES ('2', 'testProducer', 'pl-PL', 'Producer2_PL', '2');
INSERT INTO ${flyway:defaultSchema}.PRODUCER_DETAILS
VALUES ('3', 'testProducer', 'pl-PL', 'Producer3_PL', '3');
INSERT INTO ${flyway:defaultSchema}.PRODUCER_DETAILS
VALUES ('4', 'testProducer', 'en-US', 'Producer3_US', '3');

-- Category --

INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('0', '0');
INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('1', '0');
INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('2', '0');
INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('3', '0');
INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('4', '2');
INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('5', '2');
INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('6', '3');
INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('7', '3');

-- Category details --

INSERT INTO ${flyway:defaultSchema}.CATEGORY_DETAILS
VALUES ('0', 'root', 'pl-PL', 'root', '0');
INSERT INTO ${flyway:defaultSchema}.CATEGORY_DETAILS
VALUES ('1', 'Shipping', 'pl-PL', 'shipping', '1');

INSERT INTO ${flyway:defaultSchema}.CATEGORY_DETAILS
VALUES ('2', 'Komputery Stancjonarne', 'pl-PL', 'Desktop PC', '2');
INSERT INTO ${flyway:defaultSchema}.CATEGORY_DETAILS
VALUES ('3', 'Laptopy', 'pl-PL', 'Laptopy', '3');

INSERT INTO ${flyway:defaultSchema}.CATEGORY_DETAILS
VALUES ('4', 'Komputery z procesorem Core i5', 'pl-PL', 'Core i5', '4');
INSERT INTO ${flyway:defaultSchema}.CATEGORY_DETAILS
VALUES ('5', 'Komputery z procesorem Core i7', 'pl-PL', 'Core i7', '5');

INSERT INTO ${flyway:defaultSchema}.CATEGORY_DETAILS
VALUES ('6', '15 cali', 'pl-PL', '15,6 cali', '6');
INSERT INTO ${flyway:defaultSchema}.CATEGORY_DETAILS
VALUES ('7', '13 cali', 'pl-PL', '17,3 cali', '7');

-- Items --

INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('1', '99999999', '0.0', '0.23', '1', '1', 'true'); -- Shipping

INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('2', '45', '3464.23', '0.23', '2', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('3', '50', '7307.30', '0.23', '3', '3');
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('4', '57', '1999.00', '0.23', '2', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('5', '17', '2399.00', '0.23', '3', '3');
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('6', '25', '11030.00', '0.23', '3', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('7', '21', '6775.23', '0.23', '2', '3');

INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('1', 'shippingMethod', 'pl-PL', 'shipping', '1');
-- Shipping

-- Desktop computers --
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('2', 'Core i5, RTX 2026, 16GB RAM, 500GB SSD', 'pl-PL', 'Infinity X500', '2');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('3', 'Core i7, RTX 3070, 32GB RAM, 1TB SSD', 'pl-PL', 'Ultimate X711', '3');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('4', 'Core i5, UHD730, 8GB RAM, 500GB SSD', 'pl-PL', 'Sensilo X511', '4');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('5', 'Core i7, GT 730, 8GB RAM, 500GB SSD', 'pl-PL', 'Vostro 3888 MT', '5');

-- Laptops --
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('6', 'MacbookPro 15 cali, Ryzen, 500GB SSD', 'pl-PL', 'MacbookPro', '6');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('7', 'Dell XPS 13, i7 16GB RAM 500GB SSD', 'pl-PL', 'Dell XPS 13', '7');


-- Item-Category --

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('1', '0');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('1', '1');

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('2', '0');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('2', '4');

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('3', '0');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('3', '5');

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('4', '0');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('4', '2');

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('5', '0');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('5', '2');

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('6', '0');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('6', '3');

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('7', '0');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('7', '3');

INSERT INTO ${flyway:defaultSchema}.USERS
VALUES ('1', 'testmail@test.com', 'TestUser', 'TestName');


INSERT INTO ${flyway:defaultSchema}.ADDRESS
VALUES ('1', 'TestCity', '666', 'TestStreet', '00-000');

