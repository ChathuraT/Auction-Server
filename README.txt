AUCTION SERVER 09
by E/13/107
Gamage C.T.N


Client Side
===========================================================

♦ When a user connects to the server he/she shold initially provide his name.
(The first input he/she enters is taken as his name) 

♦ After that he/she should enter the symbol of the security he/she wants to bid on.

♦ If user inputs an ivalid symbol it will prompt an error message with -1

♦ If user enters a correct symbol the current price of the security is shown and 
 he/she is granted the permission to bid on that item.

♦ Then after a user enter a value it is validated whether it exceeds the current price of the security and
 if so the current value is updated to the user's bid and at the same time it records the user's name and bid value
 to a seperate database.

♦ User can quit from the system by entering "quit" at anytime.

Server Side
===========================================================

♦  The GUI displays the current values of some securites with their full security name and the symbol.

♦ These values are updated in every 500ns. If a client successfully bid on one of those items it will display the update.

♦ The GUI contains a serch bid history option which allows to view all the bid history of a specific item.

♦ When the SYMBOL of an security is entered to the text box and the "search bid history" button is clicked it will validate 
  the symbol with the stocks database and prompt "Input Symbol is Wrong !" if a wrong symbol is entered.
  If a correct symbol is entered on the text box and it has no any bids yet it will prompt "No bids available for xxx yet! Initial Price is xxx" and if
  the symbol entered has bids it will list up all the bids with clients names.

♦ Server side price update
   - It is needed to enter a valid symbol in the top text box and a valid amount in the bottom text box to update the price of an security.
    (They will be validated by the system)
   - The new value will not be validate against the current value of the security since it is a server side update.
    (New price should not necessarily be greater than the current price)
   - A new entry will be recorded upon a update under the security's bid history as "Admin Update  xxx"

