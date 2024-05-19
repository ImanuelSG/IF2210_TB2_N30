package libs.Card.Products;

import java.util.ArrayList;

import javafx.scene.image.Image;

import libs.Card.Card;
import libs.Card.Harvestable.AnimalCard;
import libs.Interfaces.Transactionable;
import libs.Interfaces.UseableOnAnimal;

public abstract class ProductCard extends Card implements Transactionable, UseableOnAnimal {
    private int price;
    private int addedParameter;
    private ArrayList<String> allowedEater;

    public ProductCard(String name, Image image, int price, int addedParameter, ArrayList<String> allowedEater) {
        super(name, image);
        this.price = price;
        this.addedParameter = addedParameter;
        this.allowedEater = allowedEater;
    }

    public int getAddedParameter() {
        return addedParameter;
    }

    public ArrayList<String> getAllowedEater() {
        return allowedEater;
    }

    @Override
    public void use(AnimalCard target) {
        System.out.println("Product is used");
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }
}
