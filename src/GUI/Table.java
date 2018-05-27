package GUI;

import javafx.beans.property.SimpleStringProperty;

/**
 * Constructor de la tabla
 *
 * @author deiber
 */
public class Table { ///////////////Parametros base de Tabla////////////////////
	
    private final SimpleStringProperty Artist;
    private final SimpleStringProperty Song;
    private final SimpleStringProperty Album;
    private final SimpleStringProperty Genre;
    private final SimpleStringProperty Score;
    private final SimpleStringProperty Released;

    public Table(String Artist, String Song, String Album, String Genre, String Score, String Released) {
        super();
        this.Artist = new SimpleStringProperty(Artist);
        this.Song = new SimpleStringProperty(Song);
        this.Album = new SimpleStringProperty(Album);
        this.Genre = new SimpleStringProperty(Genre);
        this.Score = new SimpleStringProperty(Score);
        this.Released = new SimpleStringProperty(Released);
    }

    public void setArtist(String data) {
        Artist.set(data);
    }
    
    public String getArtist() {
        return Artist.get();
    }

    public void setSong(String data) {
        Song.set(data);
    }
    
    public String getSong() {
        return Song.get();
    }

    public void setAlbum(String data) {
        Album.set(data);
    }
    
    public String getAlbum() {
        return Album.get();
    }
    
    public void setGenre(String data) {
        Genre.set(data);
    }
    
    public String getGenre() {
        return Genre.get();
    }
    
    public void setScore(String data) {
        Score.set(data);
    }
    
    public String getScore() {
        return Score.get();
    }
    
    public void setReleased(String data) {
        Released.set(data);
    }
    
    public String getReleased() {
        return Released.get();
    }
   
}
