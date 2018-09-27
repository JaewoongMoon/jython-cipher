from cipher.interfaces import ICaesarCipher

LETTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'


class CaesarCipher (ICaesarCipher):
    ''' Make CaesarCipher Object'''
    
    def __init__(self):
        self.key = ''
        self.mode = "decrypt" # set to 'encrypt' or 'decrypt'
        self.message = ''
    
    def initialize(self, key):
        self.key = key
        
    def process(self, mode, message):
        self.mode = mode
        self.message = message
        key = self.key
        translated = ''
        
        # run the encryption/decryption code on each symbol in the messsage string
        for symbol in message:
            # print symbol
            if symbol in LETTERS:
                # get the encrypted (or decrypted) number for this symbol
                num = LETTERS.find(symbol) # get the number of the symbol
                if mode == 'encrypt':
                    num = num + key
                elif mode == 'decrypt':
                    num = num - key
        
                # handle the wrap-around if num is larger than the length of 
                # LETTERS or less than 0
                if num >= len(LETTERS):
                    num = num - len(LETTERS)
                elif num < 0:
                    num = num + len(LETTERS)
        
                # add encrypted/decrypted number's symbol at the end of translated
                translated = translated + LETTERS[num]
        
            else:
                # just add the symobol without encrypting/decrypting
                translated = translated + symbol 
               # print "just add : %s" % translated
        
        return translated
            