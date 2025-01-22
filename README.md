**Amazon Automation Framework**
This framework is designed to automate specific functionalities on the Amazon website using Selenium WebDriver and TestNG. 
The framework provides modular and reusable test scripts to validate functionalities such as product search, related searches, and quick look features.

**Prerequisites**
Java Development Kit (JDK):
- Ensure JDK 8 or above is installed and properly configured in the system's PATH.
Maven:
- Maven is required for managing dependencies and building the project.
Browser and WebDriver:
- This framework currently supports Google Chrome.
**Important**: Ensure that the ChromeDriver version matches the installed Google Chrome version. If there is a mismatch, download the correct version from the ChromeDriver download page.
Dependencies:
- All required dependencies are defined in the pom.xml file for Maven projects. Run the following command to download them:

**Important Notes**
Keep ChromeDriver Updated:
- Ensure that the ChromeDriver version matches the installed version of Google Chrome. Mismatches will result in test failures.
Property Configuration:
- Configure the application.properties file to update browser type and driver paths.
Dependencies:
- Ensure all required dependencies are downloaded via Maven before running the tests.
Extensibility:
- This framework can be extended to support additional browsers and functionalities by updating the setUpDriver method and adding corresponding libraries.

**Code Structure**
Package: testScripts
- Contains the main test class TestAmazon.
Package: functionalLibrary
- Contains reusable methods for navigating and interacting with the Amazon website.
Package: util
- Includes utility classes for setting up the WebDriver and HTML reporting.

**Test Execution Steps**
Set Up the Environment:
- Clone or download the repository.
Run Tests:
- Execute the test suite using TestNG
View Reports:
- After the test execution, the HTML report will be generated in the Results folder:
/Results/Report.html
