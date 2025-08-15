# Luma_AutomationTesting

**Luma_AutomationTesting** was built to validate core functionalities of the [Magento Luma Demo Store](https://magento.com/tech-resources/demo) as my graduation project form Intensive training program at ITI software testing track. This project is designed for clarity, scalability, and integration into CI/CD pipelines using industry-standard tools.

---

## Overview

This framework automates UI testing for the Luma e-commerce platform using:

- **Selenium WebDriver** — browser automation
- **TestNG** — test orchestration and assertions
- **Allure Reports** — interactive reporting
- **Jenkins** — CI/CD integration
- **GitHub** — version control and collaboration

---

## Project Structure

Luma_AutomationTesting/
├── src/
│   ├── main/java/         # Page Objects, Utilities
│   └── test/java/         # Test Cases
├── testng.xml             # TestNG suite configuration
├── pom.xml                # Maven build and dependencies
└── README.md              # Project documentation

---

## Tested Features & E2E Scenarios

This framework validates key user journeys and functional components of the Magento Luma Demo Store.

### Functional Features Tested

- **User Authentication**
  - Signin with valid and invalid credentials
  - Signup with valid and invalid credentials
  - Signout functionality
- **Product Browsing**
  - Category navigation
  - Product detail page validation
- **Search & Filtering**
  - Keyword-based product search
  - Filter by price, color, and size
  - Search order
- **Shopping Cart**
  - Add/remove/update products
  - Price and subtotal validation
  - Cart discount (for over 3 items)
- **Checkout Process**
  - Guest, registered user and new user checkout
  - Shipping method selection
  - Payment method simulation
  - Dicount code 
- **Order Confirmation**
  - Order summary validation
  - Success message verification
- **Wishlist**
  - Add Item to Wishlist
- **UI & UX Validation**
  - Element visibility and responsiveness
  - Error message handling
  - Page load and navigation checks


### End-to-End Scenarios

There is six core user journeys — three positive and three negative — to ensure both successful and failure-prone interactions are handled gracefully across the Magento Luma demo store.

### Positive Scenarios

1. **Guest Checkout Flow**
   - Navigate to the home page  
   - Select a product and add it to the cart  
   - Proceed to checkout  
   - Enter shipping details  
   - Provide payment information  
   - Complete the purchase and verify confirmation  

2. **Registered User Purchase Flow**
   - Navigate to the sign-in page  
   - Log in with valid credentials  
   - Search for a product using the search bar  
   - Sort results by price (ascending)  
   - Select size and color, then add product to cart  
   - Add an item to the wishlist  
   - Remove product from cart  
   - Return to home and add a new item to cart  
   - Apply a discount code  
   - Confirm address and payment during checkout  
   - Place the order and verify confirmation  
   - Sign out  

3. **New User Registration & Purchase Flow**
   - Navigate to the sign-up page  
   - Create an account with valid credentials  
   - Browse the "Tee" category  
   - Add a product to the cart  
   - Update quantity to 4 to trigger cart discount  
   - Enter shipping and payment details  
   - Confirm and place the order  



### Negative Scenarios

1. **Forgot Password Flow**
   - Attempt login with invalid credentials  
   - Leave login fields empty  
   - Request password reset using an unregistered email  
   - Request password reset using a registered email  

2. **Invalid Registration Flow**
   - Leave registration fields empty  
   - Enter email in invalid format  
   - Attempt to register with an existing email  
   - Fail to confirm password correctly  
   - Use a weak password (invalid format or insufficient length)  

3. **Invalid Product Selection Flow**
   - Attempt to add item with quantity set to 0  
   - Select an out-of-stock item  
   - Add item to wishlist as a guest user  
   - Add item without selecting required size and color  

---

## Key Features

-  Modular Page Object Model (POM) design  
-  Robust fallback logic and retry mechanisms  
-  Screenshot capture on failure  
-  Parameterized test execution via TestNG  
-  Allure reporting with environment metadata  
-  Jenkins pipeline-ready structure
-  Documenting and presenting test strategies professionally

---

## Prerequisites

Make sure the following are installed:

- Java 21
- Maven 3.6+
- ChromeDriver (compatible with your Chrome version)
- Allure
- Git
- Jenkins (optional for CI)

---

## 🧑‍💻 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/Samar1000/Luma_AutomationTesting.git
   cd Luma_AutomationTesting
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Generate Allure Report**
   ```bash
   mvn allure:serve
   ```

---

## Reporting

Allure generates detailed HTML reports with:

- Test history
- Step-by-step execution
- Screenshots on failure
- Environment details

