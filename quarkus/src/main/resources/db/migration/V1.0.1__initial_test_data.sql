INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('1', '{
	"id": "f08e43f6-a4d6-435d-8334-3e423f072b20",
	"name": "root",
	"description": "catalog root",
	"children": []
}');

-- Image --

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

-- Producer --

INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('1', 'testProducer', 'Producer1_PL', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('2', 'testProducer', 'Producer2_PL', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('3', 'testProducer', 'Producer3_PL', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('4', 'testProducer', 'Producer3_US', 'https://via.placeholder.com/200.png');


-- Item-Details --

INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('1', 'Some shipping method');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('2', 'MacbookPro 16, Ryzen, 500GB SSD');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('3', 'Dell XPS 13, i7 16GB RAM 500GB SSD');

-- Item-Details-Image --

INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS_IMAGE
VALUES ('2', '4');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS_IMAGE
VALUES ('2', '5');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS_IMAGE
VALUES ('3', '6');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS_IMAGE
VALUES ('3', '7');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS_IMAGE
VALUES ('3', '8');

-- Items --

INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('1', 'shippingMethod', '9999', '10', '0.23', '1', '1', '1', true); -- Shipping
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('2', 'MacbookPro', '5', '33000', '0.23', '2', '2', '2', false);
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('3', 'Dell XPS 13', '5', '12000', '0.23', '3', '3', '3', false);


-- Item-Category --

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('2', 'f08e43f6-a4d6-435d-8334-3e423f072b20');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('3', 'f08e43f6-a4d6-435d-8334-3e423f072b20');

--
-- INSERT INTO ${flyway:defaultSchema}.ITEM
-- VALUES ('2', '45', '3464.23', '0.23', '2', '2');
-- INSERT INTO ${flyway:defaultSchema}.ITEM
-- VALUES ('3', '50', '7307.30', '0.23', '3', '3');
-- INSERT INTO ${flyway:defaultSchema}.ITEM
-- VALUES ('4', '57', '1999.00', '0.23', '2', '2');
-- INSERT INTO ${flyway:defaultSchema}.ITEM
-- VALUES ('5', '17', '2399.00', '0.23', '3', '3');
-- INSERT INTO ${flyway:defaultSchema}.ITEM
-- VALUES ('6', '25', '11030.00', '0.23', '3', '2');
-- INSERT INTO ${flyway:defaultSchema}.ITEM
-- VALUES ('7', '21', '6775.23', '0.23', '2', '3');
--
-- INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
-- VALUES ('1', 'shippingMethod', 'pl-PL', 'shipping', '1');
-- -- Shipping
--
-- -- Desktop computers --
-- INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
-- VALUES ('2', 'Core i5, RTX 2026, 16GB RAM, 500GB SSD', 'pl-PL', 'Infinity X500', '2');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
-- VALUES ('3', 'Core i7, RTX 3070, 32GB RAM, 1TB SSD', 'pl-PL', 'Ultimate X711', '3');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
-- VALUES ('4', 'Core i5, UHD730, 8GB RAM, 500GB SSD', 'pl-PL', 'Sensilo X511', '4');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
-- VALUES ('5', 'Core i7, GT 730, 8GB RAM, 500GB SSD', 'pl-PL', 'Vostro 3888 MT', '5');
--
-- -- Laptops --
-- INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
-- VALUES ('6', 'MacbookPro 15 cali, Ryzen, 500GB SSD', 'pl-PL', 'MacbookPro', '6');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
-- VALUES ('7', 'Dell XPS 13, i7 16GB RAM 500GB SSD', 'pl-PL', 'Dell XPS 13', '7');
--
--
-- -- Item-Category --
--
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('1', '0');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('1', '1');
--
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('2', '0');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('2', '4');
--
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('3', '0');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('3', '5');
--
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('4', '0');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('4', '2');
--
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('5', '0');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('5', '2');
--
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('6', '0');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('6', '3');
--
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('7', '0');
-- INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
-- VALUES ('7', '3');
--
-- INSERT INTO ${flyway:defaultSchema}.USERS
-- VALUES ('d463fb63-9158-49d7-b0fd-fd9d24370e78', 'testmail@test.com', 'TestUser', 'TestName');
--
--
-- INSERT INTO ${flyway:defaultSchema}.ADDRESS
-- VALUES ('73a40dde-0738-4b21-8188-2019cea89b7e', 'TestCity', '666', 'TestStreet', '00-000');
--
