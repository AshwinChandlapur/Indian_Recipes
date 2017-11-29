package vadeworks.vadekitchen.adapter;

public class generic_adapter {

    String image[], title;
    String time;
    String ingredients;
    String directions;
    String category;
    String videoUrl;


    public generic_adapter(String[] image, String title, String time, String ingredients, String directions,String category, String videoUrl) {
        this.image = image;
        this.title = title;
        this.time = time;
        this.ingredients = ingredients;
        this.directions = directions;
        this.category=category;
        this.videoUrl = videoUrl;
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
    public void setCategory(String category) {
        this.category = category;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


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
    public String getCategory() {
        return category;
    }
    public String getVideoUrl() {
        return videoUrl;
    }


}
