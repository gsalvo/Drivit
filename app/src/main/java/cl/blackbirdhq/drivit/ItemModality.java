package cl.blackbirdhq.drivit;

import android.content.ClipData;

/**
 * Created by Gustavo Salvo Lara on 22-12-2015.
 */
public class ItemModality {
    private int image;
    private String title;
    private String desModality;

    public ItemModality(){
        super();
    }

    public ItemModality(int image, String title, String desModality){
        super();
        this.image = image;
        this.title = title;
        this.desModality = desModality;
    }


    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesModality(String desModality) {
        this.desModality = desModality;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesModality() {
        return desModality;
    }
}
