Feature: Work frustrated
    You need to know if you are frustrated

    Scenario: Spamming Escape Key
            Given I have the application running
            And I press Escape constantly
            When the page refreshes
            Then the result on the screen should indicate frustration

    Scenario: Writing normally
            Given I have the application running
            And I write as I usually do
            When the page refreshes
            Then the result on the screen should indicate normal state of mind
