# Simpe_Banking_System
Find out how the banking system works and learn about SQL and databases. 
See how the Luhn algorithm can help us avoid mistakes when entering the card number and learn basic OOP concepts such as classes.

The very first number is the Major Industry Identifier (MII), which tells you what sort of institution issued the card.

1 and 2 are issued by airlines
3 is issued by travel and entertainment
4 and 5 are issued by banking and financial institutions
6 is issued by merchandising and banking
7 is issued by petroleum companies
8 is issued by telecommunications companies
9 is issued by national assignment

The first six digits are the Bank Identification Number (BIN). These can be used to look up where the card originated from. 
If you have access to a list that provides detail on who owns each BIN, you can see who issued the card just by reading the card number.

Here are a few you might recognize:

Visa: 4*****
American Express (AMEX): 34**** or 37****
Mastercard: 51**** to 55****

The seventh digit to the second-to-last digit is the customer account number. Most companies use just 9 digits for the account numbers, 
but it’s possible to use up to 12. 
This means that using the current algorithm for credit cards, the world can issue about a trillion cards before it has to change the system.

We often see 16-digit credit card numbers today, but it’s possible to issue a card with up to 19 digits using the current system. 
In the future, we may see longer numbers becoming more common.

The very last digit of a credit card is the check digit or checksum. It is used to validate the credit card number using the Luhn algorithm.  

