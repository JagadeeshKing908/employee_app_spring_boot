📘 Employee Management System (Spring Boot)
-------------------------------------------

A full-stack Employee Management System built using Spring Boot, Thymeleaf, MySQL, and Bootstrap.
This application supports role-based login (ADMIN / EMPLOYEE) and provides CRUD operations with a modern UI.

🚀 Features 
---------------

👨‍💼 Admin Features :-
-----------------

Add new employees
View all employees
Edit employee details
Delete employees
Search & filter employees (by name, department)
Manage roles and designations

👨‍💻 Employee Features
--------------------

Login dashboard
View personal profile
Update skills
View salary, manager, designation, department

🏗️ Tech Stack
--------------

Backend: Spring Boot (MVC)
Frontend: Thymeleaf, Bootstrap 5
Database: MySQL
ORM: Spring Data JPA
Authentication: Session-based login

📂 Project Structure
---------------------

src/main/java/com/example/demo
│
├── controller
│   ├── AuthController.java
│   └── EmployeeController.java
│
├── model
│   └── Employee.java
│
├── repository
│   └── EmployeeRepository.java
│
└── EmployeeApplication.java
src/main/resources/templates
│
├── login.html
├── index.html
├── form.html
└── employee-dashboard.html

🧑‍💻 Roles
----------

Role	Access
ADMIN	Full CRUD access
EMPLOYEE	View dashboard & update skills


Run Application :-
-----------------
Created docker conatiners with docker and deployed in docker swarm


📌 Key Endpoints

Endpoint	Description
/login	      Login page
/admin	      Admin dashboard
/add	        Add employee
/save	        Save employee
/edit/{id}	  Edit employee
/delete/{id}	Delete employee
/employee/dashboard	Employee dashboard

🧠 Future Improvements :--
------------------------

JWT authentication
File upload (profile images / documents)
Role-based access control (Spring Security)
Pagination for employee table
Audit logs
REST API version


👨‍💻 Author Details :
--------------------
Jagadeesh
DevOps Engineer
