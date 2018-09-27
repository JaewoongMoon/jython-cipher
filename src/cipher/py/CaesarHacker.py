from cipher.interfaces import ICaesarHacker
#from cipher.ui.CipherUI import HackingActionHandler 

LETTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

class CaesarHacker (ICaesarHacker):
    ''' Caesar Hacker '''
    
    def __init__(self):
        self.message = ''
        self.ui = None
        
    def hacking(self, message):
        self.message = message
        translated = ''
         # loop through every possible key
        result = ''
        for key in range(len(LETTERS)):
        
            # It is important to set translated to the blank string so that the 
            # previous iteration's value for translated is cleared. 
            translated = ''
        
            # The rest of the program is the same as the original Casesar program:
        
            # run the encryption/decryption code on each symbol in the message
            for symbol in message:
                if symbol in LETTERS:
                    num = LETTERS.find(symbol) # get the number of the symbol
                    num = num - key
        
                    # handle the wrap-around if num is 26 or larger or less than 0
                    if num < 0:
                        num = num + len(LETTERS)
        
                    # add number's symbol at the end of translated
                    translated = translated + LETTERS[num]
                else:
                    # just add the symbol without encrypting/decrypting
                    translated = translated + symbol
        
            # display the current key being tested, along with its decryption
            # print 'Key #%s: %s' % (key, translated)
            temp = 'Key #%s: %s' % (key, translated)
            result += temp
            self.ui.printResult(temp)
        return result
    
    def setUI(self, ui):
        self.ui = ui
        