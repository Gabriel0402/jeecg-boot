using centos 7


#install java
sudo yum update
yum install wget
wget http://download.oracle.com/otn-pub/java/jdk/8u171-b11/512cd62ec5174c3487ac17c61aaa89e8/jdk-8u171-linux-x64.tar.gz?AuthParam=1531155951_4e06a4d17c6c1dbfb8440352e19dd2ae

yum -y install java-1.8.0-openjdk

java -version

#install mysql
wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm

sudo rpm -ivh mysql-community-release-el7-5.noarch.rpm

yum update

sudo yum install mysql-server
sudo systemctl start mysqld

sudo mysql_secure_installation

mysql -u root -p

# 创建mysql库
create database `pipe-app` default character set utf8mb4 collate utf8mb4_general_ci;

USE pipe-app

source *.sql


#install redis

sudo yum install epel-release
sudo yum update

sudo yum install redis

sudo systemctl start redis

sudo systemctl enable redis

redis-cli ping

#install nginx
sudo yum install epel-release
sudo yum install nginx
sudo systemctl start nginx
sudo systemctl enable nginx