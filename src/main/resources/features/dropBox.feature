@dropBox
Feature: IT Solution Page to be loaded from dropBox HomePage
    Background:
        Given I am on dropBox home page

    Scenario: User able to view dropBox solution for IT department
        And  verify the user in dropBox home
        When user mouse hover on solution link
        And  user click on IT service link
        Then verify user landing into the IT service page
