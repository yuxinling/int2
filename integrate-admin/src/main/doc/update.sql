CREATE TABLE `t_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NULL COMMENT '名称',
  `src` varchar(250) NULL COMMENT '图片地址',
  `rid` bigint(20) NOT NULL COMMENT '关联记录id',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
);


CREATE TABLE `t_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NULL COMMENT '名称',
  `date_time` varchar(20) COLLATE utf8mb4_bin DEFAULT '',
  `detail` varchar(250) NULL COMMENT '图片地址',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
);


CREATE TABLE `t_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NULL COMMENT '名称',
  `integrate` int(11) NOT NULL DEFAULT '0' COMMENT '兑换积分',
  `thumbnail` varchar(250) NULL COMMENT '缩略图地址',
  `detail` blob COMMENT '详细信息',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `t_hot_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(100) NULL COMMENT '关键词',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `update_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
);



ALTER TABLE `t_rechrage` ADD COLUMN `user_name` varchar(50) COMMENT'收货人姓名' DEFAULT '';
ALTER TABLE `t_rechrage` ADD COLUMN `user_mobile` varchar(20) COMMENT'收货人手机号' DEFAULT '';
ALTER TABLE `t_rechrage` ADD COLUMN `address` varchar(250) COMMENT'收货人地址' DEFAULT '';
ALTER TABLE `t_rechrage` ADD COLUMN `product_id` bigint(20) COMMENT'兑换的商品id' DEFAULT '-1';
ALTER TABLE `t_rechrage` ADD COLUMN `amount` int(4) COMMENT '兑换数量' DEFAULT '0';


INSERT INTO `integrate`.`sys_menu` (`MENU_ID`, `MENU_NAME`, `MENU_URL`, `PARENT_ID`, `MENU_ORDER`, `MENU_ICON`, `MENU_TYPE`) VALUES ('23', '应用设置', '#', '0', '8', 'icon-picture', '2');
INSERT INTO `integrate`.`sys_menu` (`MENU_ID`, `MENU_NAME`, `MENU_URL`, `PARENT_ID`, `MENU_ORDER`, `MENU_ICON`, `MENU_TYPE`) VALUES ('24', '图片新闻', 'bg/imageNews', '23', '1', NULL, '2');
INSERT INTO `integrate`.`sys_menu` (`MENU_ID`, `MENU_NAME`, `MENU_URL`, `PARENT_ID`, `MENU_ORDER`, `MENU_ICON`, `MENU_TYPE`) VALUES ('25', '动态新闻', 'bg/articleNews', '23', '2', NULL, '2');
INSERT INTO `integrate`.`sys_menu` (`MENU_ID`, `MENU_NAME`, `MENU_URL`, `PARENT_ID`, `MENU_ORDER`, `MENU_ICON`, `MENU_TYPE`) VALUES ('26', '商品信息', 'bg/productList', '23', '3', NULL, '2');
INSERT INTO `integrate`.`sys_menu` (`MENU_ID`, `MENU_NAME`, `MENU_URL`, `PARENT_ID`, `MENU_ORDER`, `MENU_ICON`, `MENU_TYPE`) VALUES ('27', '热门搜索', 'bg/hotKeyList', '23', '4', NULL, '2');

