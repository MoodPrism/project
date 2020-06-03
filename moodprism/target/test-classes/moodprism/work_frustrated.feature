Feature: Work frustrated
    You need to know if you are frustrated

    Scenario: Spamming Escape Key
            Given I have the application running
            When I press any key "a" 
            And the page refreshes
            Then The key should be on the screen

    Scenario: Writing normally
            Given I have the application running
            And I write as I usually do
            When the page refreshes
            Then the result on the screen should indicate normal state of mind
