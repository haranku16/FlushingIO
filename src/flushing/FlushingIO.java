package flushing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 
 * @author Harankumar Nallasivan
 * Class meant to abstract away IO mechanics and the need to flush output streams
 * by assuming that flushing must take place before the next read operation.
 * 
 */
public class FlushingIO {
	
	private static enum IOMode { READ, WRITE};
	private IOMode mode = IOMode.READ;
	private BufferedReader in = null;
	private OutputStreamWriter out = null;
	
	public FlushingIO(InputStream is, OutputStream os) throws NoIOStreamException {
		if (is != null && os != null) {
			throw new NoIOStreamException();
		}
		if (is != null) {
			in = new BufferedReader(new InputStreamReader(is));
		}
		if (os != null) {
			out = new OutputStreamWriter(os);
		}
	}
	
	public void write(String s) throws IOException {
		if (mode == IOMode.READ) {
			mode = IOMode.WRITE;
		}
		out.write(s);
	}
	
	public String readLine() throws IOException {
		if (mode == IOMode.WRITE) {
			out.flush();
			mode = IOMode.WRITE;
		}
		return in.readLine();
	}
}
