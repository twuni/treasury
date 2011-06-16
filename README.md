Quick Start
===========

1. Run `mvn clean package' from the base directory of this project.
2. Move the .war file in the /target folder to your Tomcat /webapps directory and rename it to treasury.war.
3. Create an empty text file in your Tomcat /lib directory named treasury.xml.
4. Copy the sample treasury.xml file (see below) into that new file, making any necessary modifications.
5. Launch Tomcat.

Sample treasury.xml file
===========================

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
		   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		   xsi:schemaLocation="http://www.springframework.org/schema/beans
		   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
	
		<bean class="org.twuni.money.treasury.service.TreasuryService.Configuration">
	
			<!-- This is the size (in characters) of identifiers and secrets generated by the treasury. -->
			<constructor-arg index="0" value="32"/>
	
			<!-- This is the domain on which this treasury will be hosted. -->
			<constructor-arg index="1" value="www.example.com"/>
	
			<!-- This is the implementation to use for the back-end token storage. -->
			<!-- hibernateTokenRepository: Uses a database for storage. -->
			<constructor-arg index="2" ref="hibernateTokenRepository"/>
	
		</bean>
	
		<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	
			<!-- Don't forget to place the package containing this driver into the /lib folder! -->
			<property name="driverClassName" value="org.hsqldb.jdbc.JDBCDriver"/>
	
			<property name="url" value="jdbc:hsqldb:file:data/treasury"/>
			<property name="username" value="SA"/>
			<property name="password" value=""/>
	
		</bean>
	
	</beans>

Next Steps
==========

1. Make sure that your treasury is accessible via the domain name you configured in treasury.xml.
2. You should host your treasury exclusively via HTTPS on port 443 using a professionally-signed certificate.
3. You should throttle requests to your treasury to help prevent brute force and DoS attacks.
4. You should configure a more reliable database connection than the default one.
5. You should run multiple instances of your treasury and feed all requests through a load balancer.
6. You should reject unauthorized calls to the Create API.
