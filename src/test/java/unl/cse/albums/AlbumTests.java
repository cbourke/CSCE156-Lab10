package unl.cse.albums;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

/**
 * A suite of tests for the {@link #AlbumAdder} class.
 * 
 */
public class AlbumTests {

	/**
	 * Tests that the {@link AlbumAdder#addAlbumToDatabase()} method correctly adds
	 * the given record to the database.
	 * 
	 */
	@Test
	public void addAlbumToDatabaseTest() {

		int albumId = AlbumAdder.addAlbumToDatabase("Atrocity Exhibition", "Danny Brown", "2016", "4");

		Album actual = Album.getDetailedAlbum(albumId);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(actual.getAlbumId(), albumId);
		Assertions.assertEquals(actual.getTitle(), "Atrocity Exhibition");
		Assertions.assertEquals(actual.getYear(), 2016);
		Assertions.assertEquals(actual.getBand().getName(), "Danny Brown");

	}

}