package unl.cse.albums;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AlbumAdderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		String albumTitle  = request.getParameter("albumTitle");
		String bandName    = request.getParameter("bandName");  
		String albumYear   = request.getParameter("albumYear");
		String albumNumber = request.getParameter("albumNumber");

		try {
			AlbumAdder.addAlbumToDatabase(albumTitle, bandName, albumYear, albumNumber);
			response.sendRedirect("index.html");
			
		} catch (Exception e) {
			try {
				response.sendRedirect("error.html");
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
		}
	}
	
}
