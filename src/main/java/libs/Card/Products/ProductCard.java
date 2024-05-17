package libs.Card.Products;
import javax.swing.ImageIcon;

import libs.Card.Card;
import libs.Card.Harvestable.AnimalCard;
import libs.Interfaces.Transactionable;
import libs.Interfaces.UseableOnAnimal;

public class ProductCard extends Card implements Transactionable, UseableOnAnimal {
    private int price;
    private int addedParameter;
    private String allowedEater;

    public ProductCard(String name, ImageIcon image, int price, int addedParameter, String allowedEater) {
        super(name, image);
        this.price = price;
        this.addedParameter = addedParameter;
        this.allowedEater = allowedEater;
    }

    public int getAddedParameter() {
        return addedParameter;
    }

    public String getAllowedEater() {
        return allowedEater;
    }

    @Override
    public void use(AnimalCard target) {
        System.out.println("Produk digunakan");
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
