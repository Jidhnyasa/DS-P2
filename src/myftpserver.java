import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
public class myftpserver {

	static ServerSocket serverSocket;

	static int portNumber = 0;

	public static void main(String[] args) {

		if (args.length != 1) {

			System.out.println("please specify the port number");

			System.exit(0);

		}

		portNumber = Integer.parseInt(args[0]);

		try {
			//ServerSocket server = new ServerSocket(portNumber);
			serverSocket = new ServerSocket(portNumber);
			
			
			

//			Runnable task = ;

			while (true) {

				Thread thread = new Thread(new Commands(serverSocket.accept()));

				thread.start();

			}

		} catch (IOException e) {

			System.out.println("Something went wrong");

		}

	}

}

class Commands implements Runnable {

	Socket socket;
	String clientInput = null;
	String firstArgument = null;
	String secondArgument = null;
	InputStreamReader inputReader;

	BufferedReader bufferReader;

	PrintStream printStream;

	boolean isExit = false;

	public Commands(Socket accept) {

		this.socket = accept;

		System.out.println("New Client connected!");

	}

	@Override

	public void run() {

		while (!isExit) {

			try {

				inputReader = new InputStreamReader(socket.getInputStream());

				bufferReader = new BufferedReader(inputReader);
				printStream = new PrintStream(socket.getOutputStream());
				clientInput = bufferReader.readLine();

				
				if(clientInput!=null) {
					String arguments[] = clientInput.split(" ");
					firstArgument = arguments[0];
					if(arguments.length==2 && arguments[1]!=null)secondArgument = arguments[1];
				}
				
				
				if(firstArgument!=null)
				switch (firstArgument) {

				case "ls":
					//to be 
					System.out.println("LS command recieved on server");
					// business logic
					String userDirectory = System.getProperty("user.dir");   //get the directory path for any machine
					File file = new File(userDirectory); //passing the directory address to file 
					String[] allfiles = file.list();    // list the files in the given folder
					
					String resultServer = null;
					for(String r : allfiles) {
						resultServer = resultServer + r + "\n";
					}
					
					//
					printStream.println(resultServer);
					break;

				case "get":

					System.out.println("get command recieved");

					break;

				case "put":

					System.out.println("put command recieved");

					break;

				case "cd":
					String changeDirectory = System.getProperty("user.dir"); 
					String cdDirectory = changeDirectory + "/" + secondArgument;
					printStream.println(cdDirectory);
					System.out.println("cd command recieved");

					break;

				case "mkdir":
					
					String homeDirectory = System.getProperty("user.dir"); 
					String createDirectory = homeDirectory + "/" + secondArgument;
					File myfile = new File(createDirectory);
					if (!myfile.exists()) {
			            if (myfile.mkdir()) {
			                System.out.println("Directory is created!");
			            } else {
			                System.out.println("Failed to create directory!");
			            }
			        }
					
					System.out.println("mkdir command recieved");

					break;

//				case "exit":
//
//					System.out.println("exit command recieved");
//
//					break;

				case "pwd":

					System.out.println("pwd command recieved");
					
					String path = System.getProperty("user.dir");   //get the directory path for any machine
					printStream.println(path);
					break;

				case "delete":
					String deleteDirectory = System.getProperty("user.dir"); 
					String delDirectory = deleteDirectory + "/" + secondArgument;
					File delfile = new File(delDirectory);
					delfile.delete();
					

					System.out.println("delete command recieved");

					break;

				}

			} catch (IOException e) {

				System.out.println("Error");

			}

		}

	}

}