import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessCommand {

	private String OSName = System.getProperty("os.name");
	private List<String> history = new ArrayList<String>();
	private File currentDirectory = new File(System.getProperty("user.home"));

	public boolean process(String commandLine) {

		String[] commands = commandLine.split(" ");
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < commands.length; i++) {
			list.add(commands[i]);
		}
		try {
			if (list.get(0).equals("!!")) {
				return process(history.get(history.size() - 1));

			} else if (list.get(0).charAt(0) == '!') {
				try {
					int b = Integer.parseInt(list.get(0).substring(1));
					if (history.size() <= b) {
						System.out.println("Invalid value for history!");
					} else {
						return process(history.get(b));
					}
				} catch (NumberFormatException nfe) {
					System.out.println("Invalid number!");
				}
			} else {
				history.add(commandLine);
			}
			if (list.get(0).equals("history")) {
				return history();
			}

			if (list.get(0).equals("cd")) {
				return cd(list);
			}

			ProcessCreate.createProcess(commandLine, currentDirectory, OSName);
		}

		catch (IOException e) {
			System.out.println("Input Error, Please try again!");
		}
		return true;
	}

	private boolean history() {
		int index = 0;
		for (String s : history)
			System.out.println((index++) + " " + s);
		return true;
	}

	private boolean cd(List<String> list) {
		if (list.size() == 1) {
			File home = new File(System.getProperty("user.home"));
			System.out.println(home.getAbsolutePath());
			currentDirectory = home;
			return true;

		} else {
			String dir = list.get(1);
			File newPath;
			if (dir.substring(0, 1).equals(File.separator) || dir.substring(0, 1).equals("/")) {
				newPath = new File(dir);
			} else {
				newPath = new File(currentDirectory, dir);
			}

			boolean exists = newPath.exists();

			if (exists) {
				System.out.println(newPath.getAbsolutePath());
				currentDirectory = newPath;
				return true;
			} else {
				System.out.println("Directory doesn't exist, Try again! ");
				return true;
			}
		}
	}
}