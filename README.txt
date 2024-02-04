=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 46635743
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1.2D Arrays- 2D Arrays are used in this game to keep track of the Traps in the Lab game model
  and all the GridButtons which represent spaces in the lab grid to the use,
  2. JUnit Testable- The "Lab" Model class works separately from the view and is tested by the LabTest class.
  After each action, the public methods of the Lab class return a "Notifier" record which gives the MouseGame class
  the necessary information to update the view and continue the game.
  3.Collections- I use a Linked List called actionQueue to store Strings, each of which represent some action that
  a user has taken. There were a lot of chances to use collections throughout this project,
  but in all but one of the cases, arrays seemed to be the more appropriate choice after some trial and error.

  4.File I/O- Ok I admittedly didn't do a great job here. See more details below in "Stumbling Blocks".

  5.Complex Game Logic??? - I feel like there is pretty complex game logic here for how to handle all
  the different events, which moves are possible at any time, how to notify user for all these possibilities, etc.
  Probably won't get any points for this but just throwing the idea out there.

Proposal was all good, so no feedback to incorparate for any of the 4.
===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  Records-
    -Various Notifiers: convenient of way of storing complicated information leaving the model and
    keeping everything encapsulated, used to update view and move game along
    -Special Move Direction: stores information for a chosen special move

  Model-
    -Lab: Model class, two Labs run at same time to model state of each player's Lab, returns notifiers and
    updated by MouseGame class

  View-
    -GridButton: represents spaces in the grid to user, repaints itself in accordance with actions,
    when clicked adds trapped
    -LaserButton: when clicked adds lasers
    -LabDisplay: Class which displays each lab in GUI and stores and organizes the buttons described enough,
    instead of changing the state of buttons individually, the control calls the LabDisplay which handles
    changing state of buttons
    -ActionToolbar: JPanel which displays all the different buttons for choosing actions as well as the next
    turn button, uses a card layout so that only the appropriate buttons are accessible at any given time
    -TextDisplay: JTextArea which takes in information from notifiers and updates text for user with the
    appropriate information, public methods have lots of different possible series of strings for each possibility
    -MouseView: Class which combines and organizes all the view components

    Control-
    -MouseGame: adds actionListeners to all the buttons and combines everything into one

    RunMouseGame starts game and asks for new inputs whereas RunSavedMouseGame reads from file to start.
    Also, if you don't want to go through the hassle of putting in all the inputs, you can use
    RunTestMouseGame in the test folder.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

I really tried to do too much with this assignment. Minesweeper would have been way easier :(
I didn't really complete the FileIO part of this assignment but pretty close. The gameState is too
complicated to easily store in file so had to keep track of history of actions and "replay" them.
When a MouseGame class is generated it produces a String storing initial values and
keeps track of it as a private var. Then, every time an Action is completed,
a string storing all the of the info needed to replay the action is added to a LinkedList. Upon close,
all of these strings and an additional one with ending information is written to a file. I have set up
things so that the initial values are properly stored and can reinitialize the game after close but
didn't have time to finish all the "replay". To separate out any potential additional bugs, I have
created two different run classes: one that accesses File with saved state and other which does not.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think that the functionality is well seperated, but I might want to go back and make some records and
  classes inner classes of others or at least separate different parts into folders. Private state is pretty
  well encapsulated I think, but a good bit of refactoring to do. Relatively insignificant bug that didn't
  have time to fix is that the round counter is all wonky right now. I'll probably finish this project
  this summer, so hopefully everything will work out then.



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
  JavaDocs and occasionally similar resources that were easier to read, lecture slides
