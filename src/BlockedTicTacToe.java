public class BlockedTicTacToe {

	// Initialising all the instance variables

	private int board_size;
	private int inline;
	private int max_levels;

	private char[][] gameBoard;

	int count = 0;

	// Creates an object for BlockedTicTacToe()
	/**
	 * @param board_size
	 * @param inline
	 * @param max_levels
	 */
	public BlockedTicTacToe(int board_size, int inline, int max_levels) {
		this.board_size = board_size;
		this.inline = inline;
		this.max_levels = max_levels;
		gameBoard = new char[board_size][board_size];

		// This loop fills in all the squares of the gameBoard with ' '
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				gameBoard[i][j] = ' ';
			}
		}
	};

	// The createDictionary() method creates a dictionary for the hashtable
	/**
	 * @return
	 */
	public TTTDictionary createDictionary() {
		// Creates a dictionary of space 4999
		TTTDictionary configurations = new TTTDictionary(4999);
		return configurations;
	};

	// The repeatedConfig() method takes the configurations as an input and
	// checks to see if the inputed configuration has
	// been repeated in the hashtable and if it is, then the score of that
	// configuration is returned
	/**
	 * @param configurations
	 * @return
	 */
	public int repeatedConfig(TTTDictionary configurations) {

		// Checks to see if the dictionary is empty
		if (count == 0)
			return -1;
		count++;

		String s = "";
		// Creates a string of the configuration on the board
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				s += gameBoard[i][j];
			}
		}
		// Get the score of the configuration
		int getValue = ((TTTRecord) configurations.get(s)).getScore();

		// if The score is 0, -1 is returned
		if (getValue == 0) {
			return -1;
		}
		return getValue;
	};

	// The insertConfig() method takes configurations, score and the level as
	// inputs and inserts that configuration in the
	// hashtable
	/**
	 * @param configurations
	 * @param score
	 * @param level
	 */
	public void insertConfig(TTTDictionary configurations, int score, int level) {
		String s = "";
		// Creates a string of the configuration on the board
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				s += gameBoard[i][j];
			}
		}
		// Inserts that configuration, score and level in the dictionary as a
		// record
		configurations.put(new TTTRecord(s, score, level));
	};

	// The storePlay() method takes row, column and symbol as an input and
	// stores that symbol in the row and column
	// position in the gameBoard
	/**
	 * @param row
	 * @param col
	 * @param symbol
	 */
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	};

	// The squareIsEmpty() method takes row and column as inputs and return true
	// if that position in the gameBoard is
	// not occupied by any symbol
	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean squareIsEmpty(int row, int col) {
		return (gameBoard[row][col] == ' ');
	};

	// The HorizontalWinCheck() takes a symbol as an input and checks whether
	// that symbol wins horizontally
	/**
	 * @param symbol
	 * @return
	 */
	private boolean HorizontalWinCheck(char symbol) {
		// Iterates through all of the rows and all of the columns horizontally
		// and if a position on the game board has the inputed symbol. If so,
		// count is incremented. And if the count is equal to or greater than
		// the inline value, it returns true. And if at any point, if the
		// position of the game board does not have that symbol, count
		// initialises to 0.
		for (int i = 0; i < board_size; i++) {
			int count = 0;
			for (int j = 0; j < board_size; j++) {
				if (gameBoard[i][j] == symbol) {
					count++;
				} else if (gameBoard[i][j] != symbol) {
					count = 0;
				}
				if (count >= inline) {
					return true;
				} 
			}
		}
		return false;
	};

	// The VerticalWinCheck() takes a symbol as an input and checks whether that
	// symbol wins vertically
	/**
	 * @param symbol
	 * @return
	 */
	private boolean VerticalWinCheck(char symbol) {
		// Iterates through all of the columns and all of the rows vertically
		// and if a position on the game board has the inputed symbol. If so,
		// count is incremented. And if the count is equal to or greater than
		// the inline value, it returns true. And if at any point, if the
		// position of the game board does not have that symbol, count
		// initialises to 0.
		for (int j = 0; j < board_size; j++) {
			int count = 0;
			for (int i = 0; i < board_size; i++) {
				if (gameBoard[i][j] == symbol) {
					count++;
				} else if (gameBoard[i][j] != symbol){
					count = 0;
				}
				if (count >= inline) {
					return true;
				} 
			}
		}
		return false;
	};

	// The DiagonalWinCheck() takes a symbol as an input and checks whether that
	// symbol wins diagonally
	/**
	 * @param symbol
	 * @return
	 */
	private boolean DiagonalWinCheck(char symbol) {

		int countneeded = inline - 1;
		int count;

		// Iterates through all the rows and columns and making sure that
		// coordinates for the position do not go outside the board size. Then
		// it checks whether the [row + i][col + i] and [row - i][col + i] are
		// equal to the inputed symbol, and if it is then count is incremented.
		// If count is equal to inline, then it returns true
		for (int row = 0; row < board_size; row++) {
			for (int col = 0; col < board_size; col++) {
				count = 0;
				if ((row + countneeded < board_size) && (col + countneeded < board_size)) {
					for (int i = 0; i < inline; i++) {
						if (gameBoard[row + i][col + i] == symbol) {
							count++;
						}
					}
				}
				if ((row - countneeded >= 0) && (col + countneeded < board_size)) {
					for (int i = 0; i < inline; i++) {
						if (gameBoard[row - i][col + i] == symbol) {
							count++;
						}
					}
				}
				if (count == inline)
					return true;

			}
		}
		return false;
	};

	// The wins() method takes a symbol as an input and check if that symbol
	// wins at all on the gameBoard
	/**
	 * @param symbol
	 * @return
	 */
	public boolean wins(char symbol) {
		// returns true if any of check conditions are true
		return (HorizontalWinCheck(symbol) || VerticalWinCheck(symbol) || DiagonalWinCheck(symbol));
	};

	// The isDraw() method checks to see the position of the board gives a draw
	// or not
	/**
	 * @return
	 */
	public boolean isDraw() {
		boolean emptyfound = false;
		// Iterates through all the rows and columns of the game board and
		// checks to see if the any of the positions are empty, and if it is,
		// then emptyFound is changed to true.
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				if (gameBoard[i][j] == ' ') {
					emptyfound = true;
				}
			}
		}
		// If the human doesn't win, computer doesn't win, and all the position
		// on the board are filled, true is returned
		if (!(emptyfound) && !(wins('x')) && !(wins('o')))
			return true;
		return false;
	};

	// The evalBoard() method checks to how the player or computer is playing
	// and return numbers relating to a different
	// situation
	/**
	 * @return
	 */
	public int evalBoard() {
		if (wins('o') && !wins('x')) {
			return 3;
		} else if (wins('x') && !wins('o')) {
			return 0;
		} else if (isDraw()) {
			return 1;
		} else {
			return 2;
		}
	};
}
