package com.example.g8shopadmin.models;

import com.example.g8shopadmin.utilities.Handle;

import java.io.Serializable;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class Voucher implements Serializable {
    private String id;
    private String image;
    private String title;
    private Integer minimumCost; // giá tối thiểu để áp dụng voucher này
    private Integer amount; // số lượng voucher
    private Date startedAt;
    private Date finishedAt;
    private Integer moneyDeals; // số tiền được khuyến mãi trong voucher này
    private String type; // loại voucher là freeship hay
    private String idDoc; // id document của voucher
    private Integer amoutOfUsed;

    public Voucher() {
        this.amoutOfUsed = 0;
    }


    public Voucher(String title, Integer minimumCost, Integer amount, Date startedAt, Date finishedAt, Integer moneyDeals, String type) {
        this.title = title;
        this.minimumCost = minimumCost;
        this.amount = amount;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.moneyDeals = moneyDeals;
        this.type = type;
        this.amoutOfUsed = 0;
    }

    public Voucher(String id, String image, String title, Integer minimumCost, Integer amount, Date startedAt, Date finishedAt, Integer moneyDeals, String type) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.minimumCost = minimumCost;
        this.amount = amount;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.moneyDeals = moneyDeals;
        this.type = type;
        this.amoutOfUsed = 0;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public Integer getMinimumCost() {
        return minimumCost;
    }

    public Integer getAmount() {
        return amount;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public Integer getMoneyDeals() {
        return moneyDeals;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMinimumCost(Integer minimumCost) {
        this.minimumCost = minimumCost;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void setMoneyDeals(Integer moneyDeals) {
        this.moneyDeals = moneyDeals;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public Integer getAmoutOfUsed() {
        return amoutOfUsed;
    }

    public void setAmoutOfUsed(Integer amoutOfUsed) {
        this.amoutOfUsed = amoutOfUsed;
    }

    public String generateSlugVoucher() {
        String titleWithDate = title + new Date().toString();
        String temp = Normalizer.normalize(titleWithDate.toLowerCase(), Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "_").replaceAll("đ", "d");

        return result;
    }

    // tạo id cho voucher
    public void generateID() {
        String result = "";

        if(type.equals("freeship"))  {
            result += "G8SHOPFRS";
        }else {
            result += "G8SHOP";
        }

        result += Handle.kFortmatter(moneyDeals.toString());

        String curDate = new SimpleDateFormat("ddMMyyyy").format(new Date());
        result += curDate;
        result = result.toUpperCase();

        this.id = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(id, voucher.id);
    }
}



