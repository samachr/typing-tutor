Typing Program
-----------------------------------------------------------------
Added Feature
-----------------------------------------------------------------
My added feature is the gravity animation for the text. As you type a new JLabel is created with the same character as
the one that just got removed and then a timer progresses the animation through frames. Each frame the label is moved
a certain distance. Collisions with defined borders are checked for and when it collides the movement pattern (speed)
is changed to the opposite direction. The curvature of the trajectory is made by increasing the speed downward every frame
(simulating a sort of gravity).

Also part of the added feature is a usage of the gravity animation at the end of the game. When the person has typed all
the prompts (or advanced to the end) they will see an animation of all the keyboard keys flying everywhere for a few seconds.
It's like when you win solitare.

I chose this feature because it creates visual interest, thus making the game more enjoyable. It encourages the user to
type faster because it looks cooler to have a stream of flying characters, and gives a good winstate so they are encouraged
to finish all the prompts (though they can just advance to the end.)

-----------------------------------------------------------------
My project included 5 Gui components.
-----------------------------------------------------------------

JLabel:
    There are JLabels for errors and correct
JButton:
    next prompt and previous prompt are buttons, as well as all of the virtual keys (they are disabled)
JMenuBar:
    the menubar on the top of the window
JMenu:
    an example is the file menu
JMenuItem:
    an example is the quit menuitem.