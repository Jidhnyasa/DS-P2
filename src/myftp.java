import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class myftp {

	static Socket SOCK;
	public static void main(String[] args) {
		String machineName;
		int portNumber;
		if (args.length != 2) {
			System.out.println("please specify the Machine Name and port number");
			System.exit(0);
		}

		machineName = args[0];

		portNumber = Integer.parseInt(args[1]);

		try {

			SOCK = new Socket(machineName, portNumber);
			myftp client = new myftp();

			client.getInput();
		} catch (UnknownHostException e) {

			System.out.println("UnknownHostException");

		} catch (IOException e) {

			System.out.println("IOException");

		}

	}

	private void getInput() throws IOException {

		InputStreamReader inputReader;

		PrintStream printStream = null;

		BufferedReader bufferReader = null;

		String userInput;
		String firstArgument = null;
		String secondArgument = null;
		

		try {

			inputReader = new InputStreamReader(SOCK.getInputStream());
			bufferReader = new BufferedReader(inputReader);
			printStream = new PrintStream(SOCK.getOutputStream());

		} catch (IOException e) {

			System.out.println("IOException Error");

		}

		do {

			System.out.println("mytftp>");

			Scanner scanner = new Scanner(System.in);

			userInput = scanner.nextLine();
			String arguments[] = userInput.split(" ");
			firstArgument = arguments[0];
			if(arguments.length==2 && arguments[1]!=null)secondArgument = arguments[1];
			
			
			switch (firstArgument) {

			case "ls":

				printStream.println("ls"); // send command to server
				
				String result;

				while(!(result = bufferReader.readLine()).equals(""))
					System.out.println(result);
				
				

				break;

			case "get":

				System.out.println("get command recieved");

				break;

			case "put":

				System.out.println("put command recieved");

				break;

			case "cd":
				printStream.println(userInput);
				System.out.println("cd command recieved");

				break;

			case "mkdir":
				printStream.println(userInput);
				
				
				System.out.println("mkdir command recieved");

				break;

//			case "quit":
//
//				System.out.println("quit command recieved");
//
//				break;

			case "pwd":
				printStream.println("pwd");
				
				System.out.println("pwd command recieved");
				String resultPwd = null;

				resultPwd = bufferReader.readLine();
					System.out.println(resultPwd);
				break;

			case "delete":
				printStream.println(userInput);
				
				System.out.println("delete command recieved");

				break;

			}

		} while (!userInput.equals("quit"));

	}

}
