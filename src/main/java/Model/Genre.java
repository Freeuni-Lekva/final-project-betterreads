package Model;

public class Genre implements Comparable{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return name.equals(genre.name);
    }

    @Override
    public String toString() {
        return "Genre id: " + this.GenreId + " name: " + getGenre_name();
    }

    @Override
    public int compareTo(Object o) {
        if(this == o) return 0;
        if(o == null) return -1;
        if(equals(o)) return 0;
        return ((Genre)o).getGenre_id() - getGenre_id();
    }
}
