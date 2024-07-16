create table avd_vul_info
(
    id          int auto_increment
        primary key,
    vul_name    varchar(255) null,
    vul_type    varchar(100) null,
    vul_date    varchar(100) null,
    cve_number  varchar(50)  null,
    exp_status  varchar(50)  null,
    description text         null,
    suggest     text         null,
    reference   text         null,
    level       varchar(50)  null
);

create table cisa_vul_info
(
    cve_number         varchar(50)   null,
    vendor             varchar(255)  null comment '供应商',
    product            varchar(255)  null,
    vulnerability_name varchar(500)  null,
    date_added         varchar(50)   null comment '漏洞公开日期',
    description        text          null,
    suggest            text          null comment '缓解措施',
    reference          text null comment '引用'
);

create table ms_vul_info
(
    cve_number   varchar(50)  not null
        primary key,
    release_date varchar(100) not null,
    mitre_url    varchar(500) not null,
    tag          varchar(500) not null
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create table oscs_vul_info
(
    public_time varchar(50)  null comment '公开时间',
    title       varchar(500) null comment '漏洞名称',
    level       varchar(50)  null comment '风险等级',
    description text         null comment '漏洞描述',
    suggest     text         null comment '修复方案',
    reference   text         null comment '参考链接',
    vuln_type   varchar(255) null comment '漏洞类型',
    cve_id      varchar(50)  null,
    cnvd_id     varchar(50)  null
);

create table repository
(
    id          int auto_increment
        primary key,
    repo_name   varchar(1000) default '0' not null,
    pushed_at   varchar(200)  default ''  not null,
    description text             null
)
    engine = InnoDB
    charset = utf8mb4;

create table ti_vul_info
(
    vul_name     varchar(500) not null comment '漏洞名称',
    description  text         null comment '描述',
    rating_level varchar(255) null comment '漏洞级别',
    publish_time varchar(255) null comment '发布时间',
    tags         varchar(255) null comment '漏洞标签',
    vul_type     varchar(255) null comment '漏洞类型 threat_category',
    cve_id       varchar(50)  null,
    cnvd_id      varchar(50)  null
)
    row_format = DYNAMIC;

