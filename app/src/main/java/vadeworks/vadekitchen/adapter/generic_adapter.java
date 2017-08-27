package vadeworks.vadekitchen.adapter;

public class generic_adapter {

    String image[], title;
    String time,ingredients,directions,inenglish;


    public generic_adapter(String[] image, String title, String time, String ingredients, String directions,String inenglish) {
        this.image = image;
        this.title = title;
        this.time = time;
        this.ingredients = ingredients;
        this.directions = directions;
        this.inenglish = inenglish;
    }

    public void setImage(String[] image) {this.image = image;}
    public void setTitle(String title)
    {
        this.title=title;
    }
    public void setTime(String time)
    {
        this.time= time;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public void setDirections(String directions) {
        this.directions = directions;
    }
    public void setInenglish(String inenglish){this.inenglish=inenglish;}


    public String[] getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }
    public String getTime(){
        return time;
    }
    public String getIngredients() {
        return ingredients;
    }
    public String getDirections() {
        return directions;
    }
    public String getInenglish() {return inenglish;}




}
