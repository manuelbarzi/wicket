// (c)2004 Templemore Technologies Limited. All Rights Reserved
// http://www.templemore.co.uk/copyright.html
package wicket.examples.hangman;

import java.io.Serializable;


/**
 * Implementation of the actual hangman game model. The model holds the
 * word generator, the current word, retries remaining and the letters that have been
 * guessed. It also answers questions such as whether all retries have
 * been used.
 *
 * @author Chris Turner
 * @version 1.0
 */
public class Hangman implements Serializable {

    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private WordGenerator wordGenerator;
    private String currentWord;
    private char[] letters;
    private boolean[] guessedLetters;
    private int guessesRemaining;
    private int guessesAllowed;

    /**
     * Initialise the hangman instance.
     *
     * @param guessesAllowed The number of guesses allowed
     * @throws WordListException If the word list cannot be read
     */
    public Hangman(final int guessesAllowed) throws WordListException {
        wordGenerator = new WordGenerator();
        init(guessesAllowed);
    }

    /**
     * Initialise the hangman instance.
     *
     * @param guessesAllowed The number of guesses allowed
     * @param words List of words to use instead of those contained in the file
     * @throws WordListException If the word list cannot be read
     */
    public Hangman(final int guessesAllowed, final String[] words) throws WordListException {
        wordGenerator = new WordGenerator(words);
        init(guessesAllowed);
    }

    /**
     * Common initialisation routine.
     *
     * @param guessesAllowed The number of guesses allowed
     */
    private void init(final int guessesAllowed) {
        currentWord = null;
        letters = null;
        guessedLetters = new boolean[26];
        guessesRemaining = 0;
        this.guessesAllowed = guessesAllowed;
        newGame();
    }


    /**
     * Initialise the hangman read for a new game.
     */
    public void newGame() {
        currentWord = wordGenerator.nextWord().toLowerCase();
        letters = new char[currentWord.length()];
        for ( int i = 0; i < letters.length; i++ ) letters[i] = '_';
        for ( int i = 0; i < guessedLetters.length; i++ ) guessedLetters[i] = false;
        guessesRemaining = guessesAllowed;
    }

    /**
     * Guess the given letter for the current word. If the letter matches then
     * the word is updated otherwise the guesses remaining counter is reduced.
     * The letter guessed is also recorded.
     *
     * @param letter The letter being guessed
     * @return Whether the letter was in the word or not
     */
    public boolean guessLetter(char letter) {
        letter = Character.toLowerCase(letter);
        boolean correctGuess = false;
        for ( int i = 0; i < currentWord.length(); i++ ) {
            if ( currentWord.charAt(i) == letter ) {
                correctGuess = true;
                letters[i] = letter;
            }
        }
        if ( !correctGuess && guessedLetters[letter - 'a'] == false ) guessesRemaining--;
        guessedLetters[letter - 'a'] = true;
        return correctGuess;
    }

    /**
     * Check whether the user has successfully guessed all of the letters in the word.
     *
     * @return Whether all of the letters have been guessed or not
     */
    public boolean isGuessed() {
        for ( int i = 0; i < letters.length; i++ ) {
            if ( letters[i] == '_') return false;
        }
        return true;
    }

    /**
     * Check whether the user has used up all of their guesses.
     *
     * @return Whether all of the user's guesses have been used
     */
    public boolean isAllGuessesUsed() {
        return (guessesRemaining == 0);
    }

    /**
     * Return the number of guesses remaining.
     *
     * @return The number of guesses
     */
    public int getGuessesRemaining() {
        return guessesRemaining;
    }

    /**
     * Get the state of the guessed letters for the word.
     *
     * @return The guessed letters
     */
    public String getLetters() {
        return new String(letters);
    }

    /**
     * Return whether the user has guessed the given letter or
     * not.
     *
     * @param letter The letter to check
     * @return Whether this letter has been guessed or not
     */
    public boolean isGuessed(char letter) {
        letter = Character.toLowerCase(letter);
        return guessedLetters[letter - 'a'];
    }

    /**
     * Get the current word that is being guessed or has been
     * guessed.
     *
     * @return The current word
     */
    public String getCurrentWord() {
        return currentWord;
    }
}
