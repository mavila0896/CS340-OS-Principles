import java.io.*;

public class SimpleShell {

	public static void main(String[] args) throws java.io.IOException {
		String commandLine;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		ProcessCommand pc = new ProcessCommand();
		boolean running = true;
		while (running) {
			System.out.print("jsh>");
			commandLine = console.readLine();
			running = pc.process(commandLine);

		}
	}
	
}