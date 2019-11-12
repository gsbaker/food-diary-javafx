package fooddiary;

public class FavouriteFood extends Food {

    private int frequency;

    public FavouriteFood(int id, String name, int calories) {
        super(id, name, calories);
        frequency = 0;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        frequency ++;
    }
}
