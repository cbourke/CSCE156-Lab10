package unl.soc.albums;

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

		Album actual = AlbumLoader.getDetailedAlbum(albumId);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(albumId, actual.getAlbumId());
		Assertions.assertEquals("Atrocity Exhibition", actual.getTitle());
		Assertions.assertEquals(2016, actual.getYear());
		Assertions.assertEquals("Danny Brown", actual.getBand().getName());

	}

}