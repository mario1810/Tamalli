package com.accenture.tamalli.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//Collection's name that I defined in MongoDB
@Document(value="product_description")
public class ProductDescription {

    @Id
    private Long productId;
    private String descriptionHead;
    private String descriptionBody;
    private String urlImage;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDescriptionHead() {
        return descriptionHead;
    }

    public void setDescriptionHead(String descriptionHead) {
        this.descriptionHead = descriptionHead;
    }

    public String getDescriptionBody() {
        return descriptionBody;
    }

    public void setDescriptionBody(String descriptionBody) {
        this.descriptionBody = descriptionBody;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
