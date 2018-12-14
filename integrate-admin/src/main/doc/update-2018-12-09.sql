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


CREATE TABLE t_coupon_exchange (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id bigint(20) NOT NULL,
  integrate int(11) NOT NULL COMMENT '金额',
  type int(11) NOT NULL DEFAULT '1' COMMENT '卡券类型：1-手机充值，2-加油卡充值',
  account varchar(50) NOT NULL COMMENT '根据卡券类型：1->手机号，2->加油卡号',
  coupon_id bigint(20) NOT NULL COMMENT '兑换的商品id',
  amount int(4) DEFAULT '0' COMMENT '兑换数量',
  status int(11) DEFAULT '1' COMMENT '兑换状态：0-未知，1-处理中，2-已兑换',
  create_time bigint(20) NOT NULL COMMENT '创建时间',
  update_time bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (id)
)

ALTER TABLE t_product ADD COLUMN category_id bigint(20) COMMENT'分类id' DEFAULT '0' AFTER id;
ALTER TABLE t_app_version ADD COLUMN description varchar(500) COMMENT'升级描述' DEFAULT NULL;
ALTER TABLE t_transaction_record MODIFY type int(5) comment '0:充值，1：提现，2:兑换，3：返还积分，4：卡券兑换'

