-- Catalog --

INSERT INTO ${flyway:defaultSchema}.CATEGORY
VALUES ('1', '{
	"id": "f08e43f6-a4d6-435d-8334-3e423f072b20",
	"name": "root",
	"description": "catalog root",
	"children": [
		{
			"id": "f8ec2256-a9bc-4d61-9b10-5f3eb0a98fe6",
			"name": "computers",
			"description": "PC, desktop, laptops, notebooks",
			"children": [
				{
					"id": "829c9c63-7f3a-4ac0-ad99-65998894bb61",
					"name": "laptops",
					"description": "portable computers",
					"children": []
				},
				{
					"id": "f6eaf737-0a8c-454c-9b30-05492e1ece9d",
					"name": "PC",
					"description": "desktop machines",
					"children": []
				}
			]
		},
		{
			"id": "6522db9d-5347-4cee-9aca-67ca3c161e22",
			"name": "smartphones",
			"description": "Mobile smart phones",
			"children": []
		}
	]
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
VALUES ('2', 'Apple', 'Producer2_PL', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('3', 'Dell', 'Producer3_PL', 'https://via.placeholder.com/200.png');
INSERT INTO ${flyway:defaultSchema}.PRODUCER
VALUES ('4', 'Acer', 'Producer3_US', 'https://via.placeholder.com/200.png');


-- Item-Details --

INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('1', 'Some shipping method');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('2', 'MacbookPro 16, Ryzen, 500GB SSD');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('3', 'Dell XPS 13, i7 16GB RAM 500GB SSD');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('4', 'Acer Nitro 50, i5-12400/16GB/512 RTX3060');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('5', 'Apple iPhone 14 Pro 512GB Space Black');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('6', 'Apple iPhone 14 Pro 1TB Deep Purple');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS
VALUES ('7', 'Apple iPhone 14 Pro 256GB Deep Purple');

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
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS_IMAGE
VALUES ('4', '9');
INSERT INTO ${flyway:defaultSchema}.ITEM_DETAILS_IMAGE
VALUES ('4', '10');

-- Items --

INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('1', 'shippingMethod', '1', '1', '1', true); -- Shipping
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('2', 'MacbookPro', '2', '2', '2', false);
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('3', 'Dell XPS 13', '3', '3', '3', false);
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('4', 'Acer Nitro 50', '10', '4', '4', false);
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('5', 'IPhone 14 Pro Space Black 520GB', '10', '5', '2', false);
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('6', 'IPhone 14 Pro Deep Purple 1TB', '10', '6', '2', false);
INSERT INTO ${flyway:defaultSchema}.ITEM
VALUES ('7', 'IPhone 14 Pro Deep Purple 256GB', '10', '7', '2', false);


-- Item-Category --

INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('2', '829c9c63-7f3a-4ac0-ad99-65998894bb61');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('3', '829c9c63-7f3a-4ac0-ad99-65998894bb61');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('4', 'f6eaf737-0a8c-454c-9b30-05492e1ece9d');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('5', '6522db9d-5347-4cee-9aca-67ca3c161e22');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('6', '6522db9d-5347-4cee-9aca-67ca3c161e22');
INSERT INTO ${flyway:defaultSchema}.ITEM_CATEGORY
VALUES ('7', '6522db9d-5347-4cee-9aca-67ca3c161e22');

-- Item-Family --

INSERT INTO ${flyway:defaultSchema}.ITEM_FAMILY
VALUES ('1');

-- Item-Family-Item --

INSERT INTO ${flyway:defaultSchema}.ITEM_FAMILY_ITEM
VALUES ('5', '1', 'Space Black 520GB');
INSERT INTO ${flyway:defaultSchema}.ITEM_FAMILY_ITEM
VALUES ('6', '1', 'Deep Purple 1TB');
INSERT INTO ${flyway:defaultSchema}.ITEM_FAMILY_ITEM
VALUES ('7', '1', 'Deep Purple 256GB');

-- Warehouse-Item-Details --

INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM_DETAILS
VALUES ('1', 'shipping', '-', '0.0', '-');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM_DETAILS
VALUES ('2', 'Macbook Pro', 'Warehouse xyz', '2.0', '35x15x5');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM_DETAILS
VALUES ('3', 'Dell XPS 13', 'Warehouse xyz', '2.0', '35x15x5');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM_DETAILS
VALUES ('4', 'Acer Nitro 50', 'Warehouse xyz', '2.0', '35x15x5');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM_DETAILS
VALUES ('5', 'IPhone 14 Pro', 'Warehouse xyz', '2.0', '35x15x5');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM_DETAILS
VALUES ('6', 'IPhone 14 Pro', 'Warehouse xyz', '2.0', '35x15x5');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM_DETAILS
VALUES ('7', 'IPhone 14 Pro', 'Warehouse xyz', '2.0', '35x15x5');

-- Warehouse-Item --

INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM
VALUES ('1', '99999', '1');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM
VALUES ('2', '3', '2');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM
VALUES ('3', '13', '3');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM
VALUES ('4', '1', '4');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM
VALUES ('5', '8', '5');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM
VALUES ('6', '23', '6');
INSERT INTO ${flyway:defaultSchema}.WAREHOUSE_ITEM
VALUES ('7', '3', '7');