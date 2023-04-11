package unl.soc.albums;

import java.util.List;

public class Demo {

	public static void main(String args[]) {

		List<Album> disc = AlbumLoader.getAlbumSummaries();

		for(Album a : disc) {
			System.out.println(a.getTitle() + " by " + a.getBand().getName() + ", " + a.getYear());
		}
		
	}
}
