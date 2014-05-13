/**
 * Description: Interface for constant variable used by other classes.
 * @author Jing Zhao
 **/

package ftp.config;

public interface Settings {
	
	//images location
	public static final String DIR_IMG = "./src/images/folder.png";
	public static final String FILE_IMG = "./src/images/file.png";
	public static final String LEFT_IMG = "./src/images/arrow_left.png";
	public static final String RIGHT_IMG = "./src/images/arrow_right.png";
	
	//for ftp client gui 
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGHT = 1200;
	public  static final String[] COLUMNS = {
		"",
		"Filename", 	
		"Filesize",
		"Filetype", 
		"Last Modify"};
	
	//for DirChangeListener
	public static final String CURRENT = ".";
	public static final String PARENT = "..";
	public static final char WIN_SEPARATOR = '\\';
	public static final char UNIX_SEPARATOR = '/';
	public static final int NUM_OF_TIMES = 3;
	public static final long SLEEP_MS = 1000;
	
	//for status output on client gui
	public static final String STATUS = "Status: \t";
	public static final String COMMAND = "Command: \t";
	public static final String RESPONSE = "Response: \t";
	public static final String ERROR = "Error: \t";

	//for ftp socket ports
	public static final int DATASOCKET_PORT = 35085;
	public static final int CMDSOCKET_PORT = 35086;
	public static final int DIR_LIST = 0;
	public static final int FILE_DOWNLOAD = 1;
	public static enum MODE{
		DIR_LIST,
		FILE_DOWNLOAD
	};
	
	//for ftp server
	public static final String ACCOUNT_IN = "./src/ftp/config/account.in";
	public static final String DEFAULT_DIR = "/";
	public static final int BLOCK_SIZE = 1024;
	
	//for cryptograph
	public static final String KEY_FILE = "./src/ftp/config/key.txt";
	public static final String REQUESTEDENCRYPTIONALGORITHM = "DES";
	public static final int KEYLENGTH = 56;
	
}
