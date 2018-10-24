Feature: Testing Hotel API

Scenario: Testing the updates
  Given items on the list
  When the item is posted
  And the item is updated
  And the item is deleted
  Then the status code reads 404