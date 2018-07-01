package unicam.cs.forza4;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/** Main class to test Grid */
public class TestRunner
{
	public static void main(String[] args)
	{
		Result result = JUnitCore.runClasses(GridTest.class);
		for (Failure failure : result.getFailures())
		{
			System.out.println(failure.toString());
		}
	}
}

