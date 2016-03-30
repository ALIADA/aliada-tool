# From Centos custom + aliada
# Made by Corrado Arba
# 25/11/2015
# Client version: 1.7.1
# Client API version: 1.19
# OS/Arch (client): linux/amd64
# Server version: 1.7.1
# Server API version: 1.19
# VERSION Docker_File 1.2

# Pull Container and set utf8 + Start aliada
FROM corrado/aliada
RUN echo "LC_CTYPE="en_US.UTF-8"" >> /etc/sysconfig/i18n
RUN cd /script && ./start_aliada.sh
EXPOSE 8080
EXPOSE 3306
EXPOSE 1111



# PREVIUOS VERSION
#  Download and install wget and tar command
#RUN yum install wget -y
#RUN RUN yum install tar -y

#  Download and install oracle-java JDK 1.8
# RUN cd /tmp
# RUN wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u45-b14/jdk-8u45-linux-x64.rpm"
# RUN rpm -Uvh jdk-8u45-linux-x64.rpm

# Install mysql
# RUN yum install mysql mysql-server php-mysql -y
# RUN /sbin/chkconfig --levels 235 mysqld on
# RUN cd /etc/init.d
# RUN service mysqld start
# RUN cd /usr/bin
# RUN ./

# Install Virtuoso
# download the dependencies
# RUN yum install -y gcc gmake autoconf automake libtool flex bison gperf gawk m4 make openssl-devel readline-devel
# RUN mkdir /home/virtuoso
# RUN git clone git://github.com/openlink/virtuoso-opensource.git
# RUN cd /home/virtuoso
# RUN ./autogen.sh
# RUN ./configure
# RUN make
# RUN make install
