CREATE TABLE t_category (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  category_name varchar(100) NULL COMMENT '分类名称',
  sort int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  usable int(11) NOT NULL DEFAULT '1' COMMENT '标识字段-是否可用',
  create_time bigint(20) NOT NULL COMMENT '创建时间',
  update_time bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (id)
);

CREATE TABLE t_coupon (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  money int(11) NOT NULL DEFAULT '0' COMMENT '面值',
  integrate int(11) NOT NULL DEFAULT '0' COMMENT '兑换积分',
  remain int(11) NOT NULL DEFAULT '0' COMMENT '剩余可兑换的数量',
  usable int(11) NOT NULL DEFAULT '1' COMMENT '标识字段-是否可用',
  type int(11) NOT NULL DEFAULT '1' COMMENT '卡券类型：1-手机充值，2-加油卡充值',
  PRIMARY KEY (id)
);


ALTER TABLE t_product ADD COLUMN category_id bigint(20) COMMENT'分类id' DEFAULT '0' AFTER id;