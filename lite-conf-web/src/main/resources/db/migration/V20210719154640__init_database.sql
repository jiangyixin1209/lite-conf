CREATE TABLE IF NOT EXISTS `lite_env`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `name`          varchar(50) NOT NULL COMMENT '环境名称',
    `code`          varchar(50) NOT NULL COMMENT '环境编码(唯一)',
    `desc`          varchar(255) NOT NULL COMMENT '描述',
    `create_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE IF NOT EXISTS `lite_system`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `name`          varchar(50) NOT NULL COMMENT '系统名称',
    `code`          varchar(50) NOT NULL COMMENT '系统编码(唯一)',
    `desc`          varchar(255) NOT NULL COMMENT '描述',
    `create_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `lite_conf`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `env`           varchar(50) NOT NULL COMMENT '环境编码',
    `system`        varchar(50) NOT NULL COMMENT '系统编码',
    `conf`          varchar(50) NOT NULL COMMENT '配置文件名',
    `key`           varchar(50) NOT NULL COMMENT '配置名',
    `value`         longtext NOT NULL COMMENT '配置值',
    `desc`          varchar(255) NOT NULL COMMENT '描述',
    `create_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_key` (`env`, `system`, `conf`, `key`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `lite_user`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `username`      varchar(50) NOT NULL COMMENT '用户名',
    `password`      varchar(50) NOT NULL COMMENT '用户密码',
    `authority`     longtext NOT NULL COMMENT '权限',
    `create_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE IF NOT EXISTS `lite_update_log`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `user_id`       int(11) NOT NULL,
    `conf_id`       int(11) NOT NULL,
    `old_value`     longtext COMMENT '旧配置值',
    `new_value`     longtext COMMENT '新配置值',
    `desc`          varchar(500) NOT NULL COMMENT '修改描述',
    `create_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    INDEX `uk_username` (`user_id`, `conf_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;