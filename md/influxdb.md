
创建数据库
create database testdb

show databases

创建用户
CREATE USER admin WITH PASSWORD 'admin' WITH ALL PRIVILEGES

创建普通用户并授权
CREATE USER test WITH PASSWORD 'test'
GRANT READ ON testdb to test
GRANT WRITE ON testdb to test

创建retention 策略
CREATE RETENTION POLICY "rpBilling" ON "billing" DURATION 7d REPLICATION 1 SHARD DURATION 1h 