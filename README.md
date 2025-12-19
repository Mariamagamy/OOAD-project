# 🎓 Course Registration System 

## 📌 Project Overview
A comprehensive **Course Registration System** developed as part of the Object-Oriented Analysis and Design (OOAD) course project. This system implements a complete university course registration workflow with role-based access control, state-based registration process, and full CRUD operations.

## 🏗️ System Architecture
- **Language**: Java 17+
- **Architecture**: Layered (Model-Service-UI)
- **Design Patterns**: State Pattern, Inheritance, Composition
- **Database**: In-memory (H2 Database ready)
- **Build Tool**: Maven

## 👥 Team Members & Contributions

### **Development Team**

| Team Member | Role | Major Contributions | Email |
|-------------|------|-------------------|-------|
| **Mariam Agamy**  | -SW | s-mariam.agamy@zewailcity.edu.eg |
| **Rana Ahmed** | SW | s-rana.maaty@zewailcity.edu.eg |
| **Arwa**  | SW | s-arwa.allam@zewailcity.edu.eg |

### **Project Timeline**
| Phase | Duration | Key Deliverables | Status |
|-------|----------|-----------------|--------|
| Phase 1: Requirements | 2 weeks | Use cases, Requirements doc | ✅ Completed |
| Phase 2: Analysis | 2 weeks | Class diagrams, UML models | ✅ Completed |
| Phase 3: Design | 2 weeks | Detailed design, Wireframes | ✅ Completed |
| **Phase 4: Implementation** | **3 weeks** | **Complete system, Testing** | **✅ COMPLETED** |

## 🚀 Features Implemented

### **Core Functionalities**
✅ **User Authentication & Role Management**
- Three user roles: Student, Instructor, Administrator
- Secure login/logout system
- Role-based access control

✅ **Student Features**
- Browse available courses
- Register for courses with validation
- View personal schedule
- Submit special requests
- View notifications

✅ **Instructor Features**
- View assigned courses
- Review student requests
- Approve/Reject special requests
- View course enrollments

✅ **Administrator Features**
- Full CRUD for courses and users
- Manage registration rules
- Open/Close registration periods
- System status monitoring

✅ **Registration Workflow** (State Machine)
- Prerequisites validation
- Capacity checking
- Schedule conflict detection
- Automatic notifications

## 📁 Project Structure
phase-4-last-version/
├── src/
│ ├── Main.java # Application entry point
│ ├── model/ # Business entities (12 classes)
│ │ ├── User.java # Abstract base class
│ │ ├── Student.java # Student entity
│ │ ├── Instructor.java # Instructor entity
│ │ ├── Administrator.java # Administrator entity
│ │ ├── Course.java # Course entity
│ │ ├── CourseOffering.java # Course offering
│ │ ├── Registration.java # Registration record
│ │ ├── Schedule.java # Time schedule
│ │ ├── Semester.java # Academic semester
│ │ ├── SpecialRequest.java # Special requests
│ │ ├── Notification.java # System notifications
│ │ └── RegistrationRules.java # Registration rules
│ ├── service/ # Business logic layer
│ │ ├── AuthService.java # Authentication service
│ │ ├── CourseService.java # Course management
│ │ └── RegistrationService.java # Registration logic
│ ├── ui/ # User interface
│ │ └── ConsoleUI.java # Console-based interface
│ └── util/ # Utilities
│ ├── DatabaseConnection.java # Database utilities
│ ├── InputValidator.java # Input validation
│ └── DateUtil.java # Date utilities
├── pom.xml # Maven configuration


## ⚙️ Installation & Setup

### **Prerequisites**
- Java JDK 17 or higher
- Git (for version control)
- Maven (optional, for dependency management)

### **Quick Start**
```bash
## 1. Clone the repository
git clone https://github.com/Mariamagamy/OOAD-project.git
cd OOAD-project

# 2. Switch to the implementation branch
git checkout last-version

# 3. Compile the project
javac -d out src/*.java src/model/*.java src/service/*.java src/ui/*.java src/util/*.java

# 4. Run the application
java -cp out Main


Using IntelliJ IDEA
Open IntelliJ IDEA

Select "Open" and navigate to the project folder

Mark src as Sources Root

Run Main.java

👤 Default Login Credentials
Role	Email	Password
Student	john@univ.edu	student123
Instructor	smith@univ.edu	teacher456
Administrator	admin@univ.edu	admin789
🧪 Testing Scenarios
Sample Test Flow
bash
# 1. Login as Student
Email: john@univ.edu
Password: student123
→ Browse Courses → Register for CS101 → View Schedule

# 2. Login as Instructor  
Email: smith@univ.edu
Password: teacher456
→ View Pending Requests → Approve Request

# 3. Login as Administrator
Email: admin@univ.edu
Password: admin789
→ Add New Course → View All Users → Manage Rules
Pre-loaded Data
Courses: CS101, CS201, CS301 with prerequisites

Users: 3 default users (Student, Instructor, Admin)

Course Offerings: 3 offerings with different schedules

Semester: Fall 2024 with open registration

🔧 Technical Implementation
Key Design Decisions
State Pattern: Used for registration workflow

Inheritance Hierarchy: User → Student/Instructor/Admin

Layered Architecture: Clear separation of concerns

Console-based UI: Simple, text-based interface for demo

UML Diagrams Implemented
✅ Design Class Diagram (12 classes)

✅ State Machine Diagram (Registration workflow)

✅ Package Diagram (Project structure)

✅ Component Diagram (System components)

Relationships Implemented
Student 1→* Registration

Course 1→* CourseOffering

Instructor 1→* CourseOffering

CourseOffering 1→1 Schedule (Composition)

📊 Development Metrics
Metric	Value
Total Classes	12
Total Lines of Code	~1,200
Development Time	3 weeks
Team Size	3 members
Test Coverage	85% (manual testing)
🐛 Known Issues & Limitations
Current Limitations
In-memory storage: Data lost on application restart

Simple authentication: No password encryption

Console interface: No graphical UI

Limited error recovery: Basic error handling



📚 Documentation
[Project Documentation](https://docs.google.com/document/d/1ASqM--pTf4_4mx46HOTMrsa_3j_V8EimEoXfMx0UjIM/edit?usp=sharing)


UML Diagrams
Class Diagrams

Sequence Diagrams

State Machine Diagram
Built with dedication by Mariam Agamy, Rana Ahmed, and Arwa
