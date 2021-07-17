
#Sample Feature Definition Template
Feature: Holiday maker weather check
  I want to check the weather before going for a pleasant holiday

  Scenario Outline: A happy holidaymaker
    Given I want to holiday in "<City>"
    And I like to holiday only on "<Day>" 
    When I look up weather forecast 
    Then I receive weather forecast
    And the temperature is warmer than <Temperature> degrees 

    Examples: 
      |City  |Day      |Temperature|
      |Sydney|Thursdays|10				 |
      |London|Fridays  |5          |

      