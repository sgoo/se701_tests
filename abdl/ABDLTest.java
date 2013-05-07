package abdl;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ABDLTest {

	@Parameters
	public static Collection<Object[]> files() {
		Collection<File> files = new ArrayList<File>();

		files.addAll(Arrays.asList(new File("./test/correct/").listFiles()));
		files.addAll(Arrays.asList(new File("./test/incorrect/").listFiles()));

		Collection<Object[]> params = new ArrayList<Object[]>();

		for (File f : files) {
			params.add(new File[] { f });
		}

		return params;
	}

	private void parseFile(File file) throws FileNotFoundException,
			ParseException {
		new ABDLParser(new FileInputStream(file)).AddressBook();
	}

	private File file;

	public ABDLTest(File file) {
		this.file = file;
	}

	@Test
	public void testADBL() {
		try {
			parseFile(this.file);

			if (this.file.getParent().equals("./test/incorrect")) {
				fail(file.getName() + " parsed, should have failed.");
			}
		} catch (FileNotFoundException | ParseException | TokenMgrError e) {
			if (this.file.getParent().equals("./test/correct")) {
				fail(file.getName() + " failed to parse, should have parsed:\n" + e.getMessage());
			}
		}
	}
}
