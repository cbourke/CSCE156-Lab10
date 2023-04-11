package unl.soc.albums;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BandLoader {

	/**
	 * This method returns a {@link #Band} instance loaded from the database
	 * corresponding to the given <code>bandId</code>. Throws an
	 * {@link IllegalStateException} upon an invalid <code>bandId</code>. All fields
	 * are loaded with this method.
	 * 
	 * @param bandId
	 * @return
	 */
	public static Band getBand(int bandId) {
		Band b = null;

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String query = "SELECT name FROM Band WHERE bandId = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, bandId);
			rs = ps.executeQuery();
			if (rs.next()) {
				b = new Band(bandId, rs.getString("name"));
			} else {
				throw new IllegalStateException("no such band with bandId = " + bandId);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		query = "SELECT firstName, lastName, country FROM BandMember bm JOIN Musician m ON bm.musicianId = m.musicianId WHERE bm.bandId = ?";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, bandId);
			rs = ps.executeQuery();
			while (rs.next()) {
				b.addMember(rs.getString("lastName") + ", " + rs.getString("firstName"));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return b;
	}
}
