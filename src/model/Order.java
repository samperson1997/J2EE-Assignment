package model;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable{
	private int id;
	private Date date;
	private String articleName;
	private double articleNum;
	private double price;
	private int isAvailable;
	private int userId;

    public Order() {
    }

    public Order(int id, Date date, String articleName, double articleNum, double price, int isAvailable, int userId) {
        this.id = id;
        this.date = date;
        this.articleName = articleName;
        this.articleNum = articleNum;
        this.price = price;
        this.isAvailable = isAvailable;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
