package com.shopsphere.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_sizes")
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_size_id")
    private Integer productSizeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "size_label", nullable = false)
    private String sizeLabel;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Column(name = "sku_code")
    private String skuCode;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    public ProductSize() {}

    public ProductSize(Product product, String sizeLabel, Integer stockQuantity, String skuCode, Boolean isAvailable) {
        this.product = product;
        this.sizeLabel = sizeLabel;
        this.stockQuantity = stockQuantity != null ? stockQuantity : 0;
        this.skuCode = skuCode;
        this.isAvailable = isAvailable != null ? isAvailable : true;
    }

    public Integer getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(Integer productSizeId) {
        this.productSizeId = productSizeId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSizeLabel() {
        return sizeLabel;
    }

    public void setSizeLabel(String sizeLabel) {
        this.sizeLabel = sizeLabel;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "ProductSize{" +
                "productSizeId=" + productSizeId +
                ", sizeLabel='" + sizeLabel + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
