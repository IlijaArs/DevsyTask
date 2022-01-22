package com.example.devsytask;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bit_coin_prices")
public class RoomClassPrice {

    @ColumnInfo(name = "symbol")
    private String symbol;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "currentPrice")
    private double currentPrice;

    @ColumnInfo(name = "high")
    private double high;

    @ColumnInfo(name = "low")
    private double low;

    @ColumnInfo(name = "data_last_update")
    private Long data_last_update;

    @ColumnInfo(name = "market_cap_rank")
    private Integer market_cap_rank;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public Long getData_last_update() {
        return data_last_update;
    }

    public void setData_last_update(Long data_last_update) {
        this.data_last_update = data_last_update;
    }

    public Integer getMarket_cap_rank() {
        return market_cap_rank;
    }

    public void setMarket_cap_rank(Integer market_cap_rank) {
        this.market_cap_rank = market_cap_rank;
    }
}
