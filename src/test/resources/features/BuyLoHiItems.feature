Feature: Buy lowest and highest price items
    As a sauce demo user,
    I want to login to application,
    So that I can buy products

  Background: Login to web application
    Given user navigate to sauce demo page
    And "login" page is displayed
    When user fills login form with "standard_user" and "secret_sauce"
    And user clicks on login button
    Then user should see the "home" page displayed

  Scenario Outline: Buying the lowest and highest price products
    Given "home" page is displayed
    And user filters products by "lowest price"
    When user adds the lowest and highest products to cart
    And user clicks on "cart" button
    And user verifies that "cart" page is displayed
    And user clicks on "checkout" button
    And user verifies that "information" page is displayed
    And user fills the user form with "<firstName>", "<lastName>" and "<postalCode>"
    And user clicks on "continue" button
    And user verifies that "overview" page is displayed
    And user verifies the values are correct
    And user clicks on "finish" button
    Then user should see the "complete" page displayed
    And user should see the cart empty

    Examples:
      | firstName  | lastName | postalCode |
      | THOMAS     | JENKINS  | 1235342    |

    Scenario: Logout from application
      Given "home" page is displayed
      When user clicks on "burger" button
      And user clicks on "logout" link
      Then user should see the "login" page displayed


