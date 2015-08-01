package umw.dahlgren.twitter.snapshot;

public class HistFileFilter implements java.io.FilenameFilter {

	public boolean accept(java.io.File dir, String name) {
		return name.endsWith(".dbhist");
	}

}
