@khan
Feature: KhanAcademy HomePage
    Background:
        Given I am on khanAcademy home page

    Scenario: parent or student can able to view khanacademy offered course details under course link in the home page
        And verify the user in kahanAcademy home
        When user clicks on course link
        And  user click on computer science course link
        And user landed in to the computer science course
        And user select Algorithms link
        Then user able to view Algorithms details

