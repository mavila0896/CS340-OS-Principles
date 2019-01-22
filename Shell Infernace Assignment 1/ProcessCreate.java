import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessCreate {

	public static void createProcess(String commandLine, File currentDirectory, String OSName) throws IOException {
		ProcessBuilder pb;
		if (OSName.contains("Windows")) {
			pb = new ProcessBuilder("cmd", "/c", commandLine);
		} else {
			pb = new ProcessBuilder(commandLine);
		}
		pb.directory(currentDirectory);

		Process process = pb.start();
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null)
			System.out.println(line);
		br.close();

	}
}
