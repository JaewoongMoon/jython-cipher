from cipher.interfaces import ITranspositionCipher
import math

class TranspositionCipher (ITranspositionCipher):
    ''' Make TranspositionCipher Object'''
    
    def __init__(self):
        pass
        
        
    def encryptMessage(self,key, message):
        # Each string in ciphertext represents a column in the grid. 
        ciphertext = [''] * key
    
        # Loop through each column in ciphertext. 
        for col in range(key):
            pointer  = col 
    
            # Keep looping until pointer goes past the length of the message. 
            while pointer < len(message):
                # Place the character at pointer in message at the end of the 
                # current column in the ciphertext list.
                ciphertext[col] += message[pointer]
    
                #move pointer over
                pointer += key
    
    
        # Convert the ciphertext list into a single string value and return it. 
        return ''.join(ciphertext)
    
    def decryptMessage(self,key, message):
        # The transposition decrypt function will simulate the "columns" and
        # "rows" of the grid that the plaintext is written on by using a list
        # of strings. First, we need to calculate a few values.
    
        # The number of "columns" in our transposition grid:
        numOfColumns = int(math.ceil(len(message) / key))
        # The number of "rows" in our grid will need:
        numOfRows = key
        # The number of "shaded boxes" in the last "column" of the grid:
        numOfShadedBoxes = (numOfColumns * numOfRows) - len(message)
    
        # Each string in plaintext represents a column in the grid.
        plaintext = [''] * numOfColumns
    
        # The col and row variables point to where in the grid the next
        # character in the encrypted message will go. 
        col = 0
        row = 0
    
        for symbol in message:
            plaintext[col] += symbol
            col += 1 # point to next column
    
            # If there are no more columns OR we're at a shaded box, go back to 
            # the first column and the next row. 
            if(col == numOfColumns) or (col == numOfColumns -1 and row >= numOfRows - numOfShadedBoxes):
                col = 0
                row += 1
    
        return ''.join(plaintext)
    
    