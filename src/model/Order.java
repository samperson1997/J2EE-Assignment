package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "user_orders")
public class Order implements Serializable {

    @Id
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_date")
    private Date date;

    @Column(name = "article_name")
    private String articleName;

    @Column(name = "article_num")
    private double articleNum;

    @Column(name = "price")
    private double price;

    @Column(name = "is_available")
    private int isAvailable;

    @Column(name = "user_id")
    private int userId;

    public Order() {
    }

    public Order(int id, Date date, String articleName, double articleNum, double price, int isAvailable, int userId) {
        this.orderId = id;
        this.date = date;
        this.articleName = articleName;
        this.articleNum = articleNum;
        this.price = price;
        this.isAvailable = isAvailable;
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int id) {
        this.orderId = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public double getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(double articleNum) {
        this.articleNum = articleNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
