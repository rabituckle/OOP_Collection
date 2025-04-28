# OOP_Collection
## How to use `ValidateCreator`

1. **Create a `ValidateCreator` instance** by specifying the game you want to validate:
ValidateCreator v = new ValidateCreator("TienLenMienBac");

3. **Call the isValid method** to validate a move:
boolean result = v.isValid(lastPlayedCards, chosenCards)
