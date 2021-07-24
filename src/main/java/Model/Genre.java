package Model;

public class Genre {
    private int GenreId;
    private String name;

    public void setGenre_id(int id){
        this.GenreId = id;
    }

    public void setGenre_name(String name){
        this.name = name;
    }

    public int getGenre_id(){
        return this.GenreId;
    }

    public String getGenre_name(){
        return this.name;
    }

}
